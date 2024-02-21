export function toastPlugin({ app }) {
  return (context) => {
    // Injects the ToastService into every store
    context.store.$toast = app.config.globalProperties.$toast;
  };
}
