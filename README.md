# moradores.es

# Introducción

Vamos a construir desde cero un proyecto para leer cómics  mediante navegador. vamos a crear un servicio web para leer tiras de cómics, el servio tendrá las siguientes características:

- Soporte multilenguaje, por defecto se mostraran las viñetas en el idioma que tenga defino el navegador por defecto, se habilitará una opción para poder cambiar al idioma deseado
  - Los texto se cargaran en la viñeta desde un servio externo, estarán en español y se traducirán automáticamente al idioma mostrado, mediante una consulta a otro servicio externo.
  - Se crearán los bocadillos de forma dinámica para envolver correctamente al los textos, el fondo del bocadillo será en blanco y recibirá dos parámetros, posición del bocadillo, que sera el centro del mismo y la posición final de la flecha que apunta al personaje que habla.
  - la fuente del texto imitará en mayúsculas a una escritura a mano, ha de ser de uso libre y clara en su lectura

- Usaremos tecnología de contenedores para envolver los distintos servicios, comunicándose entre ellos mediante llamadas a las distintas APIS o servicios

Para empezar vamos a montar un Frontend al que pasaremos como parámetro un texto posición de bocadillo de texto y posición de la flecha. La tecnología a usar para representar el dibujo sera CSS o SVG svg y el lenguaje de Frontend el que consideres mas apropiado para la tarea teniendo en cuenta rapidez, sencillez y de amplio uso por la comunidad de desarrolladores de software. El dibujo base sera un cuadrado blanco de 2040px de lado, se adaptara a los distintos dispositivos de visualización.

Buenos días, para este proyecto, vamos a utilizar una arquitectura basada en contenedores para soportar la lectura de cómics en varios idiomas. Vamos a dividir el proyecto en varias partes para facilitar su desarrollo y mantenimiento. La tecnología de frontend que utilizaremos será React.js, ya que es ampliamente utilizada, tiene una gran comunidad de desarrolladores y permite una rápida creación de interfaces interactivas. Usaremos SVG para la representación de los dibujos y CSS para el estilo.

Aquí están los pasos iniciales para montar el frontend:

### Paso 1: Configuración del Proyecto

1. **Crear la estructura del proyecto con Create React App:**
   ```bash
   npx create-react-app comic-reader
   cd comic-reader
   ```

2. **Instalar dependencias necesarias:**
   - `react-i18next` para la internacionalización.
   - `axios` para hacer llamadas a APIs externas.
   - `styled-components` para manejar los estilos de manera más dinámica si se desea.
   ```bash
   npm install react-i18next i18next axios styled-components
   ```

### Paso 2: Configuración de Internacionalización

1. **Crear la configuración básica de `i18next`:**

   Crear un archivo `i18n.js` en la carpeta `src`:
   ```js
   // src/i18n.js
   import i18n from 'i18next';
   import { initReactI18next } from 'react-i18next';

   i18n
     .use(initReactI18next)
     .init({
       resources: {
         en: {
           translation: {
             "Welcome to Comic Reader": "Welcome to Comic Reader",
             // más traducciones...
           }
         },
         es: {
           translation: {
             "Welcome to Comic Reader": "Bienvenido al Lector de Cómics",
             // más traducciones...
           }
         },
         // Agrega más idiomas según sea necesario
       },
       lng: navigator.language || 'es',
       fallbackLng: 'es',
       interpolation: {
         escapeValue: false,
       },
     });

   export default i18n;
   ```

2. **Integrar `i18next` en la aplicación:**
   En `src/index.js`:
   ```js
   import React from 'react';
   import ReactDOM from 'react-dom';
   import './index.css';
   import App from './App';
   import './i18n'; // Asegúrate de importar el archivo de configuración

   ReactDOM.render(
     <React.StrictMode>
       <App />
     </React.StrictMode>,
     document.getElementById('root')
   );
   ```

### Paso 3: Creación del Componente Principal

