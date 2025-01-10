document.addEventListener("DOMContentLoaded", () => {
    // Example data (replace this with dynamic content or API calls)
    const budgets = [
        {
            name: "January Budget",
            totalExpenses: 2500,
            totalAllocated: 3000,
            savings: 500,
            overspending: 0,
            breakdown: {
                "Food": 800,
                "Rent": 1000,
                "Entertainment": 300,
                "Transport": 400,
            },
        },
        {
            name: "February Budget",
            totalExpenses: 2000,
            totalAllocated: 2500,
            savings: 500,
            overspending: 0,
            breakdown: {
                "Food": 700,
                "Rent": 900,
                "Utilities": 300,
                "Transport": 100,
            },
        },
    ];

    const container = document.getElementById("budgetSummaryContainer");

    // Generate budget cards
    budgets.forEach((budget, index) => {
        const card = document.createElement("div");
        card.classList.add("card");

        card.innerHTML = `
            <h4>${budget.name}</h4>
            <p><strong>Total Expenses:</strong> $${budget.totalExpenses}</p>
            <p><strong>Total Allocated Amount:</strong> $${budget.totalAllocated}</p>
            <p><strong>Savings:</strong> $${budget.savings}</p>
            <p><strong>Overspending:</strong> $${budget.overspending}</p>
            <div class="chart-container">
                <canvas id="chart-${index}"></canvas>
            </div>
        `;

        container.appendChild(card);

        // Create chart for each budget
        const ctx = document.getElementById(`chart-${index}`).getContext("2d");
        new Chart(ctx, {
            type: "bar",
            data: {
                labels: Object.keys(budget.breakdown),
                datasets: [
                    {
                        label: "Expenses",
                        data: Object.values(budget.breakdown),
                        backgroundColor: "rgba(54, 162, 235, 0.5)",
                        borderColor: "rgba(54, 162, 235, 1)",
                        borderWidth: 1,
                    },
                ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                },
            },
        });
    });
});
