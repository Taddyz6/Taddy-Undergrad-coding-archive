INSERT INTO Anxiety_Indicator (Indicator, State, Time_Period, Value, Low_CI, High_CI, Confidence_Interval, Quartile_Range)
SELECT Indicator, State, `Time Period`, Value, `Low CI`, `High CI`, `Confidence Interval`, `Quartile Range`
FROM anxiety_depression_indicators;

SELECT Indicator, State, `Time Period`, COUNT(*)
FROM anxiety_depression_indicators
GROUP BY Indicator, State, `Time Period`
HAVING COUNT(*) > 1;

INSERT INTO Anxiety_Indicator (Indicator, State, Time_Period, Value, Low_CI, High_CI, Confidence_Interval, Quartile_Range)
SELECT Indicator, State, `Time Period`, AVG(Value), AVG(`Low CI`), AVG(`High CI`), MAX(`Confidence Interval`), MAX(`Quartile Range`)
FROM anxiety_depression_indicators
GROUP BY Indicator, State, `Time Period`;


INSERT INTO State_Subgroup (State, Subgroup, Time_Period, Value)
SELECT 
    State, 
    Subgroup, 
    `Time Period` AS Time_Period,
    AVG(Value) AS Value
FROM 
    anxiety_depression_indicators
GROUP BY 
    State, Subgroup, `Time Period`;

CREATE TABLE Subgroup_Mapping (
    Indicator VARCHAR(255),
    Subgroup VARCHAR(100),
    PRIMARY KEY (Indicator, Subgroup)
);

INSERT INTO Subgroup_Mapping (Indicator, Subgroup)
SELECT DISTINCT Indicator, Subgroup
FROM anxiety_depression_indicators;

SELECT ai.Indicator, ai.Value, ss.Subgroup
FROM Anxiety_Indicator ai
JOIN State_Subgroup ss ON ai.State = ss.State AND ai.Time_Period = ss.Time_Period
WHERE ai.State = 'California' AND ai.Time_Period = 1;



CREATE TABLE Health_Indicator (
    Indicator VARCHAR(255),
    State VARCHAR(100),
    Time_Period INT,
    Value FLOAT,
    Low_CI FLOAT,
    High_CI FLOAT,
    Confidence_Interval VARCHAR(50),
    Quartile_Range VARCHAR(50),
    PRIMARY KEY (Indicator, State, Time_Period)
);

INSERT INTO Health_Indicator (Indicator, State, Time_Period, Value, Low_CI, High_CI, Confidence_Interval, Quartile_Range)
SELECT Indicator, State, `Time Period`, AVG(Value) AS Value, AVG(`Low CI`), AVG(`High CI`), MAX(`Confidence Interval`), MAX(`Quartile Range`)
FROM indicators_of_health_insurancecoverage
GROUP BY Indicator, State, `Time Period`;

SELECT State, Subgroup, `Time Period`, COUNT(*)
FROM indicators_of_health_insurancecoverage
GROUP BY State, Subgroup, `Time Period`
HAVING COUNT(*) > 1;

INSERT INTO State_Subgroup (State, Subgroup, Time_Period, Value)
SELECT State, Subgroup, `Time Period`, AVG(Value) AS Value
FROM indicators_of_health_insurancecoverage
WHERE (State, Subgroup, `Time Period`) NOT IN (
    SELECT State, Subgroup, Time_Period FROM State_Subgroup
)
GROUP BY State, Subgroup, `Time Period`;


CREATE TABLE Access_Indicator (
    Indicator VARCHAR(255),
    State VARCHAR(100),
    Time_Period INT,
    Value FLOAT,
    Low_CI FLOAT,
    High_CI FLOAT,
    Confidence_Interval VARCHAR(50),
    Quartile_Range VARCHAR(50),
    PRIMARY KEY (Indicator, State, Time_Period)
);

INSERT INTO Access_Indicator (Indicator, State, Time_Period, Value, Low_CI, High_CI, Confidence_Interval, Quartile_Range)
SELECT Indicator, State, `Time Period`, AVG(Value) AS Value, AVG(`Low CI`), AVG(`High CI`), MAX(`Confidence Interval`), MAX(`Quartile Range`)
FROM indicators_of_reduced_access_to_care
GROUP BY Indicator, State, `Time Period`;

SELECT rac.State, rac.Subgroup, rac.`Time Period`
FROM indicators_of_reduced_access_to_care rac
WHERE EXISTS (
    SELECT 1
    FROM State_Subgroup ss
    WHERE rac.State = ss.State
      AND rac.Subgroup = ss.Subgroup
      AND rac.`Time Period` = ss.Time_Period
);