1. **Crear un componente para la visualización de las viñetas:**

   Crear un archivo `ComicPanel.js` en la carpeta `src/components`:
   ```js
   // src/components/ComicPanel.js
   import React from 'react';
   import styled from 'styled-components';

   const Panel = styled.div`
     width: 100%;
     height: 100%;
     background-color: white;
     position: relative;
   `;

   const Bubble = styled.div`
     position: absolute;
     background-color: white;
     border: 1px solid black;
     border-radius: 10px;
     padding: 10px;
     max-width: 200px;
     word-wrap: break-word;
     font-family: 'Comic Sans MS', cursive, sans-serif;
     text-transform: uppercase;
   `;

   const Arrow = styled.div`
     position: absolute;
     width: 0;
     height: 0;
     border-left: 10px solid transparent;
     border-right: 10px solid transparent;
     border-top: 10px solid white;
   `;

   const ComicPanel = ({ text, bubblePosition, arrowPosition }) => {
     return (
       <Panel>
         <Bubble style={{ top: bubblePosition.y, left: bubblePosition.x }}>
           {text}
           <Arrow style={{ top: arrowPosition.y, left: arrowPosition.x }} />
         </Bubble>
       </Panel>
     );
   };

   export default ComicPanel;
   ```

2. **Usar el componente en `App.js`:**

   ```js
   // src/App.js
   import React from 'react';
   import { useTranslation } from 'react-i18next';
   import ComicPanel from './components/ComicPanel';

   const App = () => {
     const { t } = useTranslation();

     const sampleText = t('Welcome to Comic Reader');
     const bubblePosition = { x: '50%', y: '20%' };
     const arrowPosition = { x: '55%', y: '30%' };

     return (
       <div style={{ width: '100vw', height: '100vh' }}>
         <ComicPanel text={sampleText} bubblePosition={bubblePosition} arrowPosition={arrowPosition} />
       </div>
     );
   };

   export default App;
   ```

### Paso 4: Integración con API Externa

En esta parte, crearemos una función para traducir el texto usando un servicio externo (por ejemplo, Google Translate API o cualquier otro servicio de traducción).

1. **Crear una función para traducir texto:**

   Crear un archivo `translationService.js` en la carpeta `src/services`:
   ```js
   // src/services/translationService.js
   import axios from 'axios';

   export const translateText = async (text, targetLang) => {
     const response = await axios.post('URL_DEL_SERVICIO_DE_TRADUCCION', {
       q: text,
       target: targetLang,
     });
     return response.data.translatedText;
   };
   ```

2. **Usar la función en `App.js`:**

   ```js
   // src/App.js
   import React, { useEffect, useState } from 'react';
   import { useTranslation } from 'react-i18next';
   import ComicPanel from './components/ComicPanel';
   import { translateText } from './services/translationService';

   const App = () => {
     const { t, i18n } = useTranslation();
     const [translatedText, setTranslatedText] = useState('');

     useEffect(() => {
       const fetchTranslation = async () => {
         const text = t('Welcome to Comic Reader');
         const translated = await translateText(text, i18n.language);
         setTranslatedText(translated);
       };
       fetchTranslation();
     }, [i18n.language, t]);

     const bubblePosition = { x: '50%', y: '20%' };
     const arrowPosition = { x: '55%', y: '30%' };

     return (
       <div style={{ width: '100vw', height: '100vh' }}>
         <ComicPanel text={translatedText} bubblePosition={bubblePosition} arrowPosition={arrowPosition} />
       </div>
     );
   };

   export default App;
   ```

### Paso 5: Configuración de Contenedores

Para la configuración de contenedores, usaremos Docker. Crearemos un archivo `Dockerfile` para el frontend.

1. **Crear un archivo `Dockerfile` en la raíz del proyecto:**

   ```dockerfile
   # Use an official Node.js runtime as a parent image
   FROM node:14-alpine

   # Set the working directory
   WORKDIR /app

   # Copy package.json and package-lock.json
   COPY package*.json ./

   # Install dependencies
   RUN npm install

   # Copy the rest of the application code
   COPY . .

   # Build the React application
   RUN npm run build

   # Use an official nginx image to serve the built application
   FROM nginx:alpine

   # Copy the built application from the previous stage
   COPY --from=0 /app/build /usr/share/nginx/html

   # Expose port 80
   EXPOSE 80

   # Start nginx
   CMD ["nginx", "-g", "daemon off;"]
   ```

2. **Crear un archivo `docker-compose.yml` para manejar múltiples servicios:**

   ```yaml
   version: '3'
   services:
     web:
       build: .
       ports:
         - "80:80"
   ```

3. **Construir y ejecutar los contenedores:**

   ```bash
   docker-compose up --build
   ```

Esto es un punto de partida para el proyecto. A medida que se avanza, se pueden agregar más funcionalidades, manejar diferentes casos de uso y optimizar la aplicación.
