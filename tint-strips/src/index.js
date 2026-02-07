import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

const isBrowserSupported = () => (
  typeof window !== 'undefined' &&
  typeof document !== 'undefined' &&
  typeof Promise !== 'undefined' &&
  typeof fetch !== 'undefined' &&
  typeof URL !== 'undefined' &&
  typeof URLSearchParams !== 'undefined'
);

const renderUnsupportedBrowserMessage = () => {
  const root = document.getElementById('root');
  if (!root) {
    return;
  }
  root.innerHTML = `
    <div style="font-family: Arial, sans-serif; padding: 24px; max-width: 720px; margin: 40px auto;">
      <h1 style="margin-bottom: 12px;">Navegador no compatible</h1>
      <p>Tu navegador no cumple los requisitos mínimos para ejecutar esta aplicación.</p>
      <p>Actualiza a una versión reciente de Chrome, Edge, Firefox o Safari.</p>
    </div>
  `;
};

if (!isBrowserSupported()) {
  renderUnsupportedBrowserMessage();
} else {
  const root = ReactDOM.createRoot(document.getElementById('root'));
  root.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>
  );

  // If you want to start measuring performance in your app, pass a function
  // to log results (for example: reportWebVitals(console.log))
  // or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
  reportWebVitals();
}
