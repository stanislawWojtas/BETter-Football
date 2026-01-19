import { useBetSlip } from "@/context/BetSlipContext";
import { BetOption } from "@/services/betSlipService";
import type { MatchOdds } from "@/services/matchService";
import { Badge, Box, Button, Flex, Grid, Heading, Separator, Text } from "@chakra-ui/react";
import React from "react";

interface MatchCardProps{
	match: MatchOdds;
}


export const MatchCard = React.memo(({match}: MatchCardProps) => {
	// use the context
	const {addPick, isLoading} = useBetSlip();

	//date formatting
	const { dateStr, timeStr, weekdayStr } = React.useMemo(() => {
		const dateObj = new Date(match.date);
		return {
			dateStr: dateObj.toLocaleDateString('pl-PL', {day: '2-digit', month: '2-digit'}),
			timeStr: dateObj.toLocaleTimeString('pl-PL', {hour: '2-digit', minute: '2-digit'}),
			weekdayStr: dateObj.toLocaleDateString('pl-PL', { weekday: 'long' }),
		};
	}, [match.date]);

	return (
		<Box 
			bg="white"
			borderRadius="lg"
			boxShadow="sm"
			p={4}
			border="1px solid"
			borderColor="gray.200"
			_hover={{boxShadow: "md", borderColor: "cyan.400"}}
			transition="all 0.2s">
			{/* Nagłówek - Liga i daty */}
			<Flex justifyContent={"space-between"} mb={3} align="center">
				<Badge colorPalette="cyan" variant='solid'>
					{match.league}
				</Badge>
				<Text fontSize='xs' color="gray.500" fontWeight="bold">
					{dateStr}, {weekdayStr} <Text as='span' color='cyan.600'>{timeStr}</Text>
				</Text>
			</Flex>
			{/* Drużyny */}
			<Flex justifyContent={'space-between'} align='center' mb={4}>
				<Heading size='md' color='brand.800' flex={1} textAlign='left'>{match.homeTeam}</Heading>
				<Text px={2} color='gray.400' fontWeight='bold'>VS</Text>
				<Heading size='md' color='brand.800' flex={1} textAlign='right'>{match.awayTeam}</Heading>
			</Flex>

			<Separator mb={4}/>

			{/* Kursy (jako przyciski) */}
			<Grid templateColumns="repeat(3, 1fr)" gap={2}>
				<OddsButton label="1" value={match.homeWin} onClick={() => addPick(match.id, BetOption.HOME)} disabled={isLoading}/>
				<OddsButton label="X" value={match.draw} onClick={() => addPick(match.id, BetOption.DRAW)} disabled={isLoading}/>
				<OddsButton label="2" value={match.awayWin} onClick={() => addPick(match.id, BetOption.AWAY)} disabled={isLoading}/>
			</Grid>
		</Box>
	)
});

const OddsButton = ({label, value, onClick, disabled}: {label:string, value:number, onClick: () => void, disabled:boolean}) => {
	return(
		<>
			<Button
				variant='outline'
				colorPalette='gray'
				height='auto'
				py={2}
				flexDirection='column'
				gap={0}
				_hover={{bg: "cyan.50", borderColor: "cyan.500", color:'cyan.700'}}
				onClick={(e) => {
					e.stopPropagation();
					onClick();
				}}
				disabled={disabled}>
					<Text fontSize='xs' color='gray.500'>{label}</Text>
					<Text fontWeight='bold' fontSize='md'>{value.toFixed(2)}</Text>
			</Button>
		</>
	)
}
