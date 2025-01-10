<template>
  <tr>
    <td>{{ transaction.date }}</td>
    <td>{{ transaction.categoryName }}</td>
    <td>£{{ transaction.amount.toFixed(2) }}</td>
    <td v-if="showActions">
      <button @click="onEdit" class="btn btn-primary btn-sm">Edit</button>
      <button @click="onDelete" class="btn btn-danger btn-sm ms-2">Delete</button>
    </td>
  </tr>
</template>

<script>
export default {
  name: "TransactionItem",
  props: {
    transaction: {
      type: Object,
      required: true,
    },
    showActions: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    onEdit() {
      this.$emit("edit", this.transaction);
    },
    onDelete() {
      this.$emit("delete", this.transaction.id);
    },
  },
};
</script>

<style scoped>

</style>
