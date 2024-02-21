<template>
  <div class="filter-container flex flex-column align-items-center">
    <div class="col-12 flex">
      <div class="flex">
        <InputText v-model="searchValue" @input="updateSearch" placeholder="Name or DexNumber" />
      </div>

      <div class="flex ml-2">
        <Dropdown
          class="w-full md:w-14rem"
          :options="sortTypes"
          optionLabel="name"
          optionValue="name"
          placeholder="Sort"
          v-model="selectedSort"
        />
      </div>
      <Button @click="reloadList()">Search</Button>
      <Button @click="resetFilters()">Reset</Button>
    </div>
    <div class="grid-container col-12">
      <div class="grid type-list">
        <div
          v-for="pokemonType in pokemonTypes"
          :key="pokemonType.value"
          @click="updateTypeFilter(pokemonType.value)"
          :data-type="pokemonType.value"
          :class="{
            'type-filter': true,
            selected: typeFilters.includes(pokemonType.value),
            typeFilterDisabled: typeFilterDisabled && !typeFilters.includes(pokemonType.value),
            'sm:col-12': true,
            'md:col-6': true,
            'lg:col-4': true,
            'xl:col-3': true,
            'text-center': true
          }"
        >
          {{ pokemonType.label }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { usePokemonStore } from '@/stores/pokemonStore'

const store = usePokemonStore()
const { search: searchValue, sort: selectedSort, typeFilters: typeFilters } = storeToRefs(store)
const pokemonTypes = [
  { value: 'NORMAL', label: 'Normal' },
  { value: 'FIRE', label: 'Fire' },
  { value: 'WATER', label: 'Water' },
  { value: 'ELECTRIC', label: 'Electric' },
  { value: 'GRASS', label: 'Grass' },
  { value: 'ICE', label: 'Ice' },
  { value: 'FIGHTING', label: 'Fighting' },
  { value: 'POISON', label: 'Poison' },
  { value: 'GROUND', label: 'Ground' },
  { value: 'FLYING', label: 'Flying' },
  { value: 'PSYCHIC', label: 'Psychic' },
  { value: 'BUG', label: 'Bug' },
  { value: 'ROCK', label: 'Rock' },
  { value: 'GHOST', label: 'Ghost' },
  { value: 'DRAGON', label: 'Dragon' },
  { value: 'DARK', label: 'Dark' },
  { value: 'STEEL', label: 'Steel' },
  { value: 'FAIRY', label: 'Fairy' }
]
const sortTypes = [
  { name: 'A-Z' },
  { name: 'Z-A' },
  { name: 'Highest to Lowest' },
  { name: 'Lowest to Highest' }
]

const typeFilterDisabled = computed(() => typeFilters.value.length >= 2)

const updateSearch = (event) => {
  console.log(event)
}

const updateTypeFilter = (pokemonType) => {
  const index = typeFilters.value.indexOf(pokemonType) // Use indexOf to find the index

  if (index === -1) {
    if (typeFilters.value.length < 2) {
      typeFilters.value.push(pokemonType) // Add if not present and less than 2 types
    }
    // Do nothing if array has 2 types and pokemonType is not in the array
  } else {
    // If the type is already included, remove it from the array
    typeFilters.value.splice(index, 1)
  }
}

const reloadList = () => {
  store.fetchPokemons() // Assuming fetchPokemons is an action in your store
}
const resetFilters = () => {
  store.resetFilters() // Assuming fetchPokemons is an action in your store
}

onMounted(() => {})
</script>

<style lang="scss" scoped>
.filter-container {
  border: 1px solid red;
  .type-list {
    background-color: white;
    margin: 0;

    .type-filter {
      border: 1px solid green;
    }
    & .selected {
      background-color: red;
    }

    .typeFilterDisabled {
      filter: grayscale(100%); /* Convert to grayscale */
      /* background-color: gray; Set the background to gray */
    }
  }
}
</style>
