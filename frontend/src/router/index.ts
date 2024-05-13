import { createRouter, createWebHistory } from 'vue-router'
import PokedexView from '../views/pokedex/PokedexView.vue'
import TeamView from '../views/team/TeamView.vue'
import BattleView from '../views/battle/BattleView.vue'

import { usePokemonStore } from '@/stores/pokemonStore'
import { useAuthStore } from '@/stores/authStore'

const routes = [
    {
        path: '/',
        name: 'Pokedex',
        component: PokedexView,
        meta: { showInMenu: true },
        beforeEnter: async (to: any, from: any, next: any) => {
            const store = usePokemonStore()
            if (!store.pokemonList.length) {
                await store.fetchPokemons()
            }
            next(store.selectPokemon(store.pokemonList[0]))
        }
    },
    {
        path: '/team',
        name: 'Team',
        component: TeamView,
        meta: { showInMenu: true, requiresAuth: true }
    },
    {
        path: '/battle',
        name: 'Battle',
        component: BattleView,
        meta: { showInMenu: true, requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.VITE_BASE_URL),
    routes: routes
})

router.beforeEach((to: any, from: any, next: any) => {
    const authStore = useAuthStore()

    if (to.matched.some((record: any) => record.meta.requiresAuth) && !authStore.isLoggedIn) {
        authStore.setTargetRoute(to)
        authStore.toggleDialog()
        next(false)
    } else {
        next()
    }
})
export default router
