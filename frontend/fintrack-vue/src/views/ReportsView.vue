<template>
  <div class="reports">
    <h2 class="text-center my-4">Reports</h2>
    <div class="container">
      <!-- Expense by Category -->
      <div class="card mb-4">
        <div class="card-body">
          <h5 class="card-title">Expense by Category</h5>
          <div class="chart-container">
            <canvas id="categoryChart"></canvas>
          </div>
        </div>
      </div>

      <!-- Expense Over Time -->
      <div class="card mb-4">
        <div class="card-body">
          <h5 class="card-title">Expense Over Time</h5>
          <div class="chart-container">
            <canvas id="timeChart"></canvas>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/api/axios";
import Chart from "chart.js/auto";
import dayjs from "dayjs";

export default {
  name: "ReportsView",
  data() {
    return {
      transactions: [],
    };
  },
  mounted() {
    this.fetchTransactions();
  },
  methods: {
    fetchTransactions() {
      apiClient
          .get("/transactions/api")
          .then((response) => {
            this.transactions = response.data;
            this.renderCategoryChart();
            this.renderTimeChart();
          })
          .catch((error) => {
            console.error("Error fetching transactions:", error);
          });
    },
    renderCategoryChart() {
      const categoryData = this.transactions.reduce((acc, transaction) => {
        if (transaction.type === "Expense") {
          acc[transaction.category.name] =
              (acc[transaction.category.name] || 0) + transaction.amount;
        }
        return acc;
      }, {});

      const ctx = document.getElementById("categoryChart").getContext("2d");
      new Chart(ctx, {
        type: "bar",
        data: {
          labels: Object.keys(categoryData),
          datasets: [
            {
              label: "Amount",
              data: Object.values(categoryData),
              backgroundColor: "rgba(75, 192, 192, 0.5)",
              borderColor: "rgba(75, 192, 192, 1)",
              borderWidth: 1,
            },
          ],
        },
        options: {
          responsive: true,
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    },
    renderTimeChart() {
      const timeData = this.transactions.reduce((acc, transaction) => {
        if (transaction.type === "Expense") {
          const month = dayjs(transaction.date).format("YYYY-MM");
          acc[month] = (acc[month] || 0) + transaction.amount;
        }
        return acc;
      }, {});

      const ctx = document.getElementById("timeChart").getContext("2d");
      new Chart(ctx, {
        type: "line",
        data: {
          labels: Object.keys(timeData),
          datasets: [
            {
              label: "Amount",
              data: Object.values(timeData),
              backgroundColor: "rgba(54, 162, 235, 0.5)",
              borderColor: "rgba(54, 162, 235, 1)",
              borderWidth: 2,
              fill: true,
            },
          ],
        },
        options: {
          responsive: true,
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    },
  },
};
</script>

<style scoped>
.reports {
  margin: 20px;
}
.chart-container {
  width: 100%;
  height: 300px;
}
.card-title {
  text-align: center;
  font-weight: bold;
}
</style>