import axios from '@/config/axiosConfig'; // Assuming axiosConfig.js is in the same directory
import { useAuthStore } from '@/stores/authStore';
import { useToastStore } from '@/stores/toastStore';

const API_URL = 'auth/'; // Adjust this URL as needed


export const authenticationService = {

  async register(username, password) {
    const toast = useToastStore();
    try {
      const response = await axios.post(API_URL + 'register', {
        username,
        password
      });
      const success = response.data.success;
      if (success) {
        toast.showSuccess({ summary: 'Registration successful' })
      }

    } catch (error) {
      toast.showError({ summary: 'Registration failed' })
    }
  },

  async login(username, password) {
    try {
      const response = await axios.post(API_URL + 'login', {
        username,
        password
      });
      const jwt = response.data.root;
      if (jwt) {
        const authStore = useAuthStore(); // Get the store instance
        authStore.login(jwt); // Call the login action
      }
      return jwt; // Return the token
    } catch (error) {
      console.error('Login error:', error.response);
      throw error;
    }
  },

  logout() {
    const authStore = useAuthStore(); // Get the store instance
    authStore.logout(); // Call the logout action
  }
};
