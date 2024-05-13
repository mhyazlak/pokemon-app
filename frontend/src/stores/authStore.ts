// stores/authStore.js

import { defineStore } from 'pinia';
import router from '@/router/index';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isLoggedIn: !!sessionStorage.getItem('user-token'),
    showDialog: false,
    targetRoute: null,
  }),
  actions: {
    register() {

    },

    login(token) {
      sessionStorage.setItem('user-token', token);
      this.isLoggedIn = true;
      this.showDialog = false;
      // Check if there's a target route stored
      if (this.targetRoute) {
        router.push(this.targetRoute);
        this.targetRoute = null;

      } else {
        router.push({ name: 'defaultRoute' });
      }
    },
    logout() {
      sessionStorage.removeItem('user-token');
      this.isLoggedIn = false;
    },
    toggleDialog() {
      this.showDialog = !this.showDialog;
    },
    showSignInDialog() {
      this.showDialog = true;
    },
    setTargetRoute(route) {
      this.targetRoute = route;
    },

  }
});
