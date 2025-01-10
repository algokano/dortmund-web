<template>
  <div class="container my-4">
    <h2 class="text-center mb-4">Manage Transactions</h2>
    <div class="mb-3 text-end">
      <button class="btn btn-primary" @click="createTransaction">Create New Transaction</button>
    </div>
    <div v-if="loading" class="spinner-container">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>
    <div v-else>
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Date</th>
            <th>Category</th>
            <th>Amount</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <TransactionItem
            v-for="expense in expenses"
            :key="expense.id"
            :transaction="expense"
            show-actions
            @delete="deleteExpense"
            @edit="editExpense"
          />
          <tr v-if="expenses.length === 0">
            <td colspan="4" class="text-center">No expenses available</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import apiClient from "@/api/axios";
import dayjs from "dayjs";
import { authState } from "@/store/auth";
import TransactionItem from "@/components/TransactionItem.vue";

export default {
  name: "ManageExpenses",
  components: {
    TransactionItem,
  },
  data() {
    return {
      expenses: [],
      loading: false,
    };
  },
  mounted() {
    if (authState.id) {
      this.fetchExpenses(authState.id);
    }
  },
  methods: {
    fetchExpenses(userId) {
      this.loading = true;
      apiClient
        .get(`/transactions/api/user/${userId}`)
        .then((response) => {
          this.expenses = response.data.map((expense) => ({
            ...expense,
            date: dayjs(expense.date).format("DD.MM.YYYY HH:mm"),
            categoryName: expense.category.name,
          }));
        })
        .catch((error) => {
          console.error("Error fetching expenses:", error);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    deleteExpense(transactionId) {
      if (!confirm("Are you sure you want to delete this expense?")) return;

      apiClient
        .delete(`/transactions/api/${transactionId}`)
        .then(() => {
          this.expenses = this.expenses.filter((expense) => expense.id !== transactionId);
        })
        .catch((error) => {
          console.error("Error deleting expense:", error);
        });
    },
    editExpense(transaction) {
      this.$router.push(`/edit-transaction/${transaction.id}`);
    },
    createTransaction() {
      this.$router.push("/create-transaction");
    },
  },
};
</script>

<style scoped>
.table th {
  background-color: #f5f5f5;
  text-align: left;
  font-weight: bold;
}

.spinner-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
}

.btn-success {
  background-color: #28a745;
  border-color: #28a745;
}
</style>
