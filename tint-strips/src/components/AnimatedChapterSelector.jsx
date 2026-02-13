import React, { useState } from 'react';

// Animated selector for chapters
const AnimatedChapterSelector = ({ chapters, currentChapter, onSelect }) => {
  const [open, setOpen] = useState(false);

  // Close selector after choosing chapter
  const handleSelect = (index) => {
    onSelect(index);
    setOpen(false);
  };

  // Show a single box labeled "Chapters" when closed
  return (
    <div className={`chapter-selector${open ? ' open' : ''}`} style={{ position: 'fixed', top: 0, left: 0 }}>
      {!open ? (
        <div className="chapter-toggle" onClick={() => setOpen(true)}>
          Chapters
        </div>
      ) : (
        <div className="chapter-list">
          {chapters.map((_, index) => (
            <div
              key={index}
              className={`chapter ${index === currentChapter ? 'active' : ''}`}
              onClick={() => handleSelect(index)}
            >
              Chapter {index + 1}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default AnimatedChapterSelector;
