interface Pokemon {
    id: number
    name: string
    typeOne: Type
    typeTwo?: Type // Made optional since not every Pokemon has a second type
    baseHp: number
    baseAtk: number
    baseDef: number
    baseSpa: number
    baseSpd: number
    baseSpe: number
    baseTotal: number
    frontSprite: string
    backSprite: string
    iconB64: string // Assuming icon_b64 is a base64 encoded image string
    stage: number
}
