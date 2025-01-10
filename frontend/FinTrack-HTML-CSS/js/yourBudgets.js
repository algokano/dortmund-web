document.addEventListener("DOMContentLoaded", () => {
    // Example data for budgets (replace with API calls or static data)
    const budgets = [
        {
            id: 1,
            name: "January Budget",
            monthlyIncome: 5000,
            startDate: "01/01/2024",
            endDate: "01/31/2024",
        },
        {
            id: 2,
            name: "February Budget",
            monthlyIncome: 4500,
            startDate: "02/01/2024",
            endDate: "02/28/2024",
        },
    ];

    const tableBody = document.getElementById("budgetsTable");

    // Populate table with budget data
    budgets.forEach((budget) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${budget.name}</td>
            <td>$${budget.monthlyIncome}</td>
            <td>${budget.startDate}</td>
            <td>${budget.endDate}</td>
            <td><input type="radio" name="selectedBudget" value="${budget.id}"></td>
            <td><a href="budgetDetails.html?budgetId=${budget.id}" class="btn btn-primary btn-sm">Details</a></td>
            <td><a href="updateBudget.html?budgetId=${budget.id}" class="btn btn-warning btn-sm">Edit</a></td>
            <td><a href="#" class="btn btn-danger btn-sm" onclick="confirmDelete(${budget.id})">Delete</a></td>
        `;
        tableBody.appendChild(row);
    });

    // Handle form submission
    document.getElementById("budgetSelectionForm").addEventListener("submit", (e) => {
        e.preventDefault();
        const selectedBudget = document.querySelector('input[name="selectedBudget"]:checked');
        if (selectedBudget) {
            alert(`Selected Budget ID: ${selectedBudget.value}`);
        } else {
            alert("Please select a budget to proceed.");
        }
    });
});

// Confirm delete action
function confirmDelete(budgetId) {
    if (confirm("Are you sure you want to delete this budget?")) {
        alert(`Budget ID ${budgetId} deleted.`);
        // Add deletion logic here
    }
}
