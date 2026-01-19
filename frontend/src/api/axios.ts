import axios from "axios";

// url od którego się rozpoczyna każde zapytanie do api
const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export const api = axios.create({
	baseURL: BASE_URL,
	headers: {
		'Content-Type': 'application/json',
	},
});

// it adds bearer token if exist
api.interceptors.request.use(
	(config) => {
		const token = localStorage.getItem('token');
		if(token){
			config.headers.Authorization = `Bearer ${token}`;
		}
		return config;
	},
	(error) => {
		return Promise.reject(error)
	}
)

api.interceptors.response.use(
	(response) => response,
	(error) => {
		if(error.response?.status === 403){
			localStorage.removeItem('token');
			// TODO: dodać redirect do /login page
		}
		return Promise.reject(error);
	}
);