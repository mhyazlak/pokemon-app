import { defineStore } from 'pinia';

export const useBattleStore = defineStore('battle', {
  state: () => ({
    active: false,
    gameState: null,
    stompClient: null
  }),
  actions: {
    setStompClient(client) {
      this.stompClient = client;
    },

    setGameState(gameState) {
      this.gameState = gameState;
    }

  }
});
