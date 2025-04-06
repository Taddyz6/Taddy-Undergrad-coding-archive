import './Graph.css';
import { useState, useEffect, ChangeEvent } from 'react';
import { PieChart } from './chart/PieChart';

interface ChartData {
    labels: string[];
    datasets: {
        label: string;
        data: number[];
        backgroundColor: string[];
    }[];
}

const Distribution: React.FC = () => {
    const [groups, setGroups] = useState<string[]>([
        "National Estimate",
        "By Age",
        "By Sex",
        "By Race/Hispanic ethnicity",
        "By State",
        "By Education",
    ]); // List of groups

    const [selectedGroup, setSelectedGroup] = useState<string>("National Estimate");
    const [chartData, setChartData] = useState<ChartData | null>(null); // Define type explicitly

    // Fetch data when the selectedGroup changes
    useEffect(() => {
        fetch(`http://localhost:5001/data?group=${selectedGroup}`)
            .then(response => response.json())
            .then((data: { Subgroup: string; count: number }[]) => {
                if (data.length > 0) {
                    const labels = data.map((item) => item.Subgroup);
                    const values = data.map((item) => item.count);
    
                    setChartData({
                        labels,
                        datasets: [
                            {
                                label: "Count",
                                data: values,
                                backgroundColor: [
                                    "#FF6384", "#36A2EB", "#FFCE56",
                                    "#4BC0C0", "#9966FF", "#FF9F40"
                                ],
                            },
                        ],
                    });
                } else {
                    setChartData(null); // Handle empty data
                }
            })
            .catch(err => {
                console.error(err);
                setChartData(null);
            });
    }, [selectedGroup]);

    const handleGroupChange = (event: ChangeEvent<HTMLInputElement>) => { // Type the 'event' parameter
        setSelectedGroup(event.target.value);
    };

    return (
        <div className="chart">
            <p>Select a subgroup to see the composition of their indicators:</p>
            
            {/* Render group selector */}
            <div>
                {groups.map((group, index) => (
                    <label key={index}>
                        <input
                            type="radio"
                            name="group"
                            value={group}
                            checked={selectedGroup === group}
                            onChange={handleGroupChange}
                        />
                        {group}
                    </label>
                ))}
            </div>

            {/* Render pie chart */}
            <PieChart chartData={chartData} />
        </div>
    );
};

export default Distribution;
