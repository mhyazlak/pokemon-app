// stores/pokemonStore.js
import { defineStore } from 'pinia';
import { pokemonService } from '@/services/pokemonService';
//import { info, error } from '@/services/toastService';


export const usePokemonStore = defineStore('pokemon', {
  state: () => ({
    pokemonList: [],
    selectedPokemon: null, // Add a property for the selected Pokémon
    search: '',
    sort: 'A-Z',
    typeFilters: [],

  }),
  actions: {
    async fetchPokemons() {

      try {
        const data = await pokemonService.readAll(this.search, this.sort, this.typeFilters);
        this.pokemonList = data;

      } catch (error) {
        console.error('Error fetching pokemons:', error);
      }
    },
    selectPokemon(pokemon) {
      this.selectedPokemon = pokemon;
    },
    deselectPokemon() {
      this.selectedPokemon = null;
    },
    setSearch(value) {
      this.search = value;
    },
    setSort(value) {
      this.sort = value;
    },
    setTypeFilters(typeFilters) {
      this.typeFilters = typeFilters
    },
    resetFilters() {
      this.search = '';
      this.sort = 'A-Z';
      this.typeFilters = [];
    },

  },
});
