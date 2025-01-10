<template>
    <div class="container">
      <div class="form-wrapper">
        <h2>Login</h2>
        <form @submit.prevent="loginUser">
          <div class="form-group">
            <label for="email">Email</label>
            <input
              type="text"
              id="email"
              class="form-control"
              v-model="email"
              required
            />
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input
              type="password"
              id="password"
              class="form-control"
              v-model="password"
              required
            />
          </div>
          <div class="form-group text-center">
            <button type="submit" class="btn btn-primary">Login</button>
          </div>
        </form>
        <p class="text-center">
          Don't have an account? <router-link to="/signup">Sign Up</router-link>
        </p>
      </div>
    </div>
  </template>
  
  <script>
  import apiClient from "@/api/axios";
  import { authMethods } from "@/store/auth";
  
  export default {
    name: "LogInView",
    data() {
      return {
        email: "testuser@example.com",
        password: "password",
      };
    },
    methods: {
      async loginUser() {
        try {
          const response = await apiClient.post("/users/api/signin", {
            email: this.email,
            password: this.password,
          });
          authMethods.login(response.data?.id);
          authMethods.setId(response.data?.id);
          this.$router.push("/");
        } catch (error) {
          alert("Login failed. Please check your credentials.");
          console.error("Login error:", error);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  h2 {
    text-align: center;
    margin-bottom: 30px;
  }
  .form-wrapper {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  }
  .form-group {
    margin-bottom: 20px;
  }
  </style>
  