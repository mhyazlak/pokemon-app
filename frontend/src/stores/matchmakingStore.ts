import { defineStore } from 'pinia'

export const useMatchmakingStore = defineStore('matchmaking', {
    state: (): MatchmakingState => ({
        connected: false,
        inQueue: false,
        matchFound: false,
        inBattle: false,
        stompClient: null,
        sessionId: null,
        socket: null
    }),
    actions: {
        setConnected(value: boolean) {
            this.connected = value
        },
        setInQueue(value: boolean) {
            this.inQueue = value
        },
        setStompClient(client: any) {
            this.stompClient = client
        },
        clearClient() {
            this.stompClient = null
            this.connected = false
            this.inQueue = false
        }
    }
})
