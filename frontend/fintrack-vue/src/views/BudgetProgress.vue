<template>
    <div v-if="budgetProgress">
      <h2 class="text-center my-4">Budget Progress</h2>
      <div class="container">
        <p><strong>Budget Name:</strong> {{ budgetProgress.budgetName || "N/A" }}</p>
        <p><strong>Allocated Amount:</strong> £{{ budgetProgress.allocatedAmount?.toFixed(2) || "0.00" }}</p>
        <p><strong>Spent Amount:</strong> £{{ budgetProgress.spentAmount?.toFixed(2) || "0.00" }}</p>
        <p><strong>Remaining Amount:</strong> £{{ budgetProgress.remainingAmount?.toFixed(2) || "0.00" }}</p>
        <p><strong>Percentage:</strong> {{ budgetProgress.percentage?.toFixed(2) || "0.00" }}%</p>
        <p><strong>Alert:</strong> {{ budgetProgress.alert || "No alerts" }}</p>
  
        <div class="chart-container">
          <canvas id="progressChart"></canvas>
        </div>
      </div>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </template>
  
  <script>
  import { nextTick } from "vue";
  import apiClient from "@/api/axios";
  import Chart from "chart.js/auto";
  
  export default {
    name: "BudgetProgress",
    data() {
      return {
        budgetProgress: null,
      };
    },
    mounted() {
      const budgetId = this.$route.params.id;
      this.fetchBudgetProgress(budgetId);
    },
    methods: {
      fetchBudgetProgress(budgetId) {
        apiClient
          .get(`/budgets/${budgetId}/progress`)
          .then((response) => {
            this.budgetProgress = response.data;
            nextTick(() => {
              this.renderChart();
            });
          })
          .catch((error) => {
            console.error("Error fetching budget progress:", error);
            alert("Failed to fetch budget progress.");
          });
      },
      renderChart() {
        const ctx = document.getElementById("progressChart").getContext("2d");
        new Chart(ctx, {
          type: "doughnut",
          data: {
            labels: ["Spent", "Remaining"],
            datasets: [
              {
                data: [
                  this.budgetProgress?.spentAmount || 0,
                  this.budgetProgress?.remainingAmount || 0,
                ],
                backgroundColor: ["#FF6384", "#36A2EB"],
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
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
    margin: 0 auto;
  }
  
  p {
    margin: 5px 0;
  }
  
  .chart-container {
    width: 100%;
    height: 300px;
    margin-top: 20px;
  }
  </style>
  