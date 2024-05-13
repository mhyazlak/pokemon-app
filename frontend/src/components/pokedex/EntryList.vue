<template>
    <div class="grid">
        <div v-for="pokemon in pokemonList" class="sm:col-12 md:col-6 lg:col-4 xl:col-3 flex">
            <div class="gallery-card shadow-6">
                <div>
                    <div class="flex justify-content-center align-items-center">
                        <img alt="user header" class="pokemon-image" :src="pokemon.sprite" />
                    </div>
                </div>
                <small class="pokemon-number">{{ formatDexNumber(pokemon.id) }}</small>
                <h4 class="pokemon-name">{{ pokemon.name }}</h4>
                <div>
                    <div
                        class="pokemon-types flex justify-content-around align-items-between flex-row"
                    >
                        <span class="flex">{{ pokemon.type_one }}</span>
                        <span class="flex" v-if="pokemon.type_two"> {{ pokemon.type_two }}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { usePokemonStore } from '@/stores/pokemonStore'

const store = usePokemonStore()
const { pokemonList, selectPokemon } = store

// Local state for the ListBox v-model
const selectedPokemon = ref(null)

// Method to handle selection changes and update the store
const formatDexNumber = (number) => {
    return number.toString().padStart(3, '0')
}

selectedPokemon.value = store.selectedPokemon
</script>

<style lang="scss" scoped>
.gallery-card {
    margin: auto; /* This centers the card in the flex item */
    min-width: 200px;
    margin-top: 1rem;
    background-color: gray;
    border-radius: 5px;

    .pokemon-image {
        margin: 0.25rem;
        width: 180px;
        background: rgb(243, 243, 243);
    }
    .pokemon-name,
    .pokemon-number {
        margin: 0.25rem 1rem;
    }
    .pokemon-types {
        margin: 0.25rem 1rem;
    }
}
</style>
