import { defineStore } from 'pinia';

export const useMatchmakingStore = defineStore('matchmaking', {
  state: () => ({
    connected: false,
    inQueue: false,
    matchFound: false,
    inBattle: false,
    stompClient: null,
    sessionId: null,
    socket: null,
  }),
  actions: {
    initializeWebSocket() {
      const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
      const host = window.location.host; // includes hostname and port if available
      const wsUrl = `${protocol}//${host}/ws`;
      this.socket = new WebSocket(wsUrl);
      // Setup WebSocket event listeners here (e.g., onopen, onmessage, onclose, onerror)
    }
  }
});
