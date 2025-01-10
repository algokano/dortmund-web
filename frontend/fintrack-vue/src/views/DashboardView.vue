<template>
  <div class="dashboard">
    <h2 class="text-center my-4">Dashboard</h2>
    <div class="container">
      <div class="row">
        <!-- Expense Breakdown Chart -->
        <div class="col-md-6">
          <div class="card mb-4">
            <div class="card-body">
              <h5 class="card-title">Expense Breakdown</h5>
              <div v-if="loading" class="spinner-container">
                <div class="spinner-border text-primary" role="status">
                  <span class="visually-hidden">Loading...</span>
                </div>
              </div>
              <div v-else>
                <div class="chart-container">
                  <canvas id="expenseChart"></canvas>
                </div>
                <div class="mt-4">
                  <p><strong>Top Expense Day in the last 7 days:</strong> {{ topExpenseDay || "No Data" }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Recent Transactions Table -->
        <div class="col-md-6">
          <div class="card mb-4">
            <div class="card-body">
              <h5 class="card-title">Recent Transactions</h5>
              <div class="table-responsive">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>Date</th>
                      <th>Category</th>
                      <th>Amount</th>
                    </tr>
                  </thead>
                  <tbody>
                    <TransactionItem
                      v-for="transaction in transactions"
                      :key="transaction.id"
                      :transaction="transaction"
                    />
                    <tr v-if="transactions.length === 0">
                      <td colspan="3" class="text-center">No transactions available</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/api/axios";
import { authState } from "@/store/auth";
import Chart from "chart.js/auto";
import TransactionItem from "@/components/TransactionItem.vue";
import dayjs from "dayjs";

export default {
  name: "DashboardView",
  components: {
    TransactionItem,
  },
  data() {
    return {
      transactions: [],
      loading: false,
      chart: null,
      topExpenseDay: null,
    };
  },
  mounted() {
    if (authState.id) {
      this.fetchTransactions(authState.id);
    }
  },
  methods: {
    fetchTransactions(userId) {
      this.loading = true;
      apiClient
        .get(`/transactions/api/user/${userId}`)
        .then((response) => {
          // Format transaction data and update chart
          this.transactions = response.data.map((transaction) => ({
          ...transaction,
          date: dayjs(transaction.date).format("DD.MM.YYYY HH:mm"),
          categoryName: transaction.category.name,
          categoryDescription: transaction.category.description,
        }));

          setTimeout(() => {
            this.updateExpenseChart();
          }, 0);
          this.calculateTopExpenseDay();
        })
        .catch((error) => {
          console.error("Error fetching transactions:", error);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    updateExpenseChart() {
      this.$nextTick(() => {
        const canvas = document.getElementById("expenseChart");

        if (!canvas) {
          console.error("Canvas element not found!");
          return;
        }

        const ctx = canvas.getContext("2d");
        if (this.chart) {
          this.chart.destroy();
        }

        const categoryTotals = this.transactions.reduce((acc, transaction) => {
          acc[transaction.category?.name] = (acc[transaction.category?.name] || 0) + transaction.amount;
          return acc;
        }, {});

        const labels = Object.keys(categoryTotals);
        const data = Object.values(categoryTotals);

        this.chart = new Chart(ctx, {
          type: "pie",
          data: {
            labels,
            datasets: [
              {
                data,
                backgroundColor: ["#007bff", "#28a745", "#ffc107", "#17a2b8", "#ff6384"],
              },
            ],
          },
          options: {
            responsive: true,
            plugins: {
              legend: {
                position: "top",
              },
            },
          },
        });
      });
    },
    calculateTopExpenseDay() {
      const dateTotals = this.transactions.reduce((acc, transaction) => {
        const date = transaction.date.split(" ")[0];
        acc[date] = (acc[date] || 0) + transaction.amount;
        return acc;
      }, {});

      if (Object.keys(dateTotals).length === 0) {
        this.topExpenseDay = "No Data";
        return;
      }

      this.topExpenseDay = Object.keys(dateTotals).reduce((maxDate, currentDate) => {
        return dateTotals[currentDate] > dateTotals[maxDate] ? currentDate : maxDate;
      });
    },
  },
};
</script>

<style scoped>
.dashboard {
  margin: 20px;
}

.chart-container {
  width: 100%;
  height: 300px;
}

.table th {
  background-color: #f5f5f5;
  font-weight: bold;
}

.spinner-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
}
</style>