INSERT INTO State_Subgroup (State, Subgroup, Time_Period, Value)
SELECT rac.State, rac.Subgroup, rac.`Time Period`, AVG(rac.Value) AS Value
FROM indicators_of_reduced_access_to_care rac
WHERE NOT EXISTS (
    SELECT 1
    FROM State_Subgroup ss
    WHERE rac.State = ss.State
      AND rac.Subgroup = ss.Subgroup
      AND rac.`Time Period` = ss.Time_Period
)
GROUP BY rac.State, rac.Subgroup, rac.`Time Period`;

CREATE TABLE Social_Indicator (
    Indicator VARCHAR(255),
    State VARCHAR(100),
    Time_Period INT,
    Value FLOAT,
    Low_CI FLOAT,
    High_CI FLOAT,
    Confidence_Interval VARCHAR(50),
    Quartile_Range VARCHAR(50),
    PRIMARY KEY (Indicator, State, Time_Period)
);

INSERT INTO Social_Indicator (Indicator, State, Time_Period, Value, Low_CI, High_CI, Confidence_Interval, Quartile_Range)
SELECT Indicator, State, `Time Period`, AVG(Value) AS Value, AVG(`Low CI`), AVG(`High CI`), MAX(`Confidence Interval`), MAX(`Quartile Range`)
FROM lack_of_social_connection
GROUP BY Indicator, State, `Time Period`;


SELECT State, Subgroup, `Time Period`, COUNT(*)
FROM lack_of_social_connection
GROUP BY State, Subgroup, `Time Period`
HAVING COUNT(*) > 1;

SELECT lsc.State, lsc.Subgroup, lsc.`Time Period`
FROM lack_of_social_connection lsc
WHERE EXISTS (
    SELECT 1
    FROM State_Subgroup ss
    WHERE lsc.State = ss.State
      AND lsc.Subgroup = ss.Subgroup
      AND lsc.`Time Period` = ss.Time_Period
);

INSERT INTO State_Subgroup (State, Subgroup, Time_Period, Value)
SELECT lsc.State, lsc.Subgroup, lsc.`Time Period`, AVG(lsc.Value) AS Value
FROM lack_of_social_connection lsc
WHERE NOT EXISTS (
    SELECT 1
    FROM State_Subgroup ss
    WHERE lsc.State = ss.State
      AND lsc.Subgroup = ss.Subgroup
      AND lsc.`Time Period` = ss.Time_Period
)
GROUP BY lsc.State, lsc.Subgroup, lsc.`Time Period`;

CREATE TABLE Mental_Health_Indicator (
    Indicator VARCHAR(255),
    State VARCHAR(100),
    Time_Period INT,
    Value FLOAT,
    Low_CI FLOAT,
    High_CI FLOAT,
    Confidence_Interval VARCHAR(50),
    Quartile_Range VARCHAR(50),
    PRIMARY KEY (Indicator, State, Time_Period)
);

INSERT INTO Mental_Health_Indicator (Indicator, State, Time_Period, Value, Low_CI, High_CI, Confidence_Interval, Quartile_Range)
SELECT Indicator, State, `Time Period`, AVG(Value) AS Value, AVG(`LowCI`), AVG(`HighCI`), MAX(`Confidence Interval`), MAX(`Quartile Range`)
FROM mental_health_care
GROUP BY Indicator, State, `Time Period`;

INSERT INTO State_Subgroup (State, Subgroup, Time_Period, Value)
SELECT mhc.State, mhc.Subgroup, mhc.`Time Period`, AVG(mhc.Value) AS Value
FROM mental_health_care mhc
WHERE NOT EXISTS (
    SELECT 1
    FROM State_Subgroup ss
    WHERE mhc.State = ss.State
      AND mhc.Subgroup = ss.Subgroup
      AND mhc.`Time Period` = ss.Time_Period
)
GROUP BY mhc.State, mhc.Subgroup, mhc.`Time Period`;

CREATE TABLE Post_COVID_Indicator (
    Indicator VARCHAR(255),
    State VARCHAR(100),
    Time_Period INT,
    Value FLOAT,
    Low_CI FLOAT,
    High_CI FLOAT,
    Confidence_Interval VARCHAR(50),
    Quartile_Range VARCHAR(50),
    PRIMARY KEY (Indicator, State, Time_Period)
);

