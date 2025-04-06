const express = require('express');
const mysql = require('mysql')
const cors = require('cors')
require('dotenv').config(); //import .env file for mysql 

const app = express()
app.use(cors())
app.use(express.json()); // For parsing JSON in requests

//mysql connection
const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
});

//error message if not able to connect to mysql
db.connect((err) => {
    if (err) {
      console.error('Error connecting to MySQL:', err.message);
      return;
    }
    console.log('Connected to MySQL!');
  });

app.get('/', (re,res)=> {
    return res.json("Connected to MySQL!");
})

//fetch data from `indicators_of_anxiety_or_depression`
app.get("/indicators_of_anxiety", (req, res) => {
    const query = `SELECT *
                    FROM TermProject.indicators_of_anxiety_or_depression`;
    db.query(query, (err, result) => {
        if (err) { res.status(500).send(err);} else {res.json(result);}
    });
});

//fetch data from `indicators_of_health_insurance_coverage_at_the_time_of_interview`
app.get("/indicators_of_health_insurance_coverage_at_the_time_of_interview", (req, res) => {
    const query = `SELECT *
                    FROM TermProject.indicators_of_health_insurance_coverage_at_the_time_of_interview`;
    db.query(query, (err, result) => {
        if (err) { res.status(500).send(err); } else { res.json(result);}
    });
});

//fetch data from `indicators_of_reduced_access_to_care_due_to_covid_last_4_weeks`
app.get("/indicators_of_reduced_access_to_care_due_to_covid_last_4_weeks", (req, res) => {
    const query = `SELECT *
                    FROM TermProject.indicators_of_reduced_access_to_care_due_to_covid_last_4_weeks`;
    db.query(query, (err, result) => {
        if (err) { res.status(500).send(err);} else { res.json(result); }
    });
});

//fetch data from `lack_of_social_connection_20241118`
app.get("/lack_of_social_connection_20241118", (req, res) => {
    const query = `SELECT *
                    FROM TermProject.lack_of_social_connection_20241118`;
    db.query(query, (err, result) => {
        if (err) { res.status(500).send(err);} else { res.json(result);}
    });
});


//fetch data from `mental_health_care_in_the_last_4_weeks_20241118`
app.get("/mental_health_care_in_the_last_4_weeks_20241118", (req, res) => {
    const query = `SELECT *
                    FROM TermProject.mental_health_care_in_the_last_4_weeks_20241118`;
    db.query(query, (err, result) => {
        if (err) { res.status(500).send(err);} else { res.json(result);}
    });
});

//fetch data from `post-covid_conditions_20241118`
app.get("/post-covid_conditions_20241118", (req, res) => {
    const query = `SELECT *
                    FROM TermProject.post-covid_conditions_20241118`;
    db.query(query, (err, result) => {
        if (err) { res.status(500).send(err);} else { res.json(result);}
    });
});

//fetch data from `telemedicine_use_in_the_last_4_weeks_20241118`
app.get("/telemedicine_use_in_the_last_4_weeks_20241118", (req, res) => {
    const query = `SELECT *
                    FROM TermProject.telemedicine_use_in_the_last_4_weeks_20241118`;
    db.query(query, (err, result) => {
        if (err) { res.status(500).send(err);} else { res.json(result);}
    });
});

app.get("/averageDataAnxIndicator", (req, res) => {
    const query = `SELECT 
                    CAST(SUBSTRING_INDEX(\`Time Period Label\`, ',', -1) AS UNSIGNED) AS year,
                    ROUND(AVG(value),1) AS average_value
                    FROM 
                        TermProject.indicators_of_anxiety_or_depression
                    GROUP BY 
                        year
                    ORDER BY 
                        year;
                    `;
    db.query(query, (err, result) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(result);
        }
    });
});

app.get("/averageHealthInsuranceCoverage", (req, res) => {
    const query = `SELECT 
                    CAST(SUBSTRING_INDEX(\`Time Period Label\`, ',', -1) AS UNSIGNED) AS year,
                    ROUND(AVG(value),1) AS average_value
                    FROM 
                        TermProject.indicators_of_health_insurance_coverage_at_the_time_of_interview
                    GROUP BY 
                        year
                    ORDER BY 
                        year;
                    `;
    db.query(query, (err, result) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(result);
        }
    });
});

//average reducedCare
app.get("/averageReducedCare", (req, res) => {
    const query = `SELECT 
                    CAST(SUBSTRING_INDEX(\`Time Period Label\`, ',', -1) AS UNSIGNED) AS year,
                    ROUND(AVG(value),1) AS average_value
                    FROM 
                        TermProject.indicators_of_reduced_access_to_care_due_to_covid_last_4_weeks
                    GROUP BY 
                        year
                    ORDER BY 
                        year;
                    `;
    db.query(query, (err, result) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(result);
        }
    });
});

//average lack_of_social_connection_20241118
app.get("/averageLackofSocialConnection", (req, res) => {
    const query = `SELECT 
                    CAST(SUBSTRING_INDEX(\`Time Period Label\`, ',', -1) AS UNSIGNED) AS year,
                    ROUND(AVG(value),1) AS average_value
                    FROM 
                        TermProject.lack_of_social_connection_20241118
                    GROUP BY 
                        year
                    ORDER BY 
                        year;
                    `;
    db.query(query, (err, result) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(result);
        }
    });
});

//average mental_health_care_in_the_last_4_weeks_20241118
app.get("/averageMentalHealthCare", (req, res) => {
    const query = `SELECT 
                    CAST(SUBSTRING_INDEX(\`Time Period Label\`, ',', -1) AS UNSIGNED) AS year,
                    ROUND(AVG(value),1) AS average_value
                    FROM 
                        TermProject.mental_health_care_in_the_last_4_weeks_20241118
                    GROUP BY 
                        year
                    ORDER BY 
                        year;
                    `;
    db.query(query, (err, result) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(result);
        }
    });
});

//average `post-covid_conditions_20241118`
app.get("/averagePostCovidCondition", (req, res) => {
    const query = `SELECT 
                    CAST(SUBSTRING_INDEX(\`Time Period Label\`, ',', -1) AS UNSIGNED) AS year,
                    ROUND(AVG(value),1) AS average_value
                    FROM 
                        TermProject.\`post-covid_conditions_20241118\`
                    GROUP BY 
                        year
                    ORDER BY 
                        year;
                    `;
    db.query(query, (err, result) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(result);
        }
    });
});

//average telemedicine_use_in_the_last_4_weeks_20241118
app.get("/averageTelemedicineUse", (req, res) => {
    const query = `SELECT 
                    CAST(SUBSTRING_INDEX(\`Time Period Label\`, ',', -1) AS UNSIGNED) AS year,
                    ROUND(AVG(value),1) AS average_value
                    FROM 
                        TermProject.telemedicine_use_in_the_last_4_weeks_20241118
                    GROUP BY 
                        year
                    ORDER BY 
                        year;
                    `;
    db.query(query, (err, result) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(result);
        }
    });
});

//by age 
app.get('/api/graph-data/:table', (req, res) => {
    const { table } = req.params;
    const query = `
        SELECT 
            Subgroup AS Age, 
            AVG(Value) AS AverageValue
        FROM 
            ${mysql.escapeId(table)}
        WHERE 
            \`Group\` = 'By Age'
        GROUP BY 
            Subgroup;
    `;
    db.query(query, (err, results) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(results);
        }
    });
});

//pie chart
app.get('/data', (req, res) => {
    const { group } = req.query;

    const query = `
        SELECT Subgroup, COUNT(*) AS count
        FROM (
            SELECT \`Subgroup\`, \`Group\` FROM TermProject.indicators_of_anxiety_or_depression
            UNION ALL
            SELECT \`Subgroup\`, \`Group\` FROM TermProject.indicators_of_health_insurance_coverage_at_the_time_of_interview
            UNION ALL
            SELECT \`Subgroup\`, \`Group\` FROM TermProject.indicators_of_reduced_access_to_care_due_to_covid_last_4_weeks
            UNION ALL
            SELECT \`Subgroup\`, \`Group\` FROM TermProject.lack_of_social_connection_20241118
            UNION ALL
            SELECT \`Subgroup\`, \`Group\` FROM TermProject.mental_health_care_in_the_last_4_weeks_20241118
            UNION ALL
            SELECT \`Subgroup\`, \`Group\` FROM TermProject.\`post-covid_conditions_20241118\`
            UNION ALL
            SELECT \`Subgroup\`, \`Group\` FROM TermProject.telemedicine_use_in_the_last_4_weeks_20241118
        ) AS combined
        WHERE \`Group\` = ?
        GROUP BY Subgroup;
    `;

    db.query(query, [group], (err, results) => {
        if (err) {
            console.error(err);
            res.status(500).send('Server Error');
        } else {
            res.json(results);
        }
    });
});


// Start server
const PORT = process.env.PORT || 5001;
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});