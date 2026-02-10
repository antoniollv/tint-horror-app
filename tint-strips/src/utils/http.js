const ensureOk = async (response) => {
  if (response.ok) {
    return response;
  }
  const body = await response.text().catch(() => '');
  const message = body ? `${response.status} ${response.statusText}: ${body}` : `${response.status} ${response.statusText}`;
  throw new Error(message);
};

export const fetchText = async (url, options) => {
  const response = await ensureOk(await fetch(url, options));
  return response.text();
};

export const fetchJson = async (url, options) => {
  const response = await ensureOk(await fetch(url, options));
  return response.json();
};