INSERT INTO Post_COVID_Indicator (Indicator, State, Time_Period, Value, Low_CI, High_CI, Confidence_Interval, Quartile_Range)
SELECT Indicator, State, `Time Period`, AVG(Value) AS Value, AVG(`LowCI`), AVG(`HighCI`), MAX(`Confidence Interval`), MAX(`Quartile Range`)
FROM post_covid_conditions
GROUP BY Indicator, State, `Time Period`;

INSERT INTO State_Subgroup (State, Subgroup, Time_Period, Value)
SELECT pcc.State, pcc.Subgroup, pcc.`Time Period`, AVG(pcc.Value) AS Value
FROM post_covid_conditions pcc
WHERE NOT EXISTS (
    SELECT 1
    FROM State_Subgroup ss
    WHERE pcc.State = ss.State
      AND pcc.Subgroup = ss.Subgroup
      AND pcc.`Time Period` = ss.Time_Period
)
GROUP BY pcc.State, pcc.Subgroup, pcc.`Time Period`;


CREATE TABLE Telemedicine_Indicator (
    Indicator VARCHAR(255),
    State VARCHAR(100),
    Time_Period INT,
    Value FLOAT,
    Low_CI FLOAT,
    High_CI FLOAT,
    Confidence_Interval VARCHAR(50),
    Quartile_Range VARCHAR(50),
    PRIMARY KEY (Indicator, State, Time_Period)
);

INSERT INTO Telemedicine_Indicator (Indicator, State, Time_Period, Value, Low_CI, High_CI, Confidence_Interval, Quartile_Range)
SELECT Indicator, State, `Time Period`, AVG(Value) AS Value, AVG(`Low CI`), AVG(`High CI`), MAX(`Confidence Interval`), MAX(`Quartile Range`)
FROM telemedicine
GROUP BY Indicator, State, `Time Period`;

INSERT INTO State_Subgroup (State, Subgroup, Time_Period, Value)
SELECT tm.State, tm.Subgroup, tm.`Time Period`, AVG(tm.Value) AS Value
FROM telemedicine tm
WHERE NOT EXISTS (
    SELECT 1
    FROM State_Subgroup ss
    WHERE tm.State = ss.State
      AND tm.Subgroup = ss.Subgroup
      AND tm.`Time Period` = ss.Time_Period
)
GROUP BY tm.State, tm.Subgroup, tm.`Time Period`;

INSERT INTO Subgroup_Mapping (Indicator, Subgroup)
SELECT DISTINCT Indicator, Subgroup
FROM telemedicine;

INSERT INTO Subgroup_Mapping (Indicator, Subgroup)
SELECT DISTINCT Indicator, Subgroup
FROM indicators_of_health_insurancecoverage;

INSERT IGNORE INTO Subgroup_Mapping (Indicator, Subgroup)
SELECT DISTINCT Indicator, Subgroup
FROM lack_of_social_connection;

INSERT IGNORE INTO Subgroup_Mapping (Indicator, Subgroup)
SELECT DISTINCT Indicator, Subgroup
FROM mental_health_care;

INSERT IGNORE INTO Subgroup_Mapping (Indicator, Subgroup)
SELECT DISTINCT Indicator, Subgroup
FROM post_covid_conditions;

INSERT IGNORE INTO Subgroup_Mapping (Indicator, Subgroup)
SELECT DISTINCT Indicator, Subgroup
FROM indicators_of_reduced_access_to_care;

SELECT ss.Subgroup, AVG(ss.Value) AS AverageValue
FROM State_Subgroup ss
WHERE ss.State = 'California'
GROUP BY ss.Subgroup
ORDER BY AverageValue DESC;

SELECT sm.Indicator
FROM Subgroup_Mapping sm
WHERE sm.Subgroup = '18 - 29 years';

SELECT hi.State, AVG(hi.Value) AS AverageValue
FROM Health_Indicator hi
WHERE hi.Indicator = 'Uninsured at the Time of Interview'
GROUP BY hi.State;

SELECT hi.Time_Period, AVG(hi.Value) AS AverageValue
FROM Health_Indicator hi
WHERE hi.Indicator = 'Private Health Insurance Coverage'
GROUP BY hi.Time_Period
ORDER BY hi.Time_Period;

SELECT ss.State, AVG(ss.Value) AS AverageValue
FROM State_Subgroup ss
WHERE ss.Subgroup = '18 - 29 years'
GROUP BY ss.State
ORDER BY AverageValue DESC;

SELECT Indicator, AVG(Value) AS AverageValue, MAX(Value) AS Max, MIN(Value) AS Min
FROM Health_Indicator
GROUP BY Indicator;





























