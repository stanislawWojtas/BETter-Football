import { api } from "@/api/axios";

export interface MatchOdds {
	id: number;
	homeTeam: string;
	awayTeam: string;
	date: string; // LocalDateTime in java
	league: string;
	homeWin: number;
	awayWin: number;
	draw: number;
}

export const matchService = {
	getUpcoming: async (country?: string) => {
		const params = country ? {country} : {};
		const response = await api.get<MatchOdds[]>('/api/matches/upcoming', {params});
		return response.data;
	},

	search: async(teamName: string) => {
		const response = await api.get<MatchOdds[]>('/api/matches/search', {params: {teamName}});
		return response.data;
	}
}