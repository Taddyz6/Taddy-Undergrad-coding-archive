import { useState, useEffect } from 'react';
import './Graph.css';
import { BarGraph } from './chart/BarGraph';

interface GraphData {
    table: string;
    data: { Age: string; AverageValue: number }[];
}

const TopRanking = () => {
    const [graphData, setGraphData] = useState<GraphData[]>([]); // Explicitly define the type
    const tables = [
                    'TermProject.indicators_of_anxiety_or_depression', 
                    'TermProject.indicators_of_health_insurance_coverage_at_the_time_of_interview', 
                    'TermProject.indicators_of_reduced_access_to_care_due_to_covid_last_4_weeks',
                    'TermProject.lack_of_social_connection_20241118',
                    'TermProject.mental_health_care_in_the_last_4_weeks_20241118',
                    'TermProject.post-covid_conditions_20241118',
                    'TermProject.telemedicine_use_in_the_last_4_weeks_20241118',
                ]; 

    useEffect(() => {
        const fetchData = async () => {
            const data: GraphData[] = [];
            for (const table of tables) {
                const response = await fetch(`http://localhost:5001/api/graph-data/${table}`);
                const result = await response.json();
                data.push({ table, data: result });
            }
            setGraphData(data); // Set the fetched data
        };

        fetchData();
    }, []);

    return (
        <div className="chart">
            {graphData.map((graph, index) => (
                <BarGraph key={index} data={graph.data} title={graph.table} />
            ))}
        </div>
    );
};

export default TopRanking;
