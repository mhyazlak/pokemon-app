<!-- App.vue -->
<template>
    <div class="app">
        <Toast />
        <Navigation />
        <SignInDialog v-model:visible="showDialog" style="max-width: 500px" />
        <div class="content">
            <router-view></router-view>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@stores/authStore'
import { storeToRefs } from 'pinia'
const authStore = useAuthStore()
const { showDialog: showDialog } = storeToRefs(authStore)
</script>

<script lang="ts">
import Toast from 'primevue/toast'

import Navigation from '@components/Navigation.vue'
import SignInDialog from '@components/SignInDialog.vue'

export default {
    components: {
        Navigation,
        SignInDialog,
        Toast
    }
}
</script>

<style lang="scss">
.app {
    min-height: 100vh;
    position: relative;
    background-color: #f1f1f1;
}

.content {
    margin: 10px;
}
</style>
