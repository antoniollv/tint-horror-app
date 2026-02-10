import React, { memo } from 'react';
import ComicPanel from './ComicPanel.jsx';

const BubbleLayer = ({ bubbles }) => {
  if (!Array.isArray(bubbles) || bubbles.length === 0) {
    return null;
  }

  return (
    <>
      {bubbles.map((bubble, index) => (
        <ComicPanel key={index} bubble={bubble} />
      ))}
    </>
  );
};

export default memo(BubbleLayer);
