// src/App.js
import './App.css';
import ComicPanel from './components/ComicPanel';
//import SlideShow from './components/SlideShow';
import BackgroundImage from './components/BackgroundImage';
import React, { useState, useEffect, useCallback } from 'react';
import Thumbnail from './components/Thumbnail';
import { loadConfigStrips } from './components/loadConfigStrips.js';
function App() {

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
    
  const [currentStripIndex, setCurrentStripIndex] = useState(0);
  const [isTimerActive, setIsTimerActive] = useState(true);
  const [isImageLoaded, setIsImageLoaded] = useState(false);
  const nextStrip = useCallback(() => {
    setCurrentStripIndex((prevStrip) => (prevStrip + 1) % strips.length);
  }, [strips.length]);
  const previousStrip = useCallback(() => {
    setCurrentStripIndex((prevStrip) => (prevStrip - 1 + strips.length) % strips.length);
  }, [strips.length]);
  const goToSlide = useCallback((index) => {
    setIsImageLoaded(false); // Reset image loaded state
    setCurrentStripIndex(index);
    setIsTimerActive(false);
    //setIsTimerActive(true);
  }, []);
  // Avanzar automáticamente cada 4 segundos
  useEffect(() => {
    let timer;
    if (isTimerActive) {
      timer = setInterval(nextStrip, 4000);
    }
    return () => clearInterval(timer);
  }, [nextStrip, isTimerActive]);
  // Manejar eventos de teclado
  useEffect(() => {
    const handleKeyDown = (event) => {
      if (event.key === 'ArrowRight' || event.key === ' ') {
        nextStrip();
      } else if (event.key === 'ArrowLeft') {
        previousStrip();
      } else if (event.key === 't') {
        setIsTimerActive((prev) => !prev);
      }
      // Reiniciar el temporizador
      setIsTimerActive(false);      
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [nextStrip, previousStrip]);
  const currentStrip = strips[currentStripIndex];

  if (!strips || strips.length === 0) {
    return <p>Loading...</p>;
  }
  
  return (    
    <div className="App">
      <style>@import url('https://fonts.cdnfonts.com/css/anime-ace');</style>
      <img 
        src={currentStrip.vignette} 
        onLoad={() => setIsImageLoaded(true)} 
        style={{ display: 'none' }} 
        alt="current vignette"
      />
      {isImageLoaded && <BackgroundImage src={currentStrip.vignette} />}
      {isImageLoaded && currentStrip.bubbles.map((bubble, index) => (
        <ComicPanel key={index} bubble={bubble}/>
      ))}
       <div className="thumbnails-container">
        {strips.map((strip, index) => (
          <Thumbnail 
            key={index} 
            src={strip.vignette} 
            onClick={() => goToSlide(index)}
            isActive={index === currentStripIndex}
          />
        ))}
      </div>
    </div>    
  );
}
export default App;