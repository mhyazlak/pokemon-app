import { createRouter, createWebHistory } from 'vue-router';
import PokedexView from '../views/pokedex/PokedexView.vue';
import TeamView from '../views/team/TeamView.vue';
import BattleView from '../views/battle/BattleView.vue';

import { usePokemonStore } from '@/stores/pokemonStore';
import { useAuthStore } from '@/stores/authStore';

const routes = [
  {
    path: '/',
    name: 'Pokedex',
    component: PokedexView,
    meta: { showInMenu: true },
    beforeEnter: async (to, from, next) => {
      const store = usePokemonStore();
      if (!store.pokemonList.length) {
        await store.fetchPokemons();
      }
      next(store.selectPokemon(store.pokemonList[0]));
    }
  },
  {
    path: '/team',
    name: 'Team',
    component: TeamView,
    meta: { showInMenu: true, requiresAuth: true },
  },
  {
    path: '/battle',
    name: 'Battle',
    component: BattleView,
    meta: { showInMenu: true, requiresAuth: true },
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  if (to.matched.some(record => record.meta.requiresAuth) && !authStore.isLoggedIn) {
    authStore.setTargetRoute(to); // Store the target route
    authStore.toggleDialog(); // Show the login dialog
    next(false); // Cancel the navigation
  } else {
    next();
  }
});
export default router;
