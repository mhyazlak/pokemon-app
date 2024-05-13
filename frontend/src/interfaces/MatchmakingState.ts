interface MatchmakingState {
    connected: boolean
    inQueue: boolean
    matchFound: boolean
    inBattle: boolean
    stompClient: any
    sessionId: string | null
    socket: WebSocket | null
}
