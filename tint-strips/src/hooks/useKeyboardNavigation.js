import { useEffect } from 'react';

export const useKeyboardNavigation = ({ onNext, onPrevious, onToggleTimer, onAny }) => {
  useEffect(() => {
    const handleKeyDown = (event) => {
      if (event.key === 'ArrowRight' || event.key === ' ') {
        onNext();
      } else if (event.key === 'ArrowLeft') {
        onPrevious();
      } else if (event.key === 't') {
        onToggleTimer();
      }
      if (onAny) {
        onAny();
      }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [onNext, onPrevious, onToggleTimer, onAny]);
};
