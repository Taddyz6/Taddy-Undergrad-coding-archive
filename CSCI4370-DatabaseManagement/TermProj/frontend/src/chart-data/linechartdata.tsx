export const getDynamicLineChartData = (averageValues: number[]) => {
    return {
        labels: ["2020", "2021", "2022", "2023", "2024"], // Years are static
        datasets: [
            {
                label: "Average Value",
                data: averageValues, // Dynamically updated
                borderColor: "blue",
            },
        ],
    };
};
