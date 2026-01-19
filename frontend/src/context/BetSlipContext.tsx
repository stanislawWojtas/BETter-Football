import { toaster } from "@/components/ui/toaster";
import { AuthService } from "@/services/authService";
import { betSlipService, type BetOption, type BetSlip } from "@/services/betSlipService";
import { createContext, useContext, useEffect, useState } from "react";

interface BetSlipContextType {
	betSlip: BetSlip | null;
	isLoading: boolean;
	refreshSlip: () => Promise<void>;
	addPick: (matchId: number, option:BetOption) => Promise<void>;
	removePick: (pickId: number) => Promise<void>;
	placeBet: (stake:number) => Promise<void>;
	isOpen: boolean;
	setIsOpen: (open:boolean) => void;
}

const BetSlipContext = createContext<BetSlipContextType | undefined>(undefined);

export const BetSlipProvider = ({ children }: {children: React.ReactNode}) => {
	const [betSlip, setBetSlip] = useState<BetSlip | null>(null);
	const [isLoading, setIsLoading] = useState<boolean>(false);
	const [isOpen, setIsOpen] = useState<boolean>(false);

	const isLoggedIn = AuthService.isAuthenticated();

	const refreshSlip = async () => {
		if(!isLoggedIn) return;
		try{
			const slip = await betSlipService.getDraft();
			setBetSlip(slip);
		}catch (error) {
			console.error("Failed to fetch bet slip", error);
		}
	}

	// when log in the slip is refreshed
	useEffect( () => {
		if(isLoggedIn) refreshSlip();
		else setBetSlip(null);
	}, [isLoggedIn]);

	const addPick = async (matchId: number, option:BetOption) => {
		if(!isLoggedIn){
			toaster.create({description: "Zaloguj się aby obstawić mecz", type: "error"});
			return;
		}
		setIsLoading(true);
		try{
			const updatedSlip = await betSlipService.addPick(matchId, option);
			toaster.create({description:"DODANO ZAKŁAD", type:"success"});
			setBetSlip(updatedSlip);
			setIsOpen(true);
			toaster.create({description: "Dodano zakład do kuponu", type:"success"});
		} catch (error){
			console.error(error);
		}finally{
			setIsLoading(false);
		}
	};

	const removePick = async (pickId:number) => {
		if(!isLoggedIn){
			toaster.create({description: "Zaloguj się aby obstawić mecz", type: "error"});
			return;
		}
		setIsLoading(true);
		try{
			const updatedSlip = await betSlipService.removePick(pickId);
			setBetSlip(updatedSlip);
		}catch (error){
			console.error(error);
		}finally{
			setIsLoading(false);
		}
	} 

	const placeBet = async (stake: number) => {
		if(!isLoggedIn){
			toaster.create({description: "Zaloguj się aby obstawić mecz", type: "error"});
			return;
		}
		setIsLoading(true);
		try{
			await betSlipService.placeBet(stake);
			setBetSlip(null); // reset the actual betSlip
			setIsOpen(false);
		}catch (error:any ) {
			toaster.create({description: "Błąd podczas stawiania kuponu", type:'error'});
		}finally{
			setIsLoading(false);
		}
	};

	return (
		<BetSlipContext.Provider value={{betSlip, isLoading, refreshSlip, addPick, removePick, placeBet, isOpen, setIsOpen}}>
			{children}
		</BetSlipContext.Provider>
	);
}

export const useBetSlip = () => {
	const context = useContext(BetSlipContext);
	if(!context) throw new Error("useBetSlip must be used within a BetSlipProvider");
	return context;
}