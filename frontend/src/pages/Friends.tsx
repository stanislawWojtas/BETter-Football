import { friendsService, type Friendship } from "@/services/friendsService";
import { Navbar } from "./MainPage";
import { Box, Button, Flex, Heading, Separator, SimpleGrid, Spinner, Text } from "@chakra-ui/react";
import FriendCard from "@/components/Friends/FriendCard";
import FriendInvitation from "@/components/Friends/FriendInvitation";
import { useEffect, useState } from "react";
import AddFriend from "@/components/Friends/AddFriend";


const FriendsPage = () => {

  const [friends, setFriends] =  useState<Friendship[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [invitations, setInvitations] = useState<Friendship[]>([]);
  const [isAddForm, setIsAddForm] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try{
        const friendsData = await friendsService.getAllFriends();
        setFriends(friendsData);
        const invitationsData = await friendsService.getAllRequests();
        setInvitations(invitationsData);
      } catch(e){
        console.error("Failed to fetch friends data", e);
      }
      finally{
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);



	return (
		<>
			<Box minH={'100vh'} bg={'gray.50'} textAlign={'center'}>
				<Navbar />
				<Text alignSelf={"center"} color={'blue.500'} m={8} fontSize={'3xl'} fontWeight={'bold'} letterSpacing={4}>Twoi Znajomi</Text>
				<Box m={5}>
          <SimpleGrid columns={{base: 1, md:2, lg:3, xl:4}} gap={6}>
            {friends.length === 0 ? (
              <Text color={'gray.500'} m={5}>Nie masz jeszcze żadnych znajomych. </Text>
            ) : (
              friends.map(friend => (<FriendCard key={friend.id} friend={friend} />))
            )}
          </SimpleGrid>
          <Separator m={5}/>
          <Flex justify={'space-between'} m={5}>
            <Heading as={'h3'} size={'lg'} color={'blue.500'}>Otrzymane zaproszenia</Heading>
            <Button colorScheme={'blue'} bg={'blue.400'} size={'md'} shadow={'md'} onClick={() => setIsAddForm(!isAddForm)}>{isAddForm ? "Anuluj" : "Zaproś nowego znajomego"}</Button>
          </Flex>
          {isLoading ? (
            <Flex justify={'center'} py={10}>
              <Spinner size={'xl'} color={'blue.400'} />
            </Flex>
          ) : ( isAddForm ? (
            <AddFriend />
          ) : 
            (<SimpleGrid columns={{base: 1, md:2, lg:3, xl:4}} gap={6}>
              {invitations.length === 0 ? (
                <Text color={'gray.500'} m={5}>Brak nowych zaproszeń</Text>
              ) : (
                invitations.map(invitation => (<FriendInvitation key={invitation.id} friend={invitation} />))
              )}
            </SimpleGrid>)
          )}
        </Box>
			</Box>
			
		</>
	)

}


export default FriendsPage;