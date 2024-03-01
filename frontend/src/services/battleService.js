import axios from '@/config/axiosConfig'
import qs from 'qs'
import { storeToRefs } from 'pinia'

import { useMatchmakingStore } from '@/stores/matchmakingStore'
import { useBattleStore } from '@/stores/battleStore'

const headers = { "content-type": "application/json" };

export const battleService = {

  handlePlayerActions() {
    const battleStore = useBattleStore()
    const { stompClient: stompClient } = battleStore

    const playerAction = {
      actionType: 'MOVE',
      actionId: 1
    }

    stompClient.value.send(`/battle/${storeSessionId.value}/action`, JSON.stringify(playerAction), headers);

  },


}
