import { api } from "@/api/axios";

export enum BetOption {
	HOME =  'HOME',
	DRAW = 'DRAW',
	AWAY = "AWAY"
};

export interface BetPick {
	id:number,
	matchId: number,
	homeTeam: string,
	awayTeam: string,
	matchDate: string,
	option: BetOption,
	selectedOdds: number,
	result: "PENDING" | "WIN" | "LOSE";
}

export interface BetSlip {
	id:number,
	status: "DRAFT" | "PLACED" | "LOST" | "WON",
	stake: number,
	totalOdds: number,
	totalWin: number,
	potentialWin: number,
	placedAt?: string,
	settledAt: string,
	betPicks: BetPick[];
}

export const betSlipService = {
	getDraft: async () => {
		try{
			const response = await api.get<BetSlip>('/api/betslip/draft');
			return response.status === 204 ? null : response.data;
		} catch (error){
			return null;
		}
	},

	addPick: async(matchId:number, betOption: BetOption) => {
		try{
			const response = await api.post<BetSlip>('api/betslip/picks', { matchId, betOption});
			return response.data;
		}catch (error){
			return null;
		}
	},

	removePick: async(pickId:number) => {
		try{
			const response = await api.delete<BetSlip>(`/api/betslip/picks/${pickId}`);
			return response.data;
		}catch (error){
			return null;
		}
	},

	placeBet: async(stake:number) => {
		try{
			const response = await api.post<BetSlip>('/api/betslip/place', {stake});
			return response.data;
		}catch (error){
			return null;
		}
	},

	getHistory: async () => {
		try{
			const response = await api.get<BetSlip[]>('/api/betslip/history');
			return response.data;
		} catch(error){
			return null;
		}
	},

	getFriendHistory: async(friendId: number) => {
		try{
			const response = await api.get<BetSlip[]>(`/api/betslip/history/${friendId}`);
			return response.data;
		} catch(error){
			return null;
		}
	}
};