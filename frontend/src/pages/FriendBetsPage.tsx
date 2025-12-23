import { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { betSlipService, type BetSlip } from "@/services/betSlipService";
import { Navbar } from "./MainPage";
import { Badge, Box, Button, Card, Center, Container, Flex, Grid, Heading, Separator, Spinner, Text, VStack } from "@chakra-ui/react";

interface LocationState {
  friendUsername?: string;
}

const FriendBetsPage = () => {
  const { friendId } = useParams<{ friendId: string }>();
  const friendIdNumber = useMemo(() => Number(friendId), [friendId]);
  const navigate = useNavigate();
  const { state } = useLocation() as { state?: LocationState };
  const friendUsername = state?.friendUsername ?? "Znajomy";

  const [history, setHistory] = useState<BetSlip[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchHistory = async () => {
      if (!friendId || Number.isNaN(friendIdNumber)) {
        setIsLoading(false);
        return;
      }

      try {
        const data = await betSlipService.getFriendHistory(friendIdNumber);
        setHistory(data ?? []);
      } catch (err) {
        console.error("Failed to fetch friend history", err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchHistory();
  }, [friendId, friendIdNumber]);

  const getStatusColor = (status: string) => {
    switch (status) {
      case "WON":
        return "green";
      case "LOST":
        return "red";
      case "PLACED":
        return "blue";
      default:
        return "gray";
    }
  };

  const renderContent = () => {
    if (!friendId || Number.isNaN(friendIdNumber)) {
      return (
        <Center p={10} bg={"white"} rounded={"lg"} shadow={"sm"}>
          <VStack gap={4}>
            <Text color={"gray.500"}>Niepoprawny identyfikator znajomego.</Text>
            <Button colorScheme="blue" onClick={() => navigate("/friends")}>Wróć do listy znajomych</Button>
          </VStack>
        </Center>
      );
    }

    if (isLoading) {
      return (
        <Center h={"200px"}>
          <Spinner size={"xl"} color={"blue.500"} />
        </Center>
      );
    }

    if (history.length === 0) {
      return (
        <Center p={10} bg={"white"} rounded={"lg"} shadow={"sm"}>
          <Text color={"gray.500"}>Ten znajomy nie ma jeszcze żadnych zakładów.</Text>
        </Center>
      );
    }

    return (
      <VStack gap={6} align="stretch">
        {history.map((slip) => (
          <Card.Root
            key={slip.id}
            overflow="hidden"
            variant="elevated"
            borderColor={getStatusColor(slip.status)}
            borderLeftWidth="4px"
          >
            <Card.Header bg="gray.50" py={3}>
              <Flex justify="space-between" align="center">
                <Flex gap={3} align="center">
                  <Badge colorPalette={getStatusColor(slip.status)} size="lg" variant="solid">
                    {slip.status}
                  </Badge>
                  <Text fontSize="sm" color="gray.500">
                    {slip.placedAt ? new Date(slip.placedAt).toLocaleString("pl-PL") : "Brak daty"}
                  </Text>
                </Flex>
                <Text fontWeight="bold" color={"black"}>
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
                      <Text fontSize="xs" color="gray.400">{new Date(pick.matchDate).toLocaleString("pl-PL")}</Text>
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
                      {pick.result === "WIN" && <Badge colorPalette="green">Trafiony</Badge>}
                      {pick.result === "LOSE" && <Badge colorPalette="red">Pudło</Badge>}
                      {pick.result === "PENDING" && <Badge colorPalette="gray" variant="outline">W grze</Badge>}
                    </Box>
                  </Grid>
                ))}
              </VStack>
            </Card.Body>
            <Separator />
            <Card.Footer py={3}>
              <Flex justify="space-between" w="full" align="center">
                <Text fontSize="sm" color="gray.500">Kurs całkowity: <span style={{ fontWeight: "bold", color: "cyan" }}>{slip.totalOdds}</span></Text>
                <Box textAlign="right">
                  <Text fontSize="xs" color="gray.500">Ewentualna wygrana</Text>
                  <Text fontSize="lg" fontWeight="bold" color={slip.status === "WON" ? "green.600" : "brand.800"}>
                    {slip.potentialWin} PLN
                  </Text>
                </Box>
              </Flex>
            </Card.Footer>
          </Card.Root>
        ))}
      </VStack>
    );
  };

  return (
    <Box minH={'100vh'} bg={'gray.50'}>
      <Navbar />
      <Container maxW={'800px'} py={8}>
        <Flex justify="space-between" align="center" mb={6} gap={4} wrap="wrap">
          <Heading size={'xl'} color={'blue.500'}>Zakłady znajomego: {friendUsername}</Heading>
          <Button variant={'outline'} colorPalette={'blue'} onClick={() => navigate('/friends')}>
            Wróć do znajomych
          </Button>
        </Flex>
        {renderContent()}
      </Container>
    </Box>
  );
};

export default FriendBetsPage;
