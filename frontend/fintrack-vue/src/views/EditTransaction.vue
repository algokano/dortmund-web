<template>
    <div class="edit-transaction">
      <h2 class="text-center my-4">Edit Transaction</h2>
      <div class="container">
        <form @submit.prevent="updateTransaction">
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
          <button type="submit" class="btn btn-primary mt-3">Update Transaction</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import apiClient from "@/api/axios";
  import dayjs from "dayjs";
  
  export default {
    name: "EditTransaction",
    data() {
      return {
        form: {
          date: "",
          type: "",
          categoryId: "",
          amount: "",
        },
        categories: [],
        transactionId: null,
      };
    },
    mounted() {
      this.transactionId = this.$route.params.id;
      this.fetchCategories();
      this.fetchTransaction(this.transactionId);
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
      fetchTransaction(id) {
        // Fetch all transactions
        apiClient
          .get("/transactions/api")
          .then((response) => {
            const transaction = response.data.find((t) => t.id === Number(id));
            if (!transaction) {
              alert("Transaction not found.");
              return;
            }

            this.form = {
              date: dayjs(transaction.date).format("YYYY-MM-DDTHH:mm"), 
              type: transaction.type,
              categoryId: transaction.category?.id,
              amount: transaction.amount,
            };
          })
          .catch((error) => {
            console.error("Error fetching transactions:", error);
            alert("Failed to fetch transactions.");
          });
      },
      updateTransaction() {
        if (!this.form.date || !this.form.type || !this.form.categoryId || !this.form.amount) {
          alert("Please fill out all required fields.");
          return;
        }
  
        const transactionData = {
          ...this.form,
          date: this.formatDate(this.form.date), 
          userId: 1, 
        };
  
        apiClient
          .put(`/transactions/api/${this.transactionId}`, transactionData)
          .then(() => {
            alert("Transaction updated successfully!");
            this.$router.push("/manage-expenses");
          })
          .catch((error) => {
            console.error("Error updating transaction:", error);
            alert("Failed to update transaction.");
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
    },
  };
  </script>
  
  <style scoped>
  .edit-transaction {
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
  