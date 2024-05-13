import axios from '@/config/axiosConfig'
import qs from 'qs'
import { storeToRefs } from 'pinia'

import { useBattleStore } from '@/stores/battleStore'

const headers = { 'content-type': 'application/json' }

export const battleService = {}
