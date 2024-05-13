interface BattleState {
    active: boolean
    gameState: GameState // Replace 'any' with a more specific type if you know the structure
    stompClient: any // Replace 'any' with a specific type if possible
    sessionId: string | null
}
