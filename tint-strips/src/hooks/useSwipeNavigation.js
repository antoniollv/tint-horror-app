import { useEffect, useRef } from 'react';

/**
 * Detects horizontal swipe gestures on a target element (or the window)
 * and calls onNext / onPrevious accordingly.
 *
 * @param {object} params
 * @param {React.RefObject} params.ref      - ref to the element to listen on (optional, defaults to window)
 * @param {function} params.onNext          - called when user swipes LEFT  (next slide)
 * @param {function} params.onPrevious      - called when user swipes RIGHT (previous slide)
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
  const startX = useRef(null);

  useEffect(() => {
    const target = ref?.current ?? window;

    const handleTouchStart = (e) => {
      if (e.touches.length === 1) {
        startX.current = e.touches?.[0]?.clientX ?? null;
      }
    };

    const handleTouchEnd = (e) => {
      if (startX.current === null) return;
      const touch = e.changedTouches?.[0];
      if (!touch) {
        startX.current = null;
        return;
      }
      const deltaX = startX.current - touch.clientX;
      startX.current = null;

      if (Math.abs(deltaX) < threshold) return;

      if (onSwipe) onSwipe();

      if (deltaX > 0) {
        if (onNext) onNext();           // swipe left → next slide
      } else {
        if (onPrevious) onPrevious();   // swipe right → previous slide
      }
    };

    const handleTouchCancel = () => {
      startX.current = null;
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
