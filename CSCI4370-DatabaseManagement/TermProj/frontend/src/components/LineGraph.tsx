import React, { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';
import { 
    Chart as ChartJS, 
    CategoryScale, 
    LinearScale, 
    PointElement, 
    LineElement, 
    Title,
    Tooltip, 
    Legend,
} from 'chart.js';

ChartJS.register(
    CategoryScale, 
    LinearScale, 
    PointElement, 
    LineElement, 
    Title,
    Tooltip, 
    Legend
);

interface DataItem {
    year: string;
    average_value: number;
}

export const LineGraph = () => {
    const [chartData, setChartData] = useState<any>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const years = ["2020", "2021", "2022", "2023", "2024"];

                // Helper function to fetch and map data for each endpoint
                const fetchAndMapData = async (url: string, label: string, color: string) => {
                    const response = await fetch(url);
                    if (!response.ok) {
                        console.error(`Failed to fetch ${label}: ${response.statusText}`);
                        return {
                            label,
                            data: [0, 0, 0, 0, 0], // Default to 0 if fetch fails
                            borderColor: color,
                        };
                    }
                    const data: DataItem[] = await response.json();
                    console.log(`Fetched data for ${label}:`, data);

                    const mappedValues = years.map((year) => {
                        const match = data.find(item => item.year.toString() === year);
                        return match ? match.average_value : 0; // Default to 0 if no match
                    });

                    console.log(`Mapped values for ${label}:`, mappedValues);

                    return {
                        label,
                        data: mappedValues,
                        borderColor: color,
                    };
                };

                // Fetch data for both datasets
                const datasets = await Promise.all([
                    fetchAndMapData(
                        'http://localhost:5001/averageDataAnxIndicator',
                        "Average value of indicators of anxiety or depression",
                        "blue"
                    ),
                    fetchAndMapData(
                        'http://localhost:5001/averageHealthInsuranceCoverage',
                        "Average value of health coverage",
                        "red"
                    ),
                    fetchAndMapData(
                        'http://localhost:5001/averageLackofSocialConnection',
                        "Average lack of social connection",
                        "green"
                    ),
                    fetchAndMapData(
                        'http://localhost:5001/averageMentalHealthCare',
                        "Average Mental HealthCare",
                        "purple"
                    ),
                    fetchAndMapData(
                        'http://localhost:5001/averagePostCovidCondition',
                        "Average Post Covid Condition",
                        "cyan"
                    ),
                    fetchAndMapData(
                        'http://localhost:5001/averageTelemedicineUse',
                        "Average Telemedicine Use",
                        "yellow"
                    ),
                ]);

                console.log("Final datasets:", datasets);

                // Update chart data
                setChartData({
                    labels: years,
                    datasets,
                });
            } catch (err) {
                console.error('Error fetching data:', err);
            }
        };

        fetchData();
    }, []);

    const options = {
        responsive: true,
        plugins: {
            title: {
                display: true,
                text: "Trend",
            },
        },
    };

    return (
        <div>
            {chartData ? (
                <Line options={options} data={chartData} />
            ) : (
                <p>Loading chart data...</p>
            )}
        </div>
    );
};
