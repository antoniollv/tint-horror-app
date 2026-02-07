const formatError = (error) => {
  if (error instanceof Error) {
    return error;
  }
  return new Error(typeof error === 'string' ? error : 'Unknown error');
};

export const logInfo = (...args) => {
  console.log(...args);
};

export const logError = (message, error) => {
  const formatted = formatError(error);
  console.error(message, formatted);
  return formatted;
};
