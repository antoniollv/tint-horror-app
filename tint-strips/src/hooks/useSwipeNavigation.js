import { useEffect, useRef } from 'react';

/**
 * Detects vertical swipe gestures on a target element (or the window)
 * and calls onNext / onPrevious accordingly.
 *
 * @param {object} params
 * @param {React.RefObject} params.ref      - ref to the element to listen on (optional, defaults to window)
 * @param {function} params.onNext          - called when user swipes UP   (next slide)
 * @param {function} params.onPrevious      - called when user swipes DOWN (previous slide)
 * @param {function} [params.onSwipe]       - called on any detected swipe (e.g. to stop the timer)
 * @param {number}   [params.threshold=50]  - minimum pixel distance to count as a swipe
 */
export const useSwipeNavigation = ({
  ref,
  onNext,
  onPrevious,
  onSwipe,
  threshold = 50
}) => {
  const startY = useRef(null);

  useEffect(() => {
    const target = ref?.current ?? window;

    const handleTouchStart = (e) => {
      if (e.touches.length === 1) {
        startY.current = e.touches[0].clientY;
      }
    };

    const handleTouchEnd = (e) => {
      if (startY.current === null) return;
      const endY = e.changedTouches[0].clientY;
      const deltaY = startY.current - endY;
      startY.current = null;

      if (Math.abs(deltaY) < threshold) return;

      if (onSwipe) onSwipe();

      if (deltaY > 0) {
        if (onNext) onNext();           // swipe up → next slide
      } else {
        if (onPrevious) onPrevious();   // swipe down → previous slide
      }
    };

    const handleTouchCancel = () => {
      startY.current = null;
    };

    target.addEventListener('touchstart', handleTouchStart, { passive: true });
    target.addEventListener('touchend', handleTouchEnd, { passive: true });
    target.addEventListener('touchcancel', handleTouchCancel, { passive: true });

    return () => {
      target.removeEventListener('touchstart', handleTouchStart);
      target.removeEventListener('touchend', handleTouchEnd);
      target.removeEventListener('touchcancel', handleTouchCancel);
    };
  }, [ref, onNext, onPrevious, onSwipe, threshold]);
};
