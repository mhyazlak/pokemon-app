import axios from '@/config/axiosConfig'; // Assuming axiosConfig.js is in the same directory
import qs from 'qs';
import { useToastStore } from '@/stores/toastStore';

const API_URL = "api/team"
export const teamService = {
  async create(name) {

    const teamDTO = {
      name: name
    }

    const toast = useToastStore();
    try {
      await axios.post(`${API_URL}/create`, teamDTO);
      toast.showSuccess('Successfully created a new team')
    } catch (error) {
      toast.showError('Error creating a team');
      console.error('Failed to create a new team', error);
      throw error;
    }
  },

  async readTeams() {
    try {
      const response = await axios.get(`${API_URL}/readTeams`);
      return response.data.root;
    } catch (e) {

    }
  },

  async selectTeam(teamId) {
    try {
      const response = await axios.get(`${API_URL}/selectTeam`, {
        params: {
          teamId: teamId
        }
      })

      return response.data.root;
    } catch (e) {
      console.error(e)
    }
  },

  async saveTeamConfig(team) {
    try {
      const response = await axios.post(`${API_URL}/saveTeamConfig`,
        team
      )
      console.log(response)
      return response.data.success;
    } catch (e) {
      console.error(e)
    }
  }
}