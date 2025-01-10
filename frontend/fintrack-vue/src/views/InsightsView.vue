<template>
    <div class="container my-4">
      <h2>Insights</h2>
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">Your Spending Trends</h5>
          <p>Here are insights about your spending habits...</p>
          <ul>
            <li>Top spending category: {{ topCategory }}</li>
            <li>Average monthly spending: ${{ averageSpending }}</li>
          </ul>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "InsightsView",
    data() {
      return {
        topCategory: "",
        averageSpending: 0,
      };
    },
    mounted() {
      axios
        .get("http://your-api-url/getInsights") // Replace with your endpoint
        .then((response) => {
          this.topCategory = response.data.topCategory;
          this.averageSpending = response.data.averageSpending;
        })
        .catch((error) => {
          console.error("Error fetching insights:", error);
        });
    },
  };
  </script>
  
  <style scoped>
  h2 {
    text-align: center;
    margin-bottom: 30px;
  }
  .card {
    margin: 0 auto;
    max-width: 600px;
  }
  </style>
  