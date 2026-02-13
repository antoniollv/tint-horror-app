// Valor base para el tamaño de fuente
const BASE_FONT_SIZE = 48; // Puedes ajustar este valor según lo que consideres adecuado
// src/components/ComicPanel.js
import React from 'react';
import styled from 'styled-components';
import { arrowPositionStyles } from '../styles/arrowStyles';
import { useTranslatedText } from '../hooks/useTranslatedText.js';

const Bubble = styled.div`
  position: absolute;
  background-color: white;
  border: 1px solid black;
  border-radius: var(--borde);
  --borde: 1em;
  padding: var(--borde);
  z-index: 1;
  filter: drop-shadow(0px 0px 1px black) ;
  max-width: 300px;
  word-wrap: break-word;
  font-family: 'Anime Ace', cursive, sans-serif;
  
  &::before{
    box-sizing: border-box;
    content: '';
    position: absolute; 
    z-index: -1;
    width: var(--arrow);
    height: var(--arrow);
    --arrow: 30px;    
    border-radius: 0 0 0 10em;        
  }

  ${(props) => arrowPositionStyles[props.arrowPosition]};
`;

const ComicPanel = ({ bubble, svgRect }) => {
  const translatedText = useTranslatedText(bubble.text);
  let style = {};
  // Usar el rectángulo del contenedor para escalar y posicionar
  if (svgRect && typeof bubble.x === 'number' && typeof bubble.y === 'number') {
    // El SVG base es 2048x2048
    const svgW = 2048, svgH = 2048;
    // Usar el ancho del contenedor (no el SVG interno)
    const containerWidth = svgRect.width;
    const px = svgRect.left + (bubble.x / svgW) * containerWidth;
    const py = svgRect.top + (bubble.y / svgH) * svgRect.height;
    // Escala BASE_FONT_SIZE según el ancho del contenedor
    const scale = containerWidth / svgW;
    let scaledFontSize = BASE_FONT_SIZE * scale;
    // Luego aplicar el porcentaje del YAML
    let percent = 100;
    if (bubble.fontSize) {
      if (typeof bubble.fontSize === 'string' && bubble.fontSize.endsWith('%')) {
        percent = parseFloat(bubble.fontSize);
      } else if (typeof bubble.fontSize === 'string') {
        const asNum = parseFloat(bubble.fontSize);
        if (!isNaN(asNum)) {
          percent = asNum;
        }
      } else if (typeof bubble.fontSize === 'number') {
        percent = bubble.fontSize;
      }
    }
    scaledFontSize = scaledFontSize * (percent / 100);
    style = {
      position: 'fixed',
      top: py,
      left: px,
      fontSize: `${scaledFontSize}px`,
      pointerEvents: 'auto',
      transform: 'translate(-50%, -50%)',
      zIndex: 10
    };
  } else {
    // Fallback: usa la posición original (absoluta sobre el contenedor)
    let percent = 100;
    if (bubble.fontSize) {
      if (typeof bubble.fontSize === 'string' && bubble.fontSize.endsWith('%')) {
        percent = parseFloat(bubble.fontSize);
      } else if (typeof bubble.fontSize === 'string') {
        const asNum = parseFloat(bubble.fontSize);
        if (!isNaN(asNum)) {
          percent = asNum;
        }
      } else if (typeof bubble.fontSize === 'number') {
        percent = bubble.fontSize;
      }
    }
    style = {
      top: bubble.y,
      left: bubble.x,
      fontSize: `${BASE_FONT_SIZE * (percent / 100)}px`,
      pointerEvents: 'auto',
      position: 'absolute',
      zIndex: 10
    };
  }
  return (
    <Bubble style={style} arrowPosition={bubble.arrowPosition}>
      {translatedText}
    </Bubble>
  );
};

export default ComicPanel;