import { Bar } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

// Define the type for the data prop
interface BarGraphProps {
    data: { Age: string; AverageValue: number }[]; // Replace with the actual structure of your data
    title: string;
}

export const BarGraph: React.FC<BarGraphProps> = ({ data, title }) => {
    const options = {
        responsive: true,
        plugins: {
            title: {
                display: true,
                text: title,
            },
        },
    };

    const chartData = {
        labels: data.map((item) => item.Age),
        datasets: [
            {
                label: 'Average Value',
                data: data.map((item) => item.AverageValue),
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
            },
        ],
    };

    return <Bar options={options} data={chartData} />;
};
