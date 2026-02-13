import React, { useRef, useEffect, useImperativeHandle, useState, forwardRef } from 'react';
import styled from 'styled-components';
import SvgLoader from './SvgLoader.jsx';
import BubbleLayer from './BubbleLayer.jsx';

const ImageContainer = styled.div`
  z-index: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: transparent;
  overflow: hidden;
`;

const BackgroundImage = forwardRef(({ src, onLoad, bubbles }, ref) => {
  const containerRef = useRef();
  const [svgRect, setSvgRect] = useState(null);

  useImperativeHandle(ref, () => ({
    getSvgRect: () => svgRect
  }), [svgRect]);

  useEffect(() => {
    let observer;
    let svg;
    function updateRect() {
      svg = containerRef.current?.querySelector('svg');
      if (svg) {
        const rect = svg.getBoundingClientRect();
        setSvgRect(rect);
      }
    }
    updateRect();
    svg = containerRef.current?.querySelector('svg');
    if (svg && window.ResizeObserver) {
      observer = new window.ResizeObserver(updateRect);
      observer.observe(svg);
    }
    window.addEventListener('resize', updateRect);
    // Forzar actualización también cuando cambie el contenido del SVG
    const interval = setInterval(updateRect, 200);
    return () => {
      if (observer && svg) observer.unobserve(svg);
      window.removeEventListener('resize', updateRect);
      clearInterval(interval);
    };
  }, [src]);

  return (
    <>
      <ImageContainer ref={containerRef}>
        <SvgLoader src={src} onLoad={onLoad} />
      </ImageContainer>
      {/* Overlay y burbujas exactamente encima del SVG, usando su bounding rect */}
      {svgRect && bubbles && (
        <div style={{
          position: 'fixed',
          top: svgRect.top,
          left: svgRect.left,
          width: svgRect.width,
          height: svgRect.height,
          pointerEvents: 'none',
          zIndex: 1002
        }}>
          <BubbleLayer bubbles={bubbles} svgRect={svgRect} />
        </div>
      )}
    </>
  );
});

export default BackgroundImage;