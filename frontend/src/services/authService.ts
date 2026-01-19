import { api } from "@/api/axios";

export interface UserLoginDto{
	username: string;
	password: string;
}

export interface UserRegistrationDto {
	username: string;
	password: string;
	email: string;
	firstName: string;
	lastName: string;
}

export interface TokenResponse{
	token: string;
	userId: number;
	username: string;
}

export interface BalanceResponse {
	balance: number;
}

export const AuthService = {
	login: async (data: UserLoginDto) => {
		const response = await api.post<TokenResponse>('/api/auth/login', data);
		if(response.data.token){
			localStorage.setItem('token', response.data.token);
			localStorage.setItem('userId', response.data.userId.toString());
			localStorage.setItem('username', response.data.username);
		}
		return response.data;
	},

	register: async (data: UserRegistrationDto) => {
		return api.post('/api/auth/register', data);
	},

	logout: () => {
		localStorage.removeItem('token');
	},

	getToken: () => {
		return localStorage.getItem('token');
	},

	isAuthenticated: () => {
		return !!localStorage.getItem('token');
	},

	getBalance: async () => {
		return await api.get<BalanceResponse>('/api/users/balance');
	}
}