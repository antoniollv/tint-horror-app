export const isValidStripsData = (data) => Array.isArray(data?.strips);

export const getValidationError = (data) => {
  if (!data) {
    return 'La configuración está vacía.';
  }
  if (!Array.isArray(data?.strips)) {
    return 'Formato inválido en comics.yml: se esperaba un array en "strips".';
  }
  return '';
};
