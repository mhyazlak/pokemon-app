// axiosConfig.js

import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  withCredentials: true,
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
