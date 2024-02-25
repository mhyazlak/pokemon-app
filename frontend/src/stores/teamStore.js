// stores/teamStore.js
import { defineStore } from 'pinia';
import { computed } from 'vue';
import { isEqual, cloneDeep } from 'lodash';

export const useTeamStore = defineStore('team', {
  state: () => ({
    selectedTeam: null,
    teams: [],
    originalTeams: [] // Added to keep a deep clone of the original teams
  }),
  getters: {
    isDirty() {
      // This getter will check if teams are dirty (have been modified)
      return !isEqual(this.teams, this.originalTeams);
    }
  },
  actions: {
    setTeams(teams) {
      this.teams = teams;
      // When teams are set, also set the originalTeams to a deep clone of them
      this.originalTeams = cloneDeep(teams);
    },
    resetTeams() {
      // Reset teams to the originalTeams
      this.teams = cloneDeep(this.originalTeams);
    },
    markAsClean() {
      // Call this method when you save changes and want to set the current state as the new 'clean' state
      this.originalTeams = cloneDeep(this.teams);
    }
  },
});
