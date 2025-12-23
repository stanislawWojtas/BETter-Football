import { api } from "@/api/axios";
import { betSlipService, type BetSlip } from "@/services/betSlipService"
import { Badge, Box, Card, Center, Container, Flex, Grid, Heading, Separator, Spinner, Text, VStack } from "@chakra-ui/react";
import { useEffect, useState } from "react"
import { Navbar } from "./MainPage";

const MyBetsPage = () => {
	const [history, setHistory] = useState<BetSlip[]>([]);
	const [isLoading, setIsLoading] = useState<boolean>(true);

	useEffect( () => {
		const fetchHistory = async () => {
			try{
				const data = await betSlipService.getHistory();
				setHistory(data ?? []);
			} catch (err) {
				console.error("Failed to fetch history", err);
			} finally {
				setIsLoading(false);
			}
		}
		fetchHistory();
	}, [])

	const getStatusColor = (status: string) => {
		switch(status) {
			case "WON": return "green";
			case "LOST": return "red";
			case "PLACED": return "blue";
			default: return "gray";
		}
	};

	return(
		<Box minH={'100vh'} bg={'gray.50'}>
			<Navbar />
			<Container maxW={'800px'} py={8}>
				<Heading mb={6} size={'xl'} color={'blue.500'}>Twoje Zakłady</Heading>

				{isLoading ? (
					<Center h={'200px'}><Spinner size={'xl'} color={'blue.500'}/></Center>
				) : history.length === 0 ? (
					<Center p={10} bg={'white'} rounded={'lg'} shadow={'sm'}>
						<Text color={'gray.500'}>Nie masz jeszcze żadnych postawionych zakładów. </Text>
					</Center>
				) : (
					<VStack gap={6} align="stretch">
						{history.map((slip) => (
						<Card.Root key={slip.id} overflow="hidden" variant="elevated" borderColor={getStatusColor(slip.status)} borderLeftWidth="4px">
							<Card.Header bg="gray.50" py={3}>
							<Flex justify="space-between" align="center">
								<Flex gap={3} align="center">
								<Badge colorPalette={getStatusColor(slip.status)} size="lg" variant="solid">
									{slip.status}
								</Badge>
								<Text fontSize="sm" color="gray.500">
									{new Date(slip.placedAt!).toLocaleString('pl-PL')}
								</Text>
								</Flex>
								<Text fontWeight="bold" color={'black'}>
								Stawka: {slip.stake} PLN
								</Text>
							</Flex>
							</Card.Header>
							<Card.Body>
							<VStack align="stretch" gap={3}>
								{slip.betPicks.map((pick) => (
								<Grid key={pick.id} templateColumns="3fr 1fr 1fr 1fr" gap={4} alignItems="center" fontSize="sm">
									<Box>
									<Text fontWeight="bold">{pick.homeTeam} vs {pick.awayTeam}</Text>
									<Text fontSize="xs" color="gray.400">{new Date(pick.matchDate).toLocaleString('pl-PL')}</Text>
									</Box>
									<Box textAlign="center">
									<Text color="gray.500" fontSize="xs">Twój typ</Text>
									<Badge variant="subtle" colorPalette="cyan">{pick.option}</Badge>
									</Box>
									<Box textAlign="center">
									<Text color="gray.500" fontSize="xs">Kurs</Text>
									<Text fontWeight="bold">{pick.selectedOdds}</Text>
									</Box>
									<Box textAlign="right">
									{pick.result === 'WIN' && <Badge colorPalette="green">Trafiony</Badge>}
									{pick.result === 'LOSE' && <Badge colorPalette="red">Pudło</Badge>}
									{pick.result === 'PENDING' && <Badge colorPalette="gray" variant="outline">W grze</Badge>}
									</Box>
								</Grid>
								))}
						</VStack>
						</Card.Body>
						<Separator />
						<Card.Footer py={3}>
						<Flex justify="space-between" w="full" align="center">
							<Text fontSize="sm" color="gray.500">Kurs całkowity: <span style={{ fontWeight: 'bold', color: 'cyan' }}>{slip.totalOdds}</span></Text>
							<Box textAlign="right">
							<Text fontSize="xs" color="gray.500">Ewentualna wygrana</Text>
							<Text fontSize="lg" fontWeight="bold" color={(slip.status as string) === "WON" ? 'green.600' : 'brand.800'}>
								{slip.potentialWin} PLN
							</Text>
							</Box>
						</Flex>
						</Card.Footer>
					</Card.Root>
					))}
				</VStack>
				)}
			</Container>
		</Box>
	)

}

export default MyBetsPage