import React, { useRef, useEffect, useImperativeHandle, useState, forwardRef } from 'react';
import styled from 'styled-components';
import SvgLoader from './SvgLoader.jsx';

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

const BackgroundImage = forwardRef(({ src, onLoad }, ref) => {
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
    // Llamar al cargar el src
    updateRect();
    // Observar cambios de tamaño del SVG
    svg = containerRef.current?.querySelector('svg');
    if (svg && window.ResizeObserver) {
      observer = new window.ResizeObserver(updateRect);
      observer.observe(svg);
    }
    // Listener de resize de ventana
    window.addEventListener('resize', updateRect);
    return () => {
      if (observer && svg) observer.unobserve(svg);
      window.removeEventListener('resize', updateRect);
    };
  }, [src]);

  return (
    <ImageContainer ref={containerRef}>
      <SvgLoader src={src} onLoad={onLoad} />
    </ImageContainer>
  );
});

export default BackgroundImage;