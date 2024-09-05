import yaml from 'js-yaml';

export const loadConfigStrips = async ( i=0) => {
  try {
    const response = await fetch('/comics.yml');
    const fileContents = await response.text();
    const data = yaml.load(fileContents);
    return data.strips[i].vignettes;
  } catch (e) {
    console.log(e);
  }
};

