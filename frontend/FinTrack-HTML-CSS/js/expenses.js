document.addEventListener("DOMContentLoaded", () => {
    // Example expense data (replace with API calls or static JSON files)
    const expenses = [
        {
            date: "2024-07-14",
            category: "Groceries",
            amount: 50.0,
            description: "Bought groceries for the week",
            id: 1,
        },
        {
            date: "2024-07-13",
            category: "Transport",
            amount: 15.0,
            description: "Bus fare",
            id: 2,
        },
        {
            date: "2024-07-12",
            category: "Entertainment",
            amount: 30.0,
            description: "Movie night",
            id: 3,
        },
    ];

    const container = document.getElementById("expensesContainer");

    if (expenses.length > 0) {
        // Create table
        const table = document.createElement("table");
        table.classList.add("table", "table-striped");

        // Add table header
        table.innerHTML = `
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Category</th>
                    <th>Amount</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                ${expenses
                    .map(
                        (expense) => `
                        <tr>
                            <td>${expense.date}</td>
                            <td>${expense.category}</td>
                            <td>$${expense.amount.toFixed(2)}</td>
                            <td>${expense.description}</td>
                            <td>
                                <a href="#" onclick="deleteExpense(${expense.id})">Delete</a>
                            </td>
                        </tr>
                    `
                    )
                    .join("")}
            </tbody>
        `;

        container.appendChild(table);
    } else {
        // Show "No expenses found" message
        const message = document.createElement("p");
        message.textContent = "No expenses found.";
        container.appendChild(message);
    }
});

// Function to handle expense deletion
function deleteExpense(id) {
    if (confirm("Are you sure you want to delete this expense?")) {
        alert(`Expense ID ${id} deleted.`);
    }
}
