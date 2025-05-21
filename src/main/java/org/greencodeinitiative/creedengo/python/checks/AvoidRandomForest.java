/*
 * creedengo - Python language - Provides rules to reduce the environmental footprint of your Python programs
 * Copyright © 2024 Green Code Initiative (https://green-code-initiative.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.greencodeinitiative.creedengo.python.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.python.api.PythonSubscriptionCheck;
import org.sonar.plugins.python.api.SubscriptionContext;
import org.sonar.plugins.python.api.tree.CallExpression;
import org.sonar.plugins.python.api.tree.QualifiedExpression;
import org.sonar.plugins.python.api.tree.Tree;

import java.util.List;

import static org.sonar.plugins.python.api.tree.Tree.Kind.CALL_EXPR;

@Rule(
  key = "GCI201",
  name = "Avoid using RandomForest from scikit-learn",
  description = "Use XGBoost instead of RandomForestClassifier or RandomForestRegressor from scikit-learn for better efficiency and performance in many cases.",
  priority = Priority.MAJOR
)
public class AvoidRandomForest extends PythonSubscriptionCheck {

  private static final String DESCRIPTION = "Avoid using Randomforest, use XGBoost instead";

  private static final List<String> randomForestClasses = List.of(
    "sklearn.ensemble.RandomForestClassifier",
    "sklearn.ensemble.RandomForestRegressor"
  );

  private static final List<String> randomForestMethods = List.of(
    "RandomForestClassifier",
    "RandomForestRegressor"
  );

  @Override
  public void initialize(Context context) {
    context.registerSyntaxNodeConsumer(Tree.Kind.CALL_EXPR, this::visitCallExpression);
  }

  private void visitCallExpression(SubscriptionContext ctx) {
    
      CallExpression call = (CallExpression) ctx.syntaxNode();
      if (Utils.getQualifiedName(call) != null) {
        String qualifiedName = Utils.getQualifiedName(call);
        System.out.println("Qualified name: " + qualifiedName);
        if (qualifiedName != null && randomForestClasses.contains(qualifiedName)) {
          System.out.println("Found RandomForest usage: " + qualifiedName);
          ctx.addIssue(call, String.format(DESCRIPTION, qualifiedName));
        }
        }
      Tree callee = call.callee();
      if (callee.is(Tree.Kind.QUALIFIED_EXPR)) {
        QualifiedExpression qualifiedExpression = (QualifiedExpression) callee;
            String methodName = qualifiedExpression.name().name();

            if (randomForestMethods.contains(methodName)) {

               
                  ctx.addIssue(call.firstToken(), DESCRIPTION);

            }
        }

      
    }
  }

