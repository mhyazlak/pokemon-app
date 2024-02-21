<template>
  <div class="showcase-container">
    <div v-if="selectedEntry" class="pokemon-showcase">
      <img :src="`${selectedEntry.sprite}`" alt="Selected Pokémon" class="pokemon-sprite" />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { usePokemonStore } from '@/stores/pokemonStore'

const store = usePokemonStore()
const selectedEntry = computed(() => store.selectedPokemon)
</script>

<style scoped>
.pokemon-sprite {
  width: 100px; /* Adjust based on your preference */
  height: 100px; /* Adjust based on your preference */
  /* More styles as needed */
}
</style>
