<template>
    <div class="container my-4">
      <h2>Expenses Summary</h2>
      <div>
        <h3>By Category</h3>
        <canvas id="expensesByCategory"></canvas>
      </div>
      <div>
        <h3>Over Time</h3>
        <canvas id="expensesOverTime"></canvas>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import Chart from "chart.js/auto";
  
  export default {
    name: "ExpensesSummary",
    mounted() {
      axios
        .get("http://your-api-url/getExpensesSummary") // Replace with your endpoint
        .then((response) => {
          const data = response.data;
          this.createCategoryChart(data.byCategory);
          this.createOverTimeChart(data.overTime);
        })
        .catch((error) => {
          console.error("Error fetching expenses summary:", error);
        });
    },
    methods: {
      createCategoryChart(data) {
        const ctx = document.getElementById("expensesByCategory").getContext("2d");
        new Chart(ctx, {
          type: "bar",
          data: {
            labels: data.map((item) => item.category),
            datasets: [
              {
                label: "Amount",
                data: data.map((item) => item.amount),
                backgroundColor: "rgba(54, 162, 235, 0.7)",
              },
            ],
          },
        });
      },
      createOverTimeChart(data) {
        const ctx = document.getElementById("expensesOverTime").getContext("2d");
        new Chart(ctx, {
          type: "line",
          data: {
            labels: data.map((item) => item.date),
            datasets: [
              {
                label: "Amount",
                data: data.map((item) => item.amount),
                backgroundColor: "rgba(75, 192, 192, 0.2)",
                borderColor: "rgba(75, 192, 192, 1)",
              },
            ],
          },
        });
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
  