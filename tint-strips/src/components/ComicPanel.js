// src/components/ComicPanel.js
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { arrowPositionStyles } from '../styles/arrowStyles';
import { translateText } from '../utils/translate';

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

const ComicPanel = ({ bubble }) => {
  const [translatedText, setTranslatedText] = useState(bubble.text);
  
  useEffect(() => {
    const browserLanguage = navigator.language.split('-')[0] || navigator.languages[0].split('-')[0];
    //const browserLanguage = 'en';
    if (browserLanguage !== 'en') {
      translateText(bubble.text, browserLanguage)
        .then((translated) => setTranslatedText(translated))
        .catch((err) => console.error('Translation error:', err));
    }
  }, [bubble.text]);

  return (
    <Bubble style={{
      top: bubble.y,
      left: bubble.x,
      fontSize: bubble.fontSize
    }}
      arrowPosition={bubble.arrowPosition}>
      {translatedText}
    </Bubble>      
  );
};

export default ComicPanel;
