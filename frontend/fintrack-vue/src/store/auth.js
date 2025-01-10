import { reactive } from "vue";

// Reactive state for authentication
export const authState = reactive({
  isAuthenticated: !!localStorage.getItem("token"),
  id: localStorage.getItem("userId"),
});

// Methods to update the state
export const authMethods = {
  login(token, id) {
    localStorage.setItem("token", token);
    localStorage.setItem("userId", id);
    authState.isAuthenticated = true;
    authState.id = id;
  },
  setId(id) {
    localStorage.setItem("userId", id);
    authState.id = id;
  },
  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    authState.isAuthenticated = false;
    authState.id = null;
  },
};
