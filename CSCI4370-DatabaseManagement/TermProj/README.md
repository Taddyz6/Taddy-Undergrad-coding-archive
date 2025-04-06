# TermProj
Database Management Term Project

# Group names:
1. Shahrima Ishrat
2. Youran Roh
3. Junhao Zhang

# how to compile
you need two terminals, or possibly three running at the same time for mySQL server, backend server, and REACT app.
1. you must edit `.env` file using your authentication for mySQL server. 
2. for mySQL setup, download `.csv` files from the data dropbox submission on eLC.
3. to set up the database, create a database named `TermProj`.
4. using mySQL workbench and Table Data Import Wizard, import the `.csv` files and name the tables as follows:
    1. `Indicators_of_Anxiety_or_Depression_Based_on_Reported_Frequency_of_Symptoms_During_Last_7_Days_20241118.csv` as `indicators_of_anxiety_or_depression`,
    2. `Indicators_of_Health_Insurance_Coverage_at_the_Time_of_Interview_20241118.csv` as `indicators_of_health_insurance_coverage_at_the_time_of_interview`
    3. `Indicators_of_Reduced_Access_to_Care_Due_to_the_Coronavirus_Pandemic_During_Last_4_Weeks_20241118.csv` as `indicators_of_reduced_access_to_care_due_to_covid_last_4_weeks`
    4. `datasets/Lack_of_Social_Connection_20241118.csv` as `lack_of_social_connection_20241118`
    5. `Mental_Health_Care_in_the_Last_4_Weeks_20241118.csv` as `mental_health_care_in_the_last_4_weeks_20241118`
    6. `Post-COVID_Conditions_20241118.csv` as `post-covid_conditions_20241118`
    7. `Telemedicine_Use_in_the_Last_4_Weeks_20241118.csv` as `telemedicine_use_in_the_last_4_weeks_20241118`
5. start mySQL server. 
6. move to backend folder `TermProj/backend` then start the server using `node server.js`
7. move to frontend folder `TermProj/frontend` then start the REACT app using `npm run dev`
8. if not working, check if the packages are installed.

