import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/pokemon';

export async function read(id) {
  try {
    const response = await axios.get(`${BASE_URL}/read`, { params: { id } });
    return response.data.root;
  } catch (error) {
    console.error('Failed to catch pokemon:', error);
    throw error; // Rethrow the error to handle it in the calling context
  }
}

export async function readAll(search, sort, typeFilters) {
  try {
    // Use a POST request if you want to send data in the request body
    const response = await axios.post(`${BASE_URL}/readAll`, {
      search,
      sort,
      typeFilters,
    });

    return response.data.root;
  } catch (error) {
    console.error('Failed to catch all pokemon:', error);
    throw error; // Rethrow the error to handle it in the calling context
  }
}
