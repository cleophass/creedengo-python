from sklearn.ensemble import RandomForestClassifier, RandomForestRegressor

clf = RandomForestClassifier(max_depth=2, random_state=0) # Noncompliant {{Avoid using Randomforest, use XGBoost instead}}


reg = RandomForestRegressor(n_estimators=100) # Noncompliant {{Avoid using Randomforest, use XGBoost instead}}

# Example of using RandomForestClassifier
clf = sklearn.ensemble.RandomForestClassifier(n_estimators=100, max_depth=2, random_state=0) # Noncompliant {{Avoid using Randomforest, use XGBoost instead}}

