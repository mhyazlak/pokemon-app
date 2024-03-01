import axios from '@/config/axiosConfig'
import qs from 'qs'
import { storeToRefs } from 'pinia'

import { useMatchmakingStore } from '@/stores/matchmakingStore'
import { useBattleStore } from '@/stores/battleStore'
import Stomp from 'webstomp-client'

const headers = { "content-type": "application/json" }; // Ensure the server knows you're sending JSON
//const headers_plain = { "conent-type": "text/plain" }

export const matchmakingService = {

  // connecting to channel
  connect() {
    const matchmakingStore = useMatchmakingStore()
    const {
      stompClient: stompClient,
      socket: socket,
      connected: connected,
    } = storeToRefs(matchmakingStore)

    stompClient.value = Stomp.over(socket, { debug: true })

    stompClient.value.connect(
      {},
      (frame) => {
        connected.value = true;
      },
      (error) => {
        console.error(error)
      }
    )
  },

  joinQueue() {
    const matchmakingStore = useMatchmakingStore()
    const {
      stompClient: stompClient,
      inQueue: inQueue
    } = storeToRefs(matchmakingStore)

    inQueue.value = true;

    stompClient.value.subscribe('/user/queue/match', (message) => {

      this.handleQueueResponse(message)
    }, headers);

    stompClient.value.subscribe('/user/battle', (message) => {

      this.matchStarts(message)
    }, headers)

    stompClient.value.send('/joinQueue', {}, headers);

  },

  handleQueueResponse(message) {
    const matchmakingStore = useMatchmakingStore()
    const {
      matchFound: matchFound,
      sessionId: sessionId,
      inBattle: inBattle
    } = storeToRefs(matchmakingStore)

    const queueResponse = JSON.parse(message.body);



    if (queueResponse.queueStatus === "MATCH_FOUND" && queueResponse.sessionId) {
      sessionId.value = queueResponse.sessionId;
      matchFound.value = true;
    }
    if (queueResponse.queueStatus === "MATCH_STARTS" && queueResponse.sessionId) {

      inBattle.value = true;
    }
  },

  acceptMatch(decision) {
    const matchmakingStore = useMatchmakingStore()
    const { stompClient, sessionId: storeSessionId } = storeToRefs(matchmakingStore)



    stompClient.value.send(`/acceptMatch/${storeSessionId.value}`, JSON.stringify('ACCEPT'), headers);

  },

  matchStarts(message) {
    const battleStore = useBattleStore();
    const matchmakingStore = useMatchmakingStore();

    // Extract the stompClient and sessionId from the matchmakingStore
    const { stompClient: mmStompClient } = matchmakingStore;

    // Extract the gameState from the battleStore
    const { gameState: gameState } = battleStore;

    const initGameState = JSON.parse(message.body);

    // Update the gameState
    battleStore.setGameState(initGameState)

    // Set the stompClient in the battleStore by calling the action
    battleStore.setStompClient(mmStompClient.value);
  }

}
