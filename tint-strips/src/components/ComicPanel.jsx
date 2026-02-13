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
  max-width: clamp(100px, 12vw, 300px);
  word-wrap: break-word;
  font-family: 'Anime Ace', cursive, sans-serif;
  
  &::before{
    box-sizing: border-box;
    content: '';
    position: absolute; 
    z-index: -1;
    width: var(--arrow);
    height: var(--arrow);
    --arrow: clamp(10px, 2vw, 32px);
    border-radius: 0 0 0 10em;        
  }

  ${(props) => arrowPositionStyles[props.arrowPosition]};
`;

const ComicPanel = ({ bubble, svgRect }) => {
  const translatedText = useTranslatedText(bubble.text);  
  let fontSize;
  if (bubble.fontSize) {
    if (typeof bubble.fontSize === 'number') {
      fontSize = `${bubble.fontSize}px`;
    } else if (typeof bubble.fontSize === 'string') {
      fontSize = bubble.fontSize;
    }
  }
  const style = {
    top: bubble.y,
    left: bubble.x,
    fontSize,
    pointerEvents: 'auto',
    position: 'absolute',
    zIndex: 10
  };
  return (
    <Bubble style={style} arrowPosition={bubble.arrowPosition}>{translatedText}</Bubble>
  );
};

export default ComicPanel;