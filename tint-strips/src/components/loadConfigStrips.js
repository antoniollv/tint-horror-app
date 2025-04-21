import yaml from 'js-yaml';

let comicsConfigPath = process.env.REACT_APP_YAML_CONFIG_PATH;
if (!comicsConfigPath || comicsConfigPath.trim() === "") {
  comicsConfigPath = '/comics.yml';
}

export const loadConfigStrips = async () => {
  try {
    const response = await fetch(comicsConfigPath);
    const fileContents = await response.text();
    const data = yaml.load(fileContents);
    return data;
  } catch (e) {
    console.log(e);
  }
};

