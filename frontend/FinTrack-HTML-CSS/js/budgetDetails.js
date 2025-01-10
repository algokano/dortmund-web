document.addEventListener("DOMContentLoaded", () => {
    const categories = ["Food", "Rent", "Transport", "Entertainment", "Utilities"];
    const allocatedAmounts = [1000, 2000, 500, 400, 600];
    const spentAmounts = [800, 2000, 300, 450, 500];

    const ctx = document.getElementById("budgetChart").getContext("2d");

    new Chart(ctx, {
        type: "bar",
        data: {
            labels: categories,
            datasets: [
                {
                    label: "Allocated Amount",
                    data: allocatedAmounts,
                    backgroundColor: "rgba(75, 192, 192, 0.7)", // Green
                    borderWidth: 1,
                },
                {
                    label: "Spent Amount",
                    data: spentAmounts,
                    backgroundColor: "rgba(255, 99, 132, 0.7)", // Red
                    borderWidth: 1,
                },
            ],
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: "top",
                },
            },
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
});
