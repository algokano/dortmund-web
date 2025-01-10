document.addEventListener("DOMContentLoaded", () => {
    const categorySelect = document.getElementById("categorySelect");
    const customCategoryContainer = document.getElementById("customCategoryContainer");

    // Show/hide custom category field based on selection
    categorySelect.addEventListener("change", () => {
        if (categorySelect.value === "Other") {
            customCategoryContainer.style.display = "block";
        } else {
            customCategoryContainer.style.display = "none";
        }
    });

    // Handle form submission
    const form = document.getElementById("addExpenseForm");
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const date = document.getElementById("date").value;
        const category =
            categorySelect.value === "Other"
                ? document.getElementById("customCategory").value
                : categorySelect.value;
        const amount = document.getElementById("amount").value;
        const description = document.getElementById("description").value;

        // Validate and log the form data
        if (!date || !category || !amount) {
            alert("Please fill in all required fields.");
            return;
        }

        console.log({ date, category, amount, description });
        alert("Expense added successfully!");

        // Reset the form
        form.reset();
        customCategoryContainer.style.display = "none";
    });
});
