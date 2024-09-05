// src/utils/translate.js

const endpoint = "http://localhost:5000/translate";

export const translateText = async (text, targetLanguage = 'es') => {
  try {
    const res = await fetch(endpoint, {
      method: "POST",
      body: JSON.stringify({
        q: text,
        source: "en",
        target: targetLanguage,
        //target: "it",
        format: "text",
        api_key: ""  // Si es necesario, se puede agregar un API key
      }),
      headers: { "Content-Type": "application/json" }
    });

    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }

    const json = await res.json();

    console.log("API Response:", json); // Para depurar la respuesta

    return json.translatedText || 'Translation unavailable';
  } catch (error) {
    console.error('Translation error:', error);
    //return 'Translation error'; // Mensaje de error más general
    return text;
  }
};
