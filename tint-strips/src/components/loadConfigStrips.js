import yaml from 'js-yaml';
import fs from 'fs';

export const loadConfigStrips = () => {
  try {
    const fileContents = fs.readFileSync('src/comic.yaml', 'utf8');
    const data = yaml.load(fileContents);
    return data;
  } catch (e) {
    console.log(e);
  }
};
