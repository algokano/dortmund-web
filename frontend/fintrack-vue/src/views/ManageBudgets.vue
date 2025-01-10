<template>
  <div class="manage-budgets container my-4">
    <h2 class="text-center mb-4">Manage Budgets</h2>

    <div v-if="loading" class="spinner-container">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <div v-else>
      <div v-if="budgets.length > 0" class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>Name</th>
              <th>Allocated Amount</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="budget in budgets" :key="budget.id">
              <td>{{ budget.name }}</td>
              <td>£{{ budget.allocatedAmount.toFixed(2) }}</td>
              <td>{{ budget.startDate }}</td>
              <td>{{ budget.endDate }}</td>
              <td>
                <button class="btn btn-warning btn-sm me-2" @click="editBudget(budget.id)">Edit</button>
                <button class="btn btn-danger btn-sm" @click="deleteBudget(budget.id)">Delete</button>
                <button class="btn btn-info btn-sm" @click="viewProgress(budget.id)">View Progress</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else class="text-center">
        <p>No budgets found. Create a new budget to get started.</p>
      </div>
      <div class="text-center mt-4">
        <router-link to="/create-budget" class="btn btn-success">Add New Budget</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/api/axios";

export default {
  name: "ManageBudgets",
  data() {
    return {
      budgets: [],
      loading: true,
    };
  },
  mounted() {
    this.fetchBudgets();
  },
  methods: {
    fetchBudgets() {
      this.loading = true;
      apiClient
        .get("/budgets")
        .then((response) => {
          this.budgets = response.data;
        })
        .catch((error) => {
          console.error("Error fetching budgets:", error);
          alert("Failed to fetch budgets.");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    editBudget(id) {
      this.$router.push(`/edit-budget/${id}`);
    },
    deleteBudget(id) {
      if (confirm("Are you sure you want to delete this budget?")) {
        apiClient
          .delete(`/budgets/${id}`)
          .then(() => {
            this.budgets = this.budgets.filter((budget) => budget.id !== id);
          })
          .catch((error) => {
            console.error("Error deleting budget:", error);
            alert("Failed to delete budget.");
          });
      }
    },
    viewProgress(id) {
      this.$router.push(`/budgets/progress/${id}`);
    },
  },
};
</script>

<style scoped>
.manage-budgets {
  margin: 20px;
}

.spinner-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100px;
}

.table th {
  background-color: #f5f5f5;
  text-align: left;
  font-weight: bold;
}

.table td {
  text-align: left;
}

.me-2 {
  margin-right: 10px;
}
.btn-info {
  margin-left: 10px;
}
</style>
