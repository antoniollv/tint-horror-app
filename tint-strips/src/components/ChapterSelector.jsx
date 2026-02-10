import React from 'react';

const ChapterSelector = ({ chapters, currentChapter, onSelect }) => (
  <div className="chapter-selector">
    {chapters.map((_, index) => (
      <div
        key={index}
        className={`chapter ${index === currentChapter ? 'active' : ''}`}
        onClick={() => onSelect(index)}
      >
        Chapter {index + 1}
      </div>
    ))}
  </div>
);

export default ChapterSelector;
