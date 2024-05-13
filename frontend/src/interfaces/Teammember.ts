interface TeamMember {
    id: number
    teamId: number
    pokemonId: number
    valid: boolean
    pokemon: Pokemon
    moveset: Move[]
    selected: boolean
    currentHp: number
    maxHp: number
    ivHp: number
    evHp: number
    currentAtk: number
    atk: number
    ivAtk: number
    evAtk: number
    currentDef: number
    def: number
    ivDef: number
    evDef: number
    currentSpa: number
    spa: number
    ivSpa: number
    evSpa: number
    currentSpd: number
    spd: number
    ivSpd: number
    evSpd: number
    currentSpeed: number
    speed: number
    ivSpeed: number
    evSpeed: number
    natureId: number
    natureName: string // Enum is used as a string
    burned: boolean
    fainted: boolean
}
