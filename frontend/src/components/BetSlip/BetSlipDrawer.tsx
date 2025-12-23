import { useBetSlip } from "@/context/BetSlipContext"
import { useState } from "react";
import { toaster } from "../ui/toaster";
import { Box, Button, Card, Drawer, Flex, HStack, IconButton, Input, Separator, Text, VStack } from "@chakra-ui/react";
import { LuTrash2, LuX } from "react-icons/lu";


export const BetSlipDrawer = () => {
	const {betSlip, removePick, placeBet, isOpen, setIsOpen, isLoading} = useBetSlip();
	const [stake, setStake] = useState<string>("10")

	const handlePlaceBet = () => {
		const stakeValue = parseFloat(stake);
		if(!isNaN(stakeValue) && stakeValue > 1){
			placeBet(stakeValue);
		}
		else{
			toaster.create({description: "Wpisz prawidłową obstawianą wartość", type:"error"});
		}
	}

	const potentialWin = betSlip ? (parseFloat(stake || "0") * betSlip.totalOdds).toFixed(2): '0.00';

	return (
		<Drawer.Root open={isOpen} onOpenChange={(e) => setIsOpen(e.open)} placement={'end'}>
			<Drawer.Backdrop />
			<Drawer.Positioner>
				<Drawer.Content>
					<Drawer.Header>
						<Flex justify={'space-between'} align={'center'}>
							<Drawer.Title color={'cyan'}>Twój Kupon</Drawer.Title>
							<Drawer.CloseTrigger asChild>
								<IconButton size="sm" variant="ghost">
									<LuX />
								</IconButton>
							</Drawer.CloseTrigger>
						</Flex>
					</Drawer.Header>
					<Drawer.Body bg={'gray.50'} p={4}>
						{(!betSlip || betSlip.betPicks.length === 0) ? (
							<Box textAlign={'center'} color={'gray.500'} mt={10}>
								Kupon jest pusty. Dodaj mecze z oferty
							</Box>
						): (
							<VStack gap={4} align={'stretch'}>
								{betSlip.betPicks.map((pick) => (
									<Card.Root p={3}>
										<HStack justify={'space-between'} align={'start'}>
											<Box>
												<Text fontWeight={'bold'} fontSize={'small'}>{pick.homeTeam} VS {pick.awayTeam}</Text>
												<Text fontSize={'xs'} color={'gray.500'}>
													Twój typ: <Text as="span" fontWeight="bold" color="cyan.600">{pick.option}</Text>
												</Text>
											</Box>
											<HStack align="center">
                                            <Text fontWeight="bold" color="blue.600">{pick.selectedOdds}</Text>
                                            <IconButton 
                                                size="xs" 
                                                colorPalette="red" 
                                                variant="ghost" 
                                                onClick={() => removePick(pick.id)}
                                                disabled={isLoading}
                                            >
                                                <LuTrash2 />
                                            </IconButton>
                                        </HStack>
										</HStack>
									</Card.Root>
								))}
							</VStack>
						)}
					</Drawer.Body>
					<Drawer.Footer display="block" borderTopWidth="1px">
                <VStack gap={3} align="stretch">
                    <HStack justify="space-between">
                        <Text color="white">Kurs całkowity:</Text>
                        <Text fontWeight="bold" color={'white'}>{betSlip?.totalOdds?.toFixed(2) || "1.00"}</Text>
                    </HStack>
                    <HStack justify="space-between">
                        <Text color="white">Stawka (PLN):</Text>
                        <Input 
                            width="100px" 
                            size="sm" 
                            textAlign="right" 
                            value={stake}
                            onChange={(e) => setStake(e.target.value)}
                            type="number"
							color={'white'}
                        />
                    </HStack>
                    <Separator />
                    <HStack justify="space-between" fontSize="lg" fontWeight="bold">
                        <Text color="white">Ewentualna wygrana:</Text>
                        <Text color="white">{potentialWin} PLN</Text>
                    </HStack>
                    <Button 
                        colorPalette="cyan" 
                        size="lg" 
                        width="full" 
                        onClick={handlePlaceBet}
                        loading={isLoading}
                        disabled={!betSlip || betSlip.betPicks.length === 0}
                    >
                        Postaw zakład
                    </Button>
                </VStack>
            </Drawer.Footer>
				</Drawer.Content>
			</Drawer.Positioner>
		</Drawer.Root>
	)
}