import { useEffect, useState } from 'react';
import { translateText } from '../utils/translate.js';
import { getCached, setCached } from '../utils/cache.js';

export const useTranslatedText = (text) => {
  const [translatedText, setTranslatedText] = useState(text);

  useEffect(() => {
    const browserLanguage = navigator.language.split('-')[0] || navigator.languages[0].split('-')[0];
    const cacheKey = `${browserLanguage}:${text}`;
    const cached = getCached(cacheKey);
    if (cached) {
      setTranslatedText(cached);
      return;
    }

    if (browserLanguage !== 'en') {
      translateText(text, browserLanguage)
        .then((translated) => setTranslatedText(setCached(cacheKey, translated)))
        .catch((err) => console.error('Translation error:', err));
    } else {
      setTranslatedText(setCached(cacheKey, text));
    }
  }, [text]);

  return translatedText;
};
