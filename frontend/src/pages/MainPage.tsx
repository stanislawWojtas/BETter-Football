import { useEffect, useState, useMemo, useDeferredValue, startTransition } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, Button, HStack, Flex, Spacer, Link, Container, Grid, Spinner, Center, Heading, Tabs, Input } from "@chakra-ui/react"
import { matchService } from '@/services/matchService';
import { MatchCard } from '@/components/Matches/MatchCard';
import { AuthService } from '@/services/authService';
import type { MatchOdds } from '@/services/matchService';
// import { FiSearch } from 'react-icons/fi';

export const Navbar = () => {
  const navigate = useNavigate();
  const isLoggedIn = AuthService.isAuthenticated();

  const handleLogout = () => {
    AuthService.logout();
    navigate('/login');
  };

  return (
    <Box bg="blue.600" px={8} py={3} color="white" boxShadow="md" position="sticky" top={0} zIndex={10}>
      <Flex align="center" maxW="1200px" mx="auto">
        <Heading size="lg" color={'white'} fontFamily="heading">BETter Football</Heading>
        <Spacer />
        <HStack gap={6}>
          <Link color={'white'} fontWeight="medium" href="/">Mecze</Link>
          {isLoggedIn ? (
             <Button 
               bg="white" 
               color="blue.600" 
               size="sm" 
               rounded="full" 
               px={6}
               _hover={{ bg: "gray.100" }}
               onClick={handleLogout}
             >
               Wyloguj
             </Button>
          ) : (
            <>
              <Link color={'white'} onClick={() => navigate("/login")}>Logowanie</Link>
              <Button 
                variant="outline" 
                borderColor="white" 
                color="white" 
                size="sm" 
                rounded="full"
                _hover={{ bg: "whiteAlpha.200" }}
                onClick={() => navigate("/register")}
              >
                Rejestracja
              </Button>
            </>
          )}
        </HStack>
      </Flex>
    </Box>
  )
}

type SearchBarProps = {
  query: string;
  setQuery: (value: string) => void;
  isSearching: boolean;
};

const SearchBar = ({ query, setQuery, isSearching }: SearchBarProps) => {
  return (
    <Box mb={8} maxW="600px" mx="auto" position="relative">
      <Input
        placeholder='Szukaj zespołu...'
        bg='white'
        value={query}
        onChange={e => setQuery(e.target.value)}
        pr="2.5rem"
      />
      {isSearching && (
        <Box position="absolute" right="10px" top="50%" transform="translateY(-50%)">
          <Spinner size="sm" color="blue.500" />
        </Box>
      )}
    </Box>
  );
};


const MainPage = () => {
  const [originalMatches, setOriginalMatches] = useState<MatchOdds[]>([]);
  const [matches, setMatches] = useState<MatchOdds[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedLeague, setSelectedLeague] = useState<string | null>("ALL");
  const [query, setQuery] = useState<string>("");
  const [isSearching, setIsSearching] = useState<boolean>(false);
  const deferredQuery = useDeferredValue(query);

  // Mapowanie zakładek na kraje (zgodnie z LeagueConfig.java w backendzie)
  // null = wszystkie, "England" = EPL, "Spain" = La Liga itd.
  const leagues = [
    { label: "Wszystkie", value: "ALL", countryParam: undefined },
    { label: "Premier League", value: "EPL", countryParam: "England" },
    { label: "La Liga", value: "LA_LIGA", countryParam: "Spain" },
    { label: "Bundesliga", value: "BUNDESLIGA", countryParam: "Germany" },
    { label: "Serie A", value: "SERIE_A", countryParam: "Italy" },
    { label: "Ligue 1", value: "LIGUE_1", countryParam: "France" },
  ];

  const fetchMatches = async (country?: string) => {
    setIsLoading(true);
    try {
      const data = await matchService.getUpcoming(country);
      setOriginalMatches(data);      // zapisujemy dane źródłowe
      setMatches(data);              // początkowo pokazujemy całość
    } catch (error) {
      console.error("Failed to fetch matches", error);
      setOriginalMatches([]);
      setMatches([]);
    } finally {
      setIsLoading(false);
    }
  };

  // Pobierz mecze przy zmianie wybranej ligi
  useEffect(() => {
    const league = leagues.find(l => l.value === selectedLeague);
    fetchMatches(league?.countryParam);
  }, [selectedLeague]);

  // Debounce lokalnego wyszukiwania: 800 ms po zakończeniu pisania
  useEffect(() => {
    const trimmed = deferredQuery.trim().toLowerCase();

    if (!trimmed) {
      setIsSearching(false);
      startTransition(() => setMatches(originalMatches));
      return;
    }

    setIsSearching(true);
    const t = setTimeout(() => {
      const filtered = originalMatches.filter(m => {
        const home = (m.homeTeam || "").toLowerCase();
        const away = (m.awayTeam || "").toLowerCase();
        const title = `${home} ${away}`.toLowerCase();
        return home.includes(trimmed) || away.includes(trimmed) || title.includes(trimmed);
      });
      startTransition(() => setMatches(filtered));
      setIsSearching(false);
    }, 800);

    return () => clearTimeout(t);
  }, [deferredQuery, originalMatches]);

  return (
    <Box minH="100vh" bg="gray.50">
      <Navbar />
      
      <Container maxW="1200px" py={8}>
        {/* Filtry Lig */}
        <Box mb={8}>
          <Tabs.Root 
            value={selectedLeague} 
            onValueChange={(e) => setSelectedLeague(e.value)} 
            variant="plain"
          >
            <Tabs.List
              bg="white"
              p={1}
              rounded="lg"
              shadow="sm"
              display="flex"
              w="fit-content"
              mx="auto"
            >
              {leagues.map(league => (
                <Tabs.Trigger 
                  key={league.value} 
                  value={league.value}
                  px={4}
                  py={2}
                  rounded="md"
                  _selected={{ bg: "blue.50", color: "blue.600", fontWeight: "bold" }}
                >
                  {league.label}
                </Tabs.Trigger>
              ))}
            </Tabs.List>
          </Tabs.Root>
        </Box>

        <SearchBar query={query} setQuery={setQuery} isSearching={isSearching} />
        

        {/* Lista Meczów */}
        {isLoading ? (
          <Center h="200px">
            <Spinner size="xl" color="blue.500" />
          </Center>
        ) : (
          <Grid templateColumns={{ base: "1fr", md: "repeat(2, 1fr)", lg: "repeat(2, 1fr)" }} gap={6}>
            {matches.length > 0 ? (
              matches.map((match) => (
                <MatchCard key={match.id} match={match} />
              ))
            ) : (
              <Center gridColumn="1 / -1" p={10}>
                <Heading size="md" color="gray.500">
                  {isSearching ? 'Brak wyników wyszukiwania.' : 'Brak nadchodzących meczów w tej lidze.'}
                </Heading>
              </Center>
            )}
          </Grid>
        )}
      </Container>
    </Box>
  );
};

export default MainPage;