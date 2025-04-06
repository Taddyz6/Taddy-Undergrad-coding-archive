import { Pie } from "react-chartjs-2";
import { 
    Chart as ChartJS, 
    Tooltip, 
    Legend,
    ArcElement,
} from 'chart.js';

ChartJS.register(
    Tooltip, 
    Legend,
    ArcElement,
);

// Define the ChartData type
interface ChartData {
    labels: string[];
    datasets: {
        label: string;
        data: number[];
        backgroundColor: string[];
    }[];
}

interface PieChartProps {
    chartData: ChartData | null; // The chartData can be null during loading or no data cases
}

export const PieChart: React.FC<PieChartProps> = ({ chartData }) => {
    const options = {
        responsive: true,
        plugins: {            
            title: {
                display: true,
                text: "Distribution",
            },
        },
    };

    return (
        <>
            {chartData ? (
                <Pie options={options} data={chartData} />
            ) : (
                <p>No data available for the selected group.</p>
            )}
        </>
    );
};
