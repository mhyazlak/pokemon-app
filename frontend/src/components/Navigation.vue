<template>
  <div v-if="!isMobile" class="card">
    <Menubar :model="menuItems">
      <template #end>
        <!-- Login Button with icon -->
        <Button v-if="!isLoggedIn" label="SignIn" icon="pi pi-sign-in" @click="showSignInDialog" />
        <!-- Logout Button with icon and a different style -->
        <Button
          v-if="isLoggedIn"
          label="Logout"
          icon="pi pi-sign-out"
          class="p-button-secondary"
          @click="performLogout"
        />
      </template>
    </Menubar>
  </div>
  <div v-if="isMobile" class="card">
    <Menubar>
      <template #end>
        <span class="pi pi-bars" @click="toggleSidebar" style="padding: 12px"></span>
      </template>
    </Menubar>
  </div>
  <div :visible="isMobile" class="card flex justify-content-center">
    <Sidebar v-model:visible="sidebarVisible" header="Pokemon">
      <!--<Menubar :model="menuItems" />-->
    </Sidebar>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'

import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore()
const { isLoggedIn: isLoggedIn } = storeToRefs(authStore)

const sidebarVisible = ref(false)
const menuItems = ref([])
const router = useRouter()
const isMobile = ref(window.innerWidth <= 960)

const toggleSidebar = () => {
  sidebarVisible.value = !sidebarVisible.value
}

const showSignInDialog = () => {
  //router.push({ name: 'SignIn' }) // Use the correct name for your login route
}

const performLogout = () => {
  authStore.logout() // Perform logout action
  router.push({ name: 'Pokedex' }) // Navigate to login page after logout
}

// Update isMobile and sidebarVisible based on window width
const resizeHandler = () => {
  const currentWidth = window.innerWidth
  isMobile.value = currentWidth <= 960

  // Hide sidebar when the window is resized to wider than mobile width
  if (!isMobile.value) {
    sidebarVisible.value = false
  }
}

// Setup resize event listener
window.addEventListener('resize', resizeHandler)

// Cleanup event listener when component is unmounted
onUnmounted(() => {
  window.removeEventListener('resize', resizeHandler)
})

// Populate menu items from routes
onMounted(() => {
  menuItems.value = router
    .getRoutes()
    .filter((r) => r.meta && r.meta.showInMenu)
    .map((r) => ({
      label: r.meta.title || r.name,
      icon: r.meta.icon || 'pi pi-fw pi-file',
      command: () => {
        if (isMobile.value) {
          sidebarVisible.value = false
        }
        router.push(r.path)
      }
    }))
})
// Initialize isMobile on mount
resizeHandler()
</script>

<style scoped></style>
