<template>
  <div class="create-budget container my-4">
    <h2 class="text-center mb-4">Create Budget</h2>
    <form @submit.prevent="createBudget">
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
        <button type="submit" class="btn btn-success">Create Budget</button>
      </div>
    </form>
  </div>
</template>

<script>
import apiClient from "@/api/axios";

export default {
  name: "CreateBudget",
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
    this.fetchCategories();
  },
  methods: {
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
    createBudget() {
      apiClient
        .post("/budgets", this.form)
        .then(() => {
          alert("Budget created successfully!");
          this.$router.push("/manage-budgets"); // Redirect to Manage Budgets view
        })
        .catch((error) => {
          console.error("Error creating budget:", error);
          alert("Failed to create budget.");
        });
    },
  },
};
</script>

<style scoped>
.create-budget {
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
