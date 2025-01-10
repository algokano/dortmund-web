<template>
  <div class="insights-page container my-4">
    <h2 class="text-center mb-4">Insights</h2>
    <div class="row">
      <!-- Insight Cards -->
      <div class="col-md-4" v-for="(insight, index) in insights" :key="index">
        <div class="card mb-4">
          <div class="card-body">
            <h5 class="card-title">{{ insight.title }}</h5>
            <p class="card-text">{{ insight.description }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- General Tips Section -->
    <div class="general-tips mt-5">
      <h4>General Tips for Better Financial Management</h4>
      <ul>
        <li>Create a monthly budget and stick to it.</li>
        <li>Track your spending habits to identify unnecessary expenses.</li>
        <li>Set financial goals and review them regularly.</li>
        <li>Save at least 20% of your income each month.</li>
        <li>Invest wisely for long-term benefits.</li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "InsightsView",
  data() {
    return {
      insights: [
        {
          title: "Top Spending Category",
          description: "Your top spending category last month was 'Food' with a total of £500. Consider meal prepping to save money.",
        },
        {
          title: "Savings Trend",
          description: "You have saved 15% more this month compared to last month. Keep up the good work!",
        },
        {
          title: "Upcoming Payment",
          description: "Your rent payment of £800 is due on 15th of this month. Ensure funds are available.",
        },
      ],
    };
  },
};
</script>

<style scoped>
.insights-page {
  margin: 20px;
}

.card-title {
  color: #007bff;
  font-weight: bold;
}

.card-text {
  color: #555;
}

.general-tips h4 {
  margin-top: 30px;
  font-weight: bold;
}

.general-tips ul {
  margin-top: 10px;
  padding-left: 20px;
}

.general-tips li {
  margin-bottom: 10px;
  color: #555;
}
</style>
