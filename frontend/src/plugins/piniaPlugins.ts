export function toastPlugin({ app }) {
    return (context: any) => {
        // Injects the ToastService into every store
        context.store.$toast = app.config.globalProperties.$toast
    }
}
