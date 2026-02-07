import { useMemo } from 'react';

export const useStripViewData = ({ strips, currentIndex, imgsPath }) => {
  const currentStrip = useMemo(
    () => strips[currentIndex],
    [strips, currentIndex]
  );

  const currentVignetteSrc = useMemo(
    () => (currentStrip ? imgsPath + currentStrip.vignette : ''),
    [currentStrip, imgsPath]
  );

  const thumbnailStrips = useMemo(
    () => strips.map((strip) => ({
      ...strip,
      vignette: imgsPath + strip.vignette
    })),
    [strips, imgsPath]
  );

  return { currentStrip, currentVignetteSrc, thumbnailStrips };
};
