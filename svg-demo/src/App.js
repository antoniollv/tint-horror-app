
// src/App.js
import './App.css';
import ComicPanel from './components/ComicPanel';
//import SlideShow from './components/SlideShow';
import BackgroundImage from './components/BackgroundImage';
import styled from 'styled-components';

const Panel = styled.div`
  width: 100%;
  height: 100%;
  background-color: #282c34;
  position: relative;
`;

function App() {
  // Definir una lista de burbujas con sus respectivas posiciones y textos
  const bubbles = [
    { x: '30%', y: '10%', arrowPosition: 'abajo-izquierda', text: 'Hola!, ¿Como estas?' },
    { x: '50%', y: '10%', arrowPosition: 'abajo-derecha', text: '¡Estoy muy bien, gracias!' },
    { x: '40%', y: '25%', arrowPosition: 'izquierda-arriba', text: '¡Que alegria me da escuchar eso!' },
    { x: '50%', y: '50%', arrowPosition: 'abajo-derecha', text: '¡ADIOS!' }
  ];

  const backgroundImage = '/imgs/tinweb1.svg';

  return (    
    <div className="App">      
      <style>@import url('https://fonts.cdnfonts.com/css/anime-ace');</style>
      <Panel>
        <BackgroundImage src={backgroundImage} />
        {bubbles.map((bubble, index) => (
          <ComicPanel key={index} bubble={bubble} />
        ))}
      </Panel>
    </div>    
  );
}

export default App;
