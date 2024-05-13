<template>
    <h1>Pokedex</h1>
    <div class="flex justify-content-center align-items-center flex-column">
        <div class="main-content">
            <DexFilter></DexFilter>
            <div class="flex entry-list-wrapper"><EntryList /></div>
        </div>
    </div>
</template>

<script lang="ts">
import EntryList from '@components/pokedex/EntryList.vue'
import Showcase from '@components/pokedex/EntryList.vue'
import DexFilter from '@components/pokedex/EntryList.vue'

export default {
    components: {
        EntryList,
        Showcase,
        DexFilter
    }
}
</script>

<style lang="scss">
.main-content {
    height: 80vh;
    width: 60%;
    overflow: hidden;
}

.entry-list-wrapper {
    overflow-y: auto;
    overflow-x: hidden;
    background-color: #2e2e2e;
    border: 2px gray solid;
    height: inherit;
    /* ===== Scrollbar CSS ===== */
    /* Firefox */
}
</style>
