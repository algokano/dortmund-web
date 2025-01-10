<template>
    <div class="container">
      <div class="form-wrapper">
        <h2>Sign Up</h2>
        <form @submit.prevent="signupUser">
          <div class="form-group">
            <label for="email">Email</label>
            <input
              type="email"
              id="email"
              class="form-control"
              v-model="email"
              required
            />
          </div>
          <div class="form-group">
            <label for="username">Username</label>
            <input
              type="text"
              id="username"
              class="form-control"
              v-model="username"
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
            <button type="submit" class="btn btn-primary">Sign Up</button>
          </div>
        </form>
        <p class="text-center">
          Already have an account? <router-link to="/login">Login</router-link>
        </p>
      </div>
    </div>
  </template>
  
  <script>
  import apiClient from "@/api/axios";
  import { authMethods } from "@/store/auth";
  
  export default {
    name: "SignupView",
    data() {
      return {
        email: "",
        username: "",
        password: "",
      };
    },
    methods: {
      async signupUser() {
        try {
          const response = await apiClient.post("/users/api", {
            email: this.email,
            username: this.username,
            password: this.password,
          });
          authMethods.login(response.data.id);
          authMethods.setId(response.data.id);
          this.$router.push("/");
        } catch (error) {
          alert("Signup failed. Please try again.");
          console.error("Signup error:", error);
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
  