import yaml from 'js-yaml';
import { getEnvValue } from '../utils/env.js';
import { fetchText } from '../utils/http.js';
import { getValidationError, isValidStripsData } from '../utils/validation.js';
import { logError } from '../utils/logger.js';

const comicsConfigPath = getEnvValue(
  ['VITE_YAML_CONFIG_PATH', 'REACT_APP_YAML_CONFIG_PATH'],
  '/comics.yml'
);

export const loadConfigStrips = async () => {
  try {
    const fileContents = await fetchText(comicsConfigPath);
    const data = yaml.load(fileContents);
    if (!isValidStripsData(data)) {
      throw new Error(getValidationError(data));
    }
    return data;
  } catch (e) {
    throw logError('Error loading strips config', e);
  }
};

