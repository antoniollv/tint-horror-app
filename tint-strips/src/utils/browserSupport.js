export const isBrowserSupported = () => (
  typeof window !== 'undefined' &&
  typeof document !== 'undefined' &&
  typeof Promise !== 'undefined' &&
  typeof fetch !== 'undefined' &&
  typeof URL !== 'undefined' &&
  typeof URLSearchParams !== 'undefined'
);

export const getUnsupportedBrowserHtml = () => `
  <div style="font-family: Arial, sans-serif; padding: 24px; max-width: 720px; margin: 40px auto;">
    <h1 style="margin-bottom: 12px;">Navegador no compatible</h1>
    <p>Tu navegador no cumple los requisitos mínimos para ejecutar esta aplicación.</p>
    <p>Actualiza a una versión reciente de Chrome, Edge, Firefox o Safari.</p>
  </div>
`;
