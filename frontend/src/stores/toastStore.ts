// src/stores/toastStore.js
import { defineStore } from 'pinia';

export const useToastStore = defineStore('toast', {
  actions: {
    // Generic method to show a toast
    showToast(message, severity) {
      this.$toast.add({
        severity, // 'success', 'info', 'warn', or 'error'
        summary: message.summary,
        detail: message.detail,
        life: 3000, // Duration in milliseconds
      });
    },

    // Convenience methods for different severities
    showSuccess(message) {
      this.showToast(message, 'success');
    },

    showError(message) {
      this.showToast(message, 'error');
    },

    showInfo(message) {
      this.showToast(message, 'info');
    },
  },
});
