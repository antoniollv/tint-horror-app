import React, { memo } from 'react';
import ComicPanel from './ComicPanel.jsx';


import styled from 'styled-components';

const BubbleOverlay = styled.div``;

const BubbleLayer = ({ bubbles, svgRect }) => {
  if (!Array.isArray(bubbles) || bubbles.length === 0 || !svgRect) {
    return null;
  }
  const style = {
    position: 'fixed',
    top: svgRect.top,
    left: svgRect.left,
    width: svgRect.width,
    height: svgRect.height,
    pointerEvents: 'none',
    border: '2px solid red',    
    zIndex: 1002
  };
  return (
    <BubbleOverlay style={style}>
      {bubbles.map((bubble, index) => (
        <ComicPanel key={index} bubble={bubble} svgRect={svgRect} />
      ))}
    </BubbleOverlay>
  );
};

export default memo(BubbleLayer);
