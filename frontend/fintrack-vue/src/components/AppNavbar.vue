<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
      <!-- Branding -->
      <router-link class="navbar-brand" to="/">FinTrack</router-link>

      <!-- Navigation Links -->
      <div class="collapse navbar-collapse justify-content-end">
        <ul class="navbar-nav align-items-center">
          <template v-if="authState.isAuthenticated">
            <li class="nav-item">
              <router-link class="nav-link" to="/" active-class="active-tab">Dashboard</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/manage-expenses" active-class="active-tab">Manage Transactions</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/manage-budgets" active-class="active-tab">Manage Budgets</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/reports" active-class="active-tab">Reports</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/insights" active-class="active-tab">Insights</router-link>
            </li>
            <li class="nav-item">
              <button class="btn btn-danger btn-sm logout-btn" @click="logout">Logout</button>
            </li>
          </template>
          <template v-else>
            <li class="nav-item">
              <router-link class="nav-link" to="/login" active-class="active-tab">Login</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/signup" active-class="active-tab">Sign Up</router-link>
            </li>
          </template>
        </ul>
      </div>
    </div>
  </nav>
</template>


<style scoped>
.navbar-nav .logout-btn {
  margin-left: 10px;
  display: flex;
  align-items: center;
  height: 100%; /* Match the height of the nav items */
}

.navbar-nav .nav-link {
  color: #555;
  transition: color 0.3s ease;
}

.navbar-nav .nav-link:hover {
  color: #000;
}

.navbar-nav .nav-link.active-tab {
  color: #ff5a5f; /* Highlight active tab with a different color */
  font-weight: bold;
  border-bottom: 2px solid #ff5a5f;
}
</style>

<script>
import { authState, authMethods } from "@/store/auth";

export default {
  name: "AppNavbar",
  computed: {
    authState() {
      return authState; // Use the reactive auth state
    },
  },
  methods: {
    logout() {
      authMethods.logout(); // Update state and remove token
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.navbar {
  background-color: #f8f9fa;
}
.navbar-brand {
  color: #ff5a5f;
  font-weight: bold;
}
.nav-link {
  color: #555;
}
.nav-link:hover {
  color: #000;
}
</style>
