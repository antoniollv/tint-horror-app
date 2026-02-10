import { useEffect } from 'react';

export const useAutoAdvance = ({ isActive, onTick, intervalMs }) => {
  useEffect(() => {
    let timer;
    if (isActive) {
      timer = setInterval(onTick, intervalMs);
    }
    return () => clearInterval(timer);
  }, [isActive, onTick, intervalMs]);
};
