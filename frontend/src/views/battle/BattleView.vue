<template>
    <div>
        <h3>Battle</h3>
        <Button @click="joinQueue" :disabled="!connected">Join Queue</Button>

        <div v-if="connected">Verbunden mit Server</div>
        <div v-if="inQueue">Derzeit in der Queue</div>
        <div v-if="matchFound">Match gefunden</div>

        <Button @click="acceptMatch" :disabled="!matchFound">Accept Match</Button>

        <div v-if="inBattle">Match startet...</div>

        <div v-if="gameState">
            <div v-if="gameState">Round: {{ gameState.round }}</div>

            <!-- Display the first Pokémon of each team -->
            <div v-if="gameState" class="grid">
                <div
                    v-for="(player, index) in gameState.player_list"
                    :key="index"
                    class="team-container m-3"
                >
                    <h4>{{ player.user.username }}'s Team</h4>
                    <img
                        :src="player.team.members[0].pokemon.sprite"
                        :alt="player.team.members[0].pokemon.name"
                    />
                    <p>{{ player.team.members[0].pokemon.name }}</p>
                    <Button
                        v-for="(move, index) in player.team.members[0].moveset"
                        :key="index"
                        @click="handlePlayerActions(move.id)"
                        :disabled="!gameState"
                        >{{ move.name }}</Button
                    >
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useMatchmakingStore } from '@/stores/matchmakingStore'
import { matchmakingService } from '@/services/matchmakingService'
import { useBattleStore } from '@/stores/battleStore'
import { battleService } from '@/services/battleService'

const matchmakingStore = useMatchmakingStore()
const battleStore = useBattleStore()

const {
    connected: connected,
    inQueue: inQueue,
    matchFound: matchFound,
    inBattle: inBattle
} = storeToRefs(matchmakingStore)

const { gameState: gameState } = storeToRefs(battleStore)

onMounted(() => {
    matchmakingStore.initializeWebSocket()
    connect(connected.value)
})
</script>

<script>
export default {
    name: 'BattleView'
}

const acceptMatch = () => {
    matchmakingService.acceptMatch()
}

const joinQueue = () => {
    matchmakingService.joinQueue()
}

const connect = (connected) => {
    if (!connected) {
        matchmakingService.connect()
    }
}

const handlePlayerActions = (moveId) => {
    console.log(moveId)
    battleService.handlePlayerActions()
}
</script>

<style lang="scss">
.team-container {
    background-color: gray;
    border: 2px black solid;
}
</style>
