interface GameState {
    player: Player
    opponent: Player // Replace 'any' with a more specific type if you know the structure
    round: number
}
