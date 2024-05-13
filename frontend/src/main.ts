import { createApp, ref } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from '@/router/index'
import { toastPlugin } from '@/plugins/piniaPlugins'

import '@/assets/main.scss'
import 'primeflex/primeflex.css'
import 'primevue/resources/themes/lara-light-green/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'

//primevue imports for compoents
import PrimeVue from 'primevue/config'
import Sidebar from 'primevue/sidebar'
import Menubar from 'primevue/menubar'
import Button from 'primevue/button'
import SplitButton from 'primevue/splitbutton'
import InputText from 'primevue/inputtext'
import Panel from 'primevue/panel'
import Dropdown from 'primevue/dropdown'
import FloatLabel from 'primevue/floatlabel'
import ToastService from 'primevue/toastservice'
import Dialog from 'primevue/dialog'
import Card from 'primevue/card'
import Listbox from 'primevue/listbox'

const app = createApp(App)
const pinia = createPinia()

pinia.use(toastPlugin({ app }))

app.use(pinia)
app.use(router)
app.use(PrimeVue)
app.use(ToastService)

//primevue component
app.component('Menubar', Menubar)
app.component('Sidebar', Sidebar)
app.component('Button', Button)
app.component('SplitButton', SplitButton)

app.component('FloatLabel', FloatLabel)
app.component('Dropdown', Dropdown)
app.component('InputText', InputText)
app.component('Panel', Panel)
app.component('Dialog', Dialog)
app.component('Card', Card)
app.component('Listbox', Listbox)

app.mount('#app')
