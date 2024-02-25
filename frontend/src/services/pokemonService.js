// Update your import statement in your service files
import axios from '@/config/axiosConfig'; // Assuming axiosConfig.js is in the same directory
import qs from 'qs';

const API_URL = "api/pokemon"
export const pokemonService = {

  async read(id) {
    try {
      const response = await axios.get(`${API_URL}/read`, { params: { id } });
      return response.data.root;
    } catch (error) {
      console.error('Failed to catch pokemon:', error);
      throw error;
    }
  },

  async readAll(search, sort, typeFilters) {
    try {
      const response = await axios.get(`${API_URL}/readAll`, {
        params: {
          search: search,
          sort: sort,
          types: typeFilters
        },
        paramsSerializer: params => qs.stringify(params, { arrayFormat: 'repeat' }) // This will format arrays with repeated keys
      });

      return response.data.root;
    } catch (error) {
      console.error('Failed to catch all pokemon:', error);
      throw error;
    }
  }

}

