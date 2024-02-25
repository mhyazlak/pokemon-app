<template>
  <Button class="m-1" @click="createNewTeam">Create Team</Button>
  <Button class="m-1" @click="openTeamsDialog">Existing Teams</Button>
  <InputText id="teamname" v-model="teamname" placeholder="Enter your teamname" />

  <Panel v-if="selectedTeam">
    <div class="grid" id="team-container">
      <div
        v-for="(member, index) in selectedTeam.members"
        class="sm:col-12 md:col-6 lg:col-4 xl:col-2 flex"
      >
        <div class="gallery-card shadow-5">
          <div>
            <div class="flex justify-content-center align-items-center">
              <img alt="user header" class="pokemon-image" :src="member.pokemon.sprite" />
            </div>
          </div>

          <h4 class="pokemon-name">{{ member.pokemon.name }}</h4>
          <div>
            <div class="pokemon-types flex justify-content-around align-items-between flex-row">
              <span class="flex">{{ member.pokemon.type_one }}</span>
              <span class="flex" v-if="member.pokemon.type_two">
                {{ member.pokemon.type_two }}</span
              >
            </div>
          </div>
        </div>
      </div>

      <div
        v-if="selectedTeam.members.length < 6"
        class="sm:col-12 md:col-6 lg:col-4 xl:col-2 flex"
        @click="selectPokemonDialog"
      >
        <div class="gallery-card shadow-5 placeholder-card">
          <div class="flex justify-content-center align-items-center">
            <i class="pi pi-plus" style="font-size: 2rem"></i>
          </div>
          <h4 class="pokemon-name">Add Pokemon</h4>
        </div>
      </div>
    </div>
  </Panel>

  <Button label="Save Team" class="p-button-success" @click="saveTeamConfig"></Button>

  <!-- Dialog for selecting a team -->
  <Dialog
    header="Select a Team"
    v-model:visible="showTeamsDialog"
    :modal="true"
    :dismissableMask="true"
    :draggable="false"
  >
    <Listbox
      v-model="tempSelectedTeam"
      :options="teams"
      optionLabel="name"
      class="w-full md:w-14rem"
      listStyle="max-height:250px"
    />

    <template #footer>
      <Button
        label="Select"
        class="p-button-success"
        @click="confirmSelection"
        :disabled="!tempSelectedTeam"
      />
      <Button label="Cancel" class="p-button-text" @click="closeDialog" />
    </template>
  </Dialog>

  <!-- Dialog for selecting a pokemon -->
  <Dialog
    header="Add a Pokemon"
    v-model:visible="showAddPokemonDialog"
    :modal="true"
    :dismissableMask="true"
    :draggable="false"
  >
    <Listbox
      v-model="selectedPokemon"
      :options="pokemonOptions"
      filter
      optionLabel="name"
      class="w-full md:w-14rem"
      listStyle="max-height:250px"
    />

    <template #footer>
      <Button
        label="Add"
        class="p-button-success"
        @click="addPokemonToTeam"
        :disabled="!selectedPokemon"
      />
      <Button label="Cancel" class="p-button-text" @click="closeAddPokemonDialog" />
    </template>
  </Dialog>
</template>

<script setup>
import { ref } from 'vue'
import { teamService } from '@/services/teamService' // Adjust the path as necessary
import { pokemonService } from '@/services/pokemonService'
import { storeToRefs } from 'pinia'
import { useTeamStore } from '@/stores/teamStore'

const store = useTeamStore()

const showTeamsDialog = ref(false)
const teamname = ref('')

const showAddPokemonDialog = ref(false)
const selectedPokemon = ref(null)
const pokemonOptions = ref([])

const { teams: teams, selectedTeam: selectedTeam } = storeToRefs(store)
const tempSelectedTeam = ref(null)

const openTeamsDialog = async () => {
  const fetchedTeams = await teamService.readTeams()
  store.setTeams(fetchedTeams)
  showTeamsDialog.value = true
}

const createNewTeam = () => {
  teamService.create(teamname.value)
}

const confirmSelection = async () => {
  const team = await teamService.selectTeam(tempSelectedTeam.value.id)
  selectedTeam.value = team
  showTeamsDialog.value = false
}

const saveTeamConfig = async () => {
  console.log(selectedTeam.value)
  teamService.saveTeamConfig(selectedTeam.value)
}

const selectPokemonDialog = async () => {
  const pokemonList = await pokemonService.readAll()
  pokemonOptions.value = pokemonList
  showAddPokemonDialog.value = true
}

const addPokemonToTeam = () => {
  if (selectedTeam.value && selectedPokemon.value && selectedTeam.value.members.length < 6) {
    // Create a new member object based on the selected Pokemon
    const newMember = {
      pokemon: {
        ...selectedPokemon.value, // Spread the selected Pokemon properties here
        sprite: selectedPokemon.value.sprite, // Adjust according to your data structure
        name: selectedPokemon.value.name,
        type_one: selectedPokemon.value.type_one,
        type_two: selectedPokemon.value.type_two
        // Add any other relevant properties of the Pokemon
      }
    }

    // Add the new member to the team
    selectedTeam.value.members.push(newMember)

    // Reset the selectedPokemon
    selectedPokemon.value = null
  }

  // Close the dialog
  showAddPokemonDialog.value = false
}

const closeAddPokemonDialog = () => {
  showAddPokemonDialog.value = false
}
</script>

<script>
export default {
  name: 'PokedexView'
}
</script>

<style scoped lang="scss">
.gallery-card {
  width: 250px;

  img {
    width: 200px;
  }
}
.placeholder-card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer; /* Makes it clear the card is clickable */

  &:hover {
    background-color: #f4f4f4; /* Optional: Style for hover state */
  }
}
</style>
