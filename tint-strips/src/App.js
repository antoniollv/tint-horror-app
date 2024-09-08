import './App.css';
import ComicPanel from './components/ComicPanel';
//import SlideShow from './components/SlideShow';
import BackgroundImage from './components/BackgroundImage';
import React, { useState, useEffect, useCallback } from 'react';
import Thumbnail from './components/Thumbnail';
import { loadConfigStrips } from './components/loadConfigStrips.js';

function App() {

  let imgsPath = process.env.REACT_APP_IMAGE_PATH;
  if (!imgsPath || imgsPath.trim() === "") {
    imgsPath = '/imgs/';
  }

  const [strips, setStrips] = useState([]);
  const [chapter, setChapter] = useState(0);
  const [chapters, setChapters] = useState([]);
  const [currentStripIndex, setCurrentStripIndex] = useState(0);
  const [isTimerActive, setIsTimerActive] = useState(true);
  const [isImageLoaded, setIsImageLoaded] = useState(false);

  // Actualiza la lista de strips cuando cambia el capítulo
  useEffect(() => {
    const fetchStrips = async () => {
      const stripsData = await loadConfigStrips();

      // Verificar que stripsData.strips[chapter].vignettes sea un array antes de usarlo
      if (Array.isArray(stripsData.strips[chapter].vignettes)) {
        setStrips(stripsData.strips[chapter].vignettes);
        setChapters(stripsData.strips);
        setCurrentStripIndex(0); // Reiniciar al primer strip del nuevo capítulo
      } else {
        console.error('stripsData is not an array');
      }
    };

    fetchStrips();
  }, [chapter]); // Agregar `chapter` como dependencia para que se ejecute cuando cambie el capítulo

  const nextStrip = useCallback(() => {
    setCurrentStripIndex((prevStrip) => (prevStrip + 1) % strips.length);
  }, [strips.length]);

  const previousStrip = useCallback(() => {
    setCurrentStripIndex((prevStrip) => (prevStrip - 1 + strips.length) % strips.length);
  }, [strips.length]);

  const goToSlide = useCallback((index) => {
    setIsImageLoaded(false);
    setCurrentStripIndex(index);
    setIsTimerActive(false);
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

  // Actualizar la función selectChapter
  const selectChapter = (chapterIndex) => {
    setChapter(chapterIndex); // Actualiza el capítulo actual
    setCurrentStripIndex(0);  // Reiniciar la viñeta al cambiar de capítulo
  };

  return (    
    <div className="App">
      <style>@import url('https://fonts.cdnfonts.com/css/anime-ace');</style>
      <img 
        src={imgsPath + currentStrip.vignette} 
        onLoad={() => setIsImageLoaded(true)} 
        style={{ display: 'none' }} 
        alt="current vignette"
      />
      {isImageLoaded && <BackgroundImage src={imgsPath + currentStrip.vignette} />}
      {isImageLoaded && currentStrip.bubbles.map((bubble, index) => (
        <ComicPanel key={index} bubble={bubble} />
      ))}
      <div className="thumbnails-container">
        {strips.map((strip, index) => (
          <Thumbnail 
            key={index} 
            src={imgsPath + strip.vignette} 
            onClick={() => goToSlide(index)}
            isActive={index === currentStripIndex}
          />
        ))}
      </div>
      <div className="chapter-selector">
        {chapters.map((_, index) => (
          <div
            key={index}
            className={`chapter ${index === chapter ? 'active' : ''}`}
            onClick={() => selectChapter(index)}
          >
            Chapter {index + 1}
          </div>
        ))}
      </div>
    </div>    
  );
}

export default App;
