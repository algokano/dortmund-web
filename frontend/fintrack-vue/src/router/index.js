import { createRouter, createWebHistory } from "vue-router";
import DashboardView from "../views/DashboardView.vue";
import ManageExpenses from "../views/ManageExpenses.vue";
import ManageBudgets from "../views/ManageBudgets.vue";
import ReportsView from "../views/ReportsView.vue";
import InsightsView from "../views/InsightsView.vue";
import LogInView from "../views/LogInView.vue";
import SignUpView from "../views/SignupView.vue";
import AddTransaction from "../views/AddTransaction.vue";
import EditTransaction from "../views/EditTransaction.vue";
import EditBudget from "../views/EditBudget.vue";
import CreateBudget from "../views/CreateBudget.vue";
import BudgetProgress from "../views/BudgetProgress.vue";

const routes = [
  { path: "/", name: "Dashboard", component: DashboardView, meta: { requiresAuth: true }, },
  { path: "/login", name: "Login", component: LogInView },
  { path: "/signup", name: "Signup", component: SignUpView },
  { path: "/manage-expenses", name: "ManageExpenses", component: ManageExpenses, meta: { requiresAuth: true }, },
  { path: "/manage-budgets", name: "ManageBudgets", component: ManageBudgets, meta: { requiresAuth: true }, },
  { path: "/reports", name: "Reports", component: ReportsView, meta: { requiresAuth: true }, },
  { path: "/insights", name: "Insights", component: InsightsView, meta: { requiresAuth: true }, },
  { path: "/create-transaction", name: "AddTransaction", component: AddTransaction, meta: { requiresAuth: true }, },
  { path: "/create-budget", name: "CreateBudget", component: CreateBudget, meta: { requiresAuth: true }, },
  {
    path: "/edit-transaction/:id",
    name: "EditTransaction",
    component: EditTransaction,
  },
  {
    path: "/edit-budget/:id",
    name: "EditBudget",
    component: EditBudget,
  },
  {
    path: "/budgets/progress/:id",
    name: "BudgetProgress",
    component: BudgetProgress,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
    const isAuthenticated = !!localStorage.getItem("token"); // Check if token exists
  
    if (to.matched.some((record) => record.meta.requiresAuth) && !isAuthenticated) {
      // Redirect to login if the user is not authenticated
      next("/login");
    } else {
      next(); // Proceed to the route
    }
  });

export default router;
