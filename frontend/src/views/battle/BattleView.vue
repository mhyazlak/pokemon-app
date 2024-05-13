<template>
    <div>
        <h3>Battle</h3>
        <Button @click="joinQueue" :disabled="inQueue">Join Queue</Button>

        <!--<div v-if="connected">Verbunden mit Server</div>
        <div v-if="inQueue">Derzeit in der Queue</div>
        <div v-if="matchFound">Match gefunden</div>-->
    </div>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { ref, onMounted, onUnmounted } from 'vue'
import { useMatchmakingStore } from '@/stores/matchmakingStore'
import { matchmakingService } from '@/services/matchmakingService'

const matchmakingStore = useMatchmakingStore()
const { connected, inQueue, matchFound, inBattle } = storeToRefs(matchmakingStore)
</script>

<script lang="ts">
export default {
    name: 'BattleView'
}

const joinQueue = () => {
    matchmakingService.joinQueue()
}
</script>

<style lang="scss">
.team-container {
    background-color: gray;
    border: 2px black solid;
}
</style>
