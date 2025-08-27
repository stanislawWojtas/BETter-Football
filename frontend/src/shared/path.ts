const apiBaseUrl = import.meta.env.VITE_API_BASE_URL;

export const getUrlFromPath = (path: string) => {
    return `${apiBaseUrl}/${path}`;
}