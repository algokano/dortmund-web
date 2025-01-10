<template>
  <div id="app">
    <!-- Include the Navbar -->
    <AppNavbar
    
    />

    <!-- Main Content -->
    <main>
      <router-view />
    </main>

    <!-- Include the Footer -->
    <AppFooter />
  </div>
</template>

<script>
import AppNavbar from "./components/AppNavbar.vue";
import AppFooter from "./components/AppFooter.vue";

export default {
  name: "App",
  components: {
    AppNavbar,
    AppFooter,
  },
  computed: {
    // isAuthenticated() {
    //   return !!localStorage.getItem("token");
    // },
  },
};
</script>

<style>
/* Global styles */
body {
  margin: 0;
  font-family: Arial, sans-serif;
  background-color: #f8f9fa;
}

main {
  min-height: 80vh;
  padding: 20px;
}

#app {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100vh;
}
</style>
