// src/utils/translate.js

let endpoint = process.env.REACT_APP_TRANSLATION_API_URL;
if (!endpoint || endpoint.trim() === "") {
  endpoint = "http://localhost:5000/translate";
}
let traslation_api_key = process.env.REACT_APP_TRANSLATION_API_KEY;
if (!traslation_api_key || traslation_api_key.trim() === "") {
  traslation_api_key = "";
}

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
        api_key: traslation_api_key
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
