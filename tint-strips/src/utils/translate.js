// src/utils/translate.js
import axios from 'axios';

const endpoint = 'http://localhost:5000/translate';

export const translateText = async (text, targetLanguage) => {
  try {
    const response = await axios.post(endpoint, {
      q: text,
      source: 'es',
      target: targetLanguage,
      format: 'text'
    });
    return response.data.translatedText;
  } catch (error) {
    console.error('Translation error:', error);
    return text; // En caso de error, devolver el texto original
  }
};
