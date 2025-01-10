<template>
    <div class="container my-4">
      <h2>Reports</h2>
      <div class="row">
        <div class="col-md-6">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Expenses Summary</h5>
              <button @click="viewExpensesSummary" class="btn btn-primary">View</button>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Expenses Over Time</h5>
              <button @click="viewExpensesOverTime" class="btn btn-primary">View</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "ReportsView",
    methods: {
      viewExpensesSummary() {
        this.$router.push("/expenses-summary");
      },
      viewExpensesOverTime() {
        this.$router.push("/expenses-over-time");
      },
    },
  };
  </script>
  
  <style scoped>
  h2 {
    text-align: center;
    margin-bottom: 30px;
  }
  .card {
    margin-bottom: 20px;
  }
  </style>
  