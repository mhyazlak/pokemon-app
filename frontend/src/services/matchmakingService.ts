import { useMatchmakingStore } from '@/stores/matchmakingStore'
import Stomp from 'webstomp-client'

const headers = { 'content-type': 'application/json' }

export const matchmakingService = {
    createClient() {
        const store = useMatchmakingStore()
        if (store.stompClient) {
            console.log('Stomp client already created')
            return
        }

        const apiUrl = import.meta.env.VITE_API_BASE_URL
        const wsProtocol = window.location.protocol === 'https:' ? 'wss' : 'ws'
        const wsUrl = `${wsProtocol}://${new URL(apiUrl).host}/ws`
        const socket = new WebSocket(wsUrl)
        const client = Stomp.over(socket)

        client.connect(
            {},
            (frame) => {
                console.log('Stomp connection established:', frame)
                store.setConnected(true)
                store.setStompClient(client)
                this.joinQueue() // Automatically join queue after connection
            },
            (error) => {
                console.error('Stomp connection error:', error)
                store.setConnected(false)
            }
        )
    },

    joinQueue() {
        this.createClient()

        const store = useMatchmakingStore()
        if (store.stompClient && store.connected) {
            store.stompClient.send('/joinQueue', JSON.stringify({}), headers)
            store.setInQueue(true)
            this.subscribeToQueue()
        }
    },

    subscribeToQueue() {
        const store = useMatchmakingStore()
        if (store.stompClient && store.connected) {
            store.stompClient.subscribe(
                '/queue/reply',
                (message) => {
                    console.log('Received:', message)
                    // Handle received messages
                },
                headers
            )
        }
    },

    closeClient() {
        const store = useMatchmakingStore()
        if (store.stompClient) {
            store.stompClient.disconnect(() => {
                console.log('Stomp connection closed')
                store.clearClient()
            })
        }
    }
}
