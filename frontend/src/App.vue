<!-- App.vue -->
<template>
  <Toast />
  <Navigation />
  <div class="content">
    <router-view style=""></router-view>
  </div>
</template>

<script>
import Navigation from './components/Navigation.vue'
import Toast from 'primevue/toast'

export default {
  components: {
    Navigation,
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
