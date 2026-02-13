import './App.css';
import ComicPanel from './components/ComicPanel.jsx';
//import SlideShow from './components/SlideShow';
import BackgroundImage from './components/BackgroundImage.jsx';
import React, { useState, useCallback, useEffect, useRef } from 'react';
import ThumbnailList from './components/ThumbnailList.jsx';
import ChapterSelector from './components/ChapterSelector.jsx';
import StatusMessage from './components/StatusMessage.jsx';
import BubbleLayer from './components/BubbleLayer.jsx';
import { getEnvValue } from './utils/env.js';
import { getWrappedIndex } from './utils/stripNavigation.js';
import { useAutoAdvance } from './hooks/useAutoAdvance.js';
import { useKeyboardNavigation } from './hooks/useKeyboardNavigation.js';
import { useStripsData } from './hooks/useStripsData.js';
import { useImagePreload } from './hooks/useImagePreload.js';
import { useStripViewData } from './hooks/useStripViewData.js';

function App() {
  const imgsPath = getEnvValue(
    ['VITE_IMAGE_PATH', 'REACT_APP_IMAGE_PATH'],
    '/imgs/'
  );

  const [chapter, setChapter] = useState(0);
  const [currentStripIndex, setCurrentStripIndex] = useState(0);
  const [isTimerActive, setIsTimerActive] = useState(true);
  const [loadedVignetteSrc, setLoadedVignetteSrc] = useState('');
  const [svgRect, setSvgRect] = useState(null);
  const bgRef = useRef();

  const { strips, chapters, isLoading, error } = useStripsData(chapter);

  useEffect(() => {
    setCurrentStripIndex(0);
  }, [chapter]);

  const nextStrip = useCallback(() => {
    setCurrentStripIndex((prevStrip) => getWrappedIndex(prevStrip, 1, strips.length));
  }, [strips.length]);

  const previousStrip = useCallback(() => {
    setCurrentStripIndex((prevStrip) => getWrappedIndex(prevStrip, -1, strips.length));
  }, [strips.length]);

  const goToSlide = useCallback((index) => {
    setCurrentStripIndex(index);
    setIsTimerActive(false);
  }, []);

  useAutoAdvance({
    isActive: isTimerActive,
    onTick: nextStrip,
    intervalMs: 4000
  });

  useKeyboardNavigation({
    onNext: nextStrip,
    onPrevious: previousStrip,
    onToggleTimer: () => setIsTimerActive((prev) => !prev),
    onAny: () => setIsTimerActive(false)
  });

  const { currentStrip, currentVignetteSrc, thumbnailStrips } = useStripViewData({
    strips,
    currentIndex: currentStripIndex,
    imgsPath
  });

  // Actualizar la función selectChapter
  const selectChapter = (chapterIndex) => {
    setChapter(chapterIndex); // Actualiza el capítulo actual
    setCurrentStripIndex(0);  // Reiniciar la viñeta al cambiar de capítulo
  };

  const { isLoaded: isImageLoaded, handleLoad: handleImageLoad } = useImagePreload(currentVignetteSrc);

  useEffect(() => {
    setLoadedVignetteSrc('');
  }, [currentVignetteSrc]);

  if (isLoading) {
    return <StatusMessage title="Cargando" message="Cargando viñetas..." />;
  }

  if (error) {
    return <StatusMessage title="Error" message={error} />;
  }

  if (!strips || strips.length === 0) {
    return <StatusMessage title="Sin datos" message="No hay viñetas para mostrar." />;
  }

  return (
    <div className="App">
      <style>@import url('https://fonts.cdnfonts.com/css/anime-ace');</style>
      <img
        src={currentVignetteSrc}
        onLoad={handleImageLoad}
        style={{ display: 'none' }}
        alt="current vignette"
      />
      {isImageLoaded && (
        <BackgroundImage
          ref={bgRef}
          key={currentVignetteSrc}
          src={currentVignetteSrc}
          onLoad={() => {
            requestAnimationFrame(() => {
              requestAnimationFrame(() => {
                setLoadedVignetteSrc(currentVignetteSrc);
                setTimeout(() => {
                  if (bgRef.current && bgRef.current.getSvgRect) {
                    setSvgRect(bgRef.current.getSvgRect());
                  }
                }, 100);
              });
            });
          }}
        />
      )}
      {loadedVignetteSrc === currentVignetteSrc && (
        <BubbleLayer bubbles={currentStrip.bubbles} svgRect={svgRect} />
      )}
      <ThumbnailList
        strips={thumbnailStrips}
        currentIndex={currentStripIndex}
        onSelect={goToSlide}
      />
      <ChapterSelector
        chapters={chapters}
        currentChapter={chapter}
        onSelect={selectChapter}
      />
    </div>
  );
}

export default App;