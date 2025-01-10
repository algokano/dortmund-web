document.addEventListener("DOMContentLoaded", () => {
    const expenseBreakdown = {
        Bills: 5700,
        Education: 15000,
        Food: 500,
        Gym: 100,
        Transportation: 300,
    };

    const colors = [
        "#5bc0de", // Blue
        "#d9534f", // Red
        "#5cb85c", // Green
        "#f0ad4e", // Orange
        "#292b2c", // Black
    ];

    const ctx = document.getElementById("expenseChart").getContext("2d");
    new Chart(ctx, {
        type: "pie",
        data: {
            labels: Object.keys(expenseBreakdown),
            datasets: [
                {
                    data: Object.values(expenseBreakdown),
                    backgroundColor: colors,
                    borderWidth: 1,
                },
            ],
        },
        options: {
            plugins: {
                legend: {
                    position: "top",
                },
            },
        },
    });
});
