import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import { toastPlugin } from '@/plugins/piniaPlugins';


import '@/assets/main.scss'
import "primeflex/primeflex.css";
import "primevue/resources/themes/lara-light-green/theme.css";
import "primevue/resources/primevue.min.css"; /* Deprecated */
import "primeicons/primeicons.css";


//primevue imports for compoents
import PrimeVue from "primevue/config";
import Sidebar from "primevue/sidebar";
import Menubar from 'primevue/menubar';
import Button from 'primevue/button';
import InputText from 'primevue/inputtext';
import Dropdown from 'primevue/dropdown';
import FloatLabel from 'primevue/floatlabel';
import ToastService from 'primevue/toastservice';


const app = createApp(App)
const pinia = createPinia();
pinia.use(toastPlugin({ app }));

app.use(pinia);
app.use(router)
app.use(PrimeVue);
app.use(ToastService)

//primevue component
app.component('Menubar', Menubar);
app.component('Sidebar', Sidebar);
app.component('Button', Button);
app.component('FloatLabel', FloatLabel);
app.component('Dropdown', Dropdown);
app.component('InputText', InputText);

app.mount('#app')

