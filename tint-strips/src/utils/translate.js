// src/utils/translate.js
import { getEnvValue } from './env.js';
import { fetchJson } from './http.js';
import { logError, logInfo } from './logger.js';

const endpoint = getEnvValue(
  ['VITE_TRANSLATION_API_URL'],
  'http://localhost:5000/translate'
);
const traslation_api_key = getEnvValue(
  ['VITE_TRANSLATION_API_KEY'],
  ''
);

export const translateText = async (text, targetLanguage = 'es') => {
  try {
    const json = await fetchJson(endpoint, {
      method: "POST",
      body: JSON.stringify({
        q: text,
        source: "en",
        target: targetLanguage,
        //target: "it",
        format: "text",
        api_key: traslation_api_key
      }),
      headers: { "Content-Type": "application/json" }
    });

    logInfo("API Response:", json); // Para depurar la respuesta

    return json.translatedText || 'Translation unavailable';
  } catch (error) {
    logError('Translation error', error);
    //return 'Translation error'; // Mensaje de error más general
    return text;
  }
};
