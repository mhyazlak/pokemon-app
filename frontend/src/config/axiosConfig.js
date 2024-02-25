// axiosConfig.js

import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080', // Adjust if necessary
  withCredentials: true, // Add this line
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem('user-token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    // Ensure cookies are sent with every request
    config.withCredentials = true;
    return config;
  },
  (error) => Promise.reject(error)
);

export default axiosInstance;
