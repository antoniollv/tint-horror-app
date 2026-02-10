const getEnv = () => (typeof import.meta !== 'undefined' && import.meta.env) ? import.meta.env : {};

export const getEnvValue = (keys, fallback = '') => {
  const env = getEnv();
  for (const key of keys) {
    const value = env[key];
    if (typeof value === 'string' && value.trim() !== '') {
      return value;
    }
  }
  return fallback;
};
