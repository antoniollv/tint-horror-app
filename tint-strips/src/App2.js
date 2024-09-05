import React, { useState, useEffect } from 'react';
import { loadConfigStrips } from './components/loadConfigStrips.js';

const App = () => {
  const [strips, setStrips] = useState([]);

  useEffect(() => {
    const fetchStrips = async () => {
      const stripsData = await loadConfigStrips();

      // Verificar que stripsData sea un array antes de usarlo
      if (Array.isArray(stripsData)) {
        setStrips(stripsData);
      } else {
        console.error('stripsData is not an array');
      }
    };

    fetchStrips();
  }, []);

  if (!strips || strips.length === 0) {
    return <p>Loading...</p>;
  }

  /* const strips = [
    {
      vignette: '/imgs/tinwebp.svg',
      bubbles: []
    },
    {
      vignette: '/imgs/tinweb1.svg',
      bubbles: [
        { x: '48%', y: '10%', arrowPosition: 'abajo-izquierda', text: '¿Como estas Tint?', fontSize: '24px' }
      ]
    },
    {
      vignette: '/imgs/tinweb2.svg',
      bubbles: []
    },
    {
      vignette: '/imgs/tinweb3.svg',
      bubbles: [
        { x: '27%', y: '20%', arrowPosition: 'derecha-abajo', text: 'Ya veo EXPLOSIVO!', fontSize: '24px' }
      ]
    }
  ];*/


  return (
    <div>
      {strips ? (
        <pre>{JSON.stringify(strips, null, 2)}</pre> // Muestra el contenido formateado en el navegador
      ) : (
        <p>Loading...</p> // Mensaje mientras se carga el contenido
      )}
    </div>
  );
};

export default App;
