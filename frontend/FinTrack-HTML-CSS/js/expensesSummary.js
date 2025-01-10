document.addEventListener("DOMContentLoaded", () => {
    const expensesByCategoryData = [
        { category: "Groceries", amount: 200 },
        { category: "Transport", amount: 150 },
        { category: "Entertainment", amount: 100 },
    ];

    const expensesOverTimeData = [
        { date: "2024-01", amount: 450 },
        { date: "2024-02", amount: 300 },
        { date: "2024-03", amount: 500 },
    ];

    // Expenses By Category Chart
    const expensesByCategoryCtx = document.getElementById("expensesByCategoryChart").getContext("2d");
    new Chart(expensesByCategoryCtx, {
        type: "bar",
        data: {
            labels: expensesByCategoryData.map((d) => d.category),
            datasets: [
                {
                    label: "Amount",
                    data: expensesByCategoryData.map((d) => d.amount),
                    backgroundColor: "rgba(54, 162, 235, 0.7)",
                },
            ],
        },
    });

    // Expenses Over Time Chart
    const expensesOverTimeCtx = document.getElementById("expensesOverTimeChart").getContext("2d");
    new Chart(expensesOverTimeCtx, {
        type: "line",
        data: {
            labels: expensesOverTimeData.map((d) => d.date),
            datasets: [
                {
                    label: "Amount",
                    data: expensesOverTimeData.map((d) => d.amount),
                    backgroundColor: "rgba(75, 192, 192, 0.2)",
                    borderColor: "rgba(75, 192, 192, 1)",
                },
            ],
        },
    });
});
