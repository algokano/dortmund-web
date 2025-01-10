document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("createBudgetForm");

    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const budgetName = document.getElementById("budgetName").value;
        const monthlyIncome = document.getElementById("monthlyIncome").value;
        const startDate = document.getElementById("startDate").value;
        const endDate = document.getElementById("endDate").value;

        // Collect category percentages
        const categories = [];
        const rows = document.querySelectorAll("#categoriesTable tr");
        rows.forEach((row) => {
            const category = row.cells[0].innerText;
            const percentage = row.cells[1].querySelector("input").value;
            categories.push({ category, percentage });
        });

        // Log collected data (replace this with API call or localStorage logic)
        console.log({
            budgetName,
            monthlyIncome,
            startDate,
            endDate,
            categories,
        });

        alert("Budget created successfully!");
        form.reset();
    });
});
