import { useEffect, useState } from 'react';
import { loadConfigStrips } from '../components/loadConfigStrips.js';
import { getChapterVignettes, getChapters } from '../utils/strips.js';
import { logError } from '../utils/logger.js';

export const useStripsData = (chapter) => {
  const [strips, setStrips] = useState([]);
  const [chapters, setChapters] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchStrips = async () => {
      setIsLoading(true);
      setError('');
      try {
        const stripsData = await loadConfigStrips();
        setChapters(getChapters(stripsData));
        setStrips(getChapterVignettes(stripsData, chapter));
      } catch (err) {
        const formatted = logError('Error loading strips', err);
        setError(formatted.message || 'Error loading strips');
        setChapters([]);
        setStrips([]);
      } finally {
        setIsLoading(false);
      }
    };

    fetchStrips();
  }, [chapter]);

  return { strips, chapters, isLoading, error };
};
