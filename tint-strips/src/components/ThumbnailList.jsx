import React from 'react';
import Thumbnail from './Thumbnail.jsx';

const ThumbnailList = ({ strips, currentIndex, onSelect }) => (
  <div className="thumbnails-container">
    {strips.map((strip, index) => (
      <Thumbnail
        key={index}
        src={strip.vignette}
        onClick={() => onSelect(index)}
        isActive={index === currentIndex}
      />
    ))}
  </div>
);

export default ThumbnailList;
