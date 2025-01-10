<template>
  <div class="edit-budget container my-4">
    <h2 class="text-center mb-4">Edit Budget</h2>
    <form @submit.prevent="updateBudget">
      <div class="mb-3">
        <label for="name" class="form-label">Budget Name</label>
        <input
          type="text"
          id="name"
          class="form-control"
          v-model="form.name"
          required
        />
      </div>

      <div class="mb-3">
        <label for="allocatedAmount" class="form-label">Allocated Amount (£)</label>
        <input
          type="number"
          id="allocatedAmount"
          class="form-control"
          v-model.number="form.allocatedAmount"
          required
        />
      </div>

      <div class="mb-3">
        <label for="startDate" class="form-label">Start Date</label>
        <input
          type="date"
          id="startDate"
          class="form-control"
          v-model="form.startDate"
          required
        />
      </div>

      <div class="mb-3">
        <label for="endDate" class="form-label">End Date</label>
        <input
          type="date"
          id="endDate"
          class="form-control"
          v-model="form.endDate"
          required
        />
      </div>

      <div class="mb-3">
        <label for="category" class="form-label">Category</label>
        <select
          id="category"
          class="form-select"
          v-model.number="form.category.id"
          required
        >
          <option v-for="category in categories" :key="category.id" :value="category.id">
            {{ category.name }}
          </option>
        </select>
      </div>

      <div class="text-center">
        <button type="submit" class="btn btn-primary">Update Budget</button>
      </div>
    </form>
  </div>
</template>

<script>
import apiClient from "@/api/axios";

export default {
  name: "EditBudget",
  data() {
    return {
      form: {
        name: "",
        allocatedAmount: null,
        startDate: "",
        endDate: "",
        category: {
          id: null,
        },
      },
      categories: [],
    };
  },
  mounted() {
    this.fetchBudget();
    this.fetchCategories();
  },
  methods: {
    fetchBudget() {
      apiClient
        .get("/budgets")
        .then((response) => {
          const budgetId = this.$route.params.id;
          const budget = response.data.find((b) => b.id === parseInt(budgetId));
          if (budget) {
            this.form = {
              name: budget.name,
              allocatedAmount: budget.allocatedAmount,
              startDate: budget.startDate,
              endDate: budget.endDate,
              category: {
                id: budget.category?.id || null,
              },
            };
          } else {
            alert("Budget not found.");
            this.$router.push("/manage-budgets");
          }
        })
        .catch((error) => {
          console.error("Error fetching budgets:", error);
          alert("Failed to fetch budgets.");
        });
    },
    fetchCategories() {
      apiClient
        .get("/categories")
        .then((response) => {
          this.categories = response.data;
        })
        .catch((error) => {
          console.error("Error fetching categories:", error);
          alert("Failed to fetch categories.");
        });
    },
    updateBudget() {
      const budgetId = this.$route.params.id;
      apiClient
        .put(`/budgets/${budgetId}`, this.form)
        .then(() => {
          alert("Budget updated successfully!");
          this.$router.push("/manage-budgets");
        })
        .catch((error) => {
          console.error("Error updating budget:", error);
          alert("Failed to update budget.");
        });
    },
  },
};
</script>

<style scoped>
.edit-budget {
  max-width: 600px;
  margin: 0 auto;
}

.form-label {
  font-weight: bold;
}

button {
  width: 100%;
}
</style>
