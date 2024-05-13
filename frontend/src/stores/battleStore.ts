import { defineStore } from 'pinia'

export const useBattleStore = defineStore('battle', {
    state: (): BattleState => ({
        active: false,
        gameState: null,
        stompClient: null,
        sessionId: null
    }),
    actions: {
        setStompClient(client: any) {
            // Specify the parameter type if known
            this.stompClient = client
        },

        setGameState(gameState: any) {
            // Specify the parameter type if known
            this.gameState = gameState
        },

        setSessionId(sessionId: string) {
            this.sessionId = sessionId
        }
    }
})
