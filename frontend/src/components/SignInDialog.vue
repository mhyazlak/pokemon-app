<template>
    <Dialog
        v-model="isVisible"
        header="Sign In"
        :modal="true"
        :closable="true"
        :dismissableMask="true"
    >
        <div v-if="!isRegistering">
            <!-- Login Form -->
            <div class="p-fluid grid formgrid">
                <div class="field col-12">
                    <label for="username">Username</label>
                    <InputText id="username" v-model="username" placeholder="Enter your username" />
                </div>
                <div class="field col-12">
                    <label for="password">Password</label>
                    <InputText
                        id="password"
                        v-model="password"
                        placeholder="Enter your password"
                        type="password"
                    />
                </div>
                <div class="field col-12">
                    <Button label="Login" @click="login()" />
                    <div class="mt-4" @click="toggleForm">You have no account? Register now</div>
                </div>
            </div>
        </div>

        <div v-else>
            <!-- Registration Form -->
            <div class="p-fluid grid formgrid">
                <div class="field col-12">
                    <label for="newUsername">Username</label>
                    <InputText
                        id="newUsername"
                        v-model="newUsername"
                        placeholder="Choose a username"
                    />
                </div>
                <div class="field col-12">
                    <label for="newPassword">Password</label>
                    <InputText
                        id="newPassword"
                        v-model="newPassword"
                        placeholder="Create a password"
                        type="password"
                    />
                </div>
                <div class="field col-12">
                    <Button label="Register" @click="register()" />
                    <div class="mt-4" @click="toggleForm">Already have an account? Login here</div>
                </div>
            </div>
        </div>
    </Dialog>
</template>

<script lang="ts">
// Import the authentication service
import { authenticationService } from '@/services/authenticationService' // Adjust the path as necessary

export default {
    name: 'SignInDialog',
    data() {
        return {
            isVisible: false, // Controls the visibility of the dialog
            username: '',
            password: '',
            newUsername: '',
            newPassword: '',
            isRegistering: false
        }
    },
    methods: {
        async login() {
            try {
                authenticationService.login(this.username, this.password)
                this.isVisible = false // Close the dialog on successful login
            } catch (error) {
                // Handle login error
            }
        },
        async register() {
            try {
                authenticationService.register(this.newUsername, this.newPassword)
                this.isVisible = false // Close the dialog on successful registration
            } catch (error) {
                // Handle registration error
            }
        },
        toggleForm() {
            this.isRegistering = !this.isRegistering
        },
        showDialog() {
            this.isVisible = true // Method to open the dialog
        }
    }
}
</script>

<style scoped>
.field {
    margin-bottom: 1rem;
}

.mt-4 {
    cursor: pointer;
    color: #007bff; /* Adjust the color to match your theme */
    text-decoration: underline;
}
</style>
