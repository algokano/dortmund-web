<template>
    <div class="container my-4">
      <h2>Manage Budgets</h2>
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Name</th>
            <th>Monthly Income</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="budget in budgets" :key="budget.id">
            <td>{{ budget.name }}</td>
            <td>${{ budget.monthlyIncome }}</td>
            <td>{{ budget.startDate }}</td>
            <td>{{ budget.endDate }}</td>
            <td>
              <button @click="editBudget(budget.id)" class="btn btn-warning btn-sm">Edit</button>
              <button @click="deleteBudget(budget.id)" class="btn btn-danger btn-sm">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
      <button @click="createBudget" class="btn btn-success">Add New Budget</button>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "ManageBudgets",
    data() {
      return {
        budgets: [],
      };
    },
    mounted() {
      // Fetch budgets
      axios
        .get("http://your-api-url/getAllBudgets") // Replace with your endpoint
        .then((response) => {
          this.budgets = response.data;
        })
        .catch((error) => {
          console.error("Error fetching budgets:", error);
        });
    },
    methods: {
      createBudget() {
        this.$router.push("/create-budget"); // Route to create a budget
      },
      editBudget(id) {
        this.$router.push(`/edit-budget/${id}`); // Route to edit a budget
      },
      deleteBudget(id) {
        if (confirm("Are you sure you want to delete this budget?")) {
          axios
            .delete(`http://your-api-url/deleteBudget/${id}`) // Replace with your endpoint
            .then(() => {
              this.budgets = this.budgets.filter((budget) => budget.id !== id);
            })
            .catch((error) => {
              console.error("Error deleting budget:", error);
            });
        }
      },
    },
  };
  </script>
  
  <style scoped>
  h2 {
    text-align: center;
    margin-bottom: 30px;
  }
  </style>
  