export const getWrappedIndex = (index, delta, length) => {
  if (!Number.isFinite(length) || length <= 0) {
    return 0;
  }
  return (index + delta + length) % length;
};
