<template>
    <div class="add-transaction">
      <h2 class="text-center my-4">Add Transaction</h2>
      <div class="container">
        <form @submit.prevent="addTransaction">
          <div class="form-group">
            <label for="date">Date:</label>
            <input type="datetime-local" v-model="form.date" class="form-control" required />
          </div>
          <div class="form-group">
            <label for="type">Transaction Type:</label>
            <select v-model="form.type" class="form-control" required>
              <option value="" disabled>Select Type</option>
              <option value="Expense">Expense</option>
              <option value="Income">Income</option>
            </select>
          </div>
          <div class="form-group">
            <label for="category">Category:</label>
            <select v-model="form.categoryId" class="form-control" required>
              <option value="" disabled>Select Category</option>
              <option v-for="category in categories" :key="category.id" :value="category.id">
                {{ category.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label for="amount">Amount:</label>
            <input type="number" v-model="form.amount" class="form-control" step="0.01" required />
          </div>
          <button type="submit" class="btn btn-primary mt-3">Add Transaction</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import apiClient from "@/api/axios";
  
  export default {
    name: "AddTransaction",
    data() {
      return {
        form: {
          date: "",
          type: "",
          categoryId: "",
          amount: "",
        },
        categories: [],
        userId: 1, 
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
          });
      },
      addTransaction() {
        if (!this.form.date || !this.form.type || !this.form.categoryId || !this.form.amount) {
          alert("Please fill out all required fields.");
          return;
        }
  
        const transactionData = {
          ...this.form,
          date: this.formatDate(this.form.date), 
          userId: this.userId,
        };

        console.log('transactionData', transactionData);
  
        apiClient
          .post("/transactions/api", transactionData)
          .then(() => {
            alert("Transaction added successfully!");
            this.resetForm();

            this.$router.push("/manage-expenses");
          })
          .catch((error) => {
            console.error("Error adding transaction:", error);
            alert("Failed to add transaction.");
          });
      },
      formatDate(date) {
        const d = new Date(date);
        const year = d.getFullYear();
        const month = String(d.getMonth() + 1).padStart(2, "0");
        const day = String(d.getDate()).padStart(2, "0");
        const hours = String(d.getHours()).padStart(2, "0");
        const minutes = String(d.getMinutes()).padStart(2, "0");
        const seconds = String(d.getSeconds()).padStart(2, "0");
        return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
      },
      resetForm() {
        this.form = {
          date: "",
          type: "",
          categoryId: "",
          amount: "",
        };
      },
    },
  };
  </script>
  
  <style scoped>
  .add-transaction {
    max-width: 600px;
    margin: 0 auto;
  }
  
  .form-group {
    margin-bottom: 20px;
  }
  
  .btn {
    width: 100%;
  }
  </style>
  