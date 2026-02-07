import { useEffect, useState, useCallback } from 'react';

export const useImagePreload = (src) => {
  const [isLoaded, setIsLoaded] = useState(false);

  useEffect(() => {
    setIsLoaded(false);
  }, [src]);

  const handleLoad = useCallback(() => {
    setIsLoaded(true);
  }, []);

  return { isLoaded, handleLoad };
};
