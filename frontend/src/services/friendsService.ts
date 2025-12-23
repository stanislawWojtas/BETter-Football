import { api } from "@/api/axios";



export interface Friendship {
	id: number;
	requester: {id: number; username: string, balance: number};
	addressee: {id: number; username: string, balance: number};
	status: string;
	createdAt: string;
}

export const friendsService = {
	sendFriendRequest: async (username: string) => {
		try{
			const response = await api.post<Friendship>("/api/friends/send/"+username);
			return response.data;
		} catch(e:any){
			console.log(e);
				const status = e.response?.status;
				if(status === 401){
				throw new Error("Unauthorized");
			}
				if(status === 404){
				alert("Użytkownik o podanej nazwie nie istnieje");
				return;
			}
				if(status === 409){
				alert("Zaproszenie zostało odrzucone lub użytkownik jest już znajomym")
				return;
			}

				throw new Error(e?.message ?? "Nie udało się wysłać zaproszenia");
		}
	},

	acceptFriendRequest: async (requesterId: number) => {
		try{
			const response = await api.post<Friendship>(`/api/friends/accept/${requesterId}`);
			return response.data;
		}catch(e:any){
			throw new Error(e?.message ?? "Cound not accept friend request");
		}
	},

	declineFriendRequest: async (requesterId: number) => {
		try{
			const response = await api.post<Friendship>(`/api/friends/decline/${requesterId}`);
			return response.data;
		}catch(e:any){
			throw new Error(e?.message ?? "Cound not accept friend request");
		}
	},

	getAllRequests: async () => {
		try{
			const response = await api.get<Friendship[]>("/api/friends/requests");
			return response.data;
		}catch(e:any){
			throw new Error(e?.message ?? "Cound not fetch friend requests");
		}
	},

	getAllFriends: async () => {
		try{
			const response = await api.get<Friendship[]>("/api/friends/friends");
			console.log("Fetched friends:", response.data);
			return response.data;
		}catch(e:any){
			throw new Error(e?.message ?? "Cound not fetch friends");
		}
	}
}