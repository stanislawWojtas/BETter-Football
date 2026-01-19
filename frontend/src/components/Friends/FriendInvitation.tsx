import { api } from "@/api/axios";
import { friendsService, type Friendship } from "@/services/friendsService"
import { Avatar, Box, Button, HStack, Text, VStack } from "@chakra-ui/react"

// Invitation card; handle null requester/addressee from backend
const FriendInvitation = ({friend} : {friend: Friendship}) => {
	const currentUserId = Number(localStorage.getItem('userId'));
	const requester = friend.requester ?? null;
	const addressee = friend.addressee ?? null;
	const otherUser = requester?.id === currentUserId ? addressee ?? requester : requester ?? addressee;
	const friendUsername = otherUser?.username ?? "Nieznany użytkownik";

	return(
		<>
			<Box border={"3px solid blue"} borderRadius={'lg'} p={4} bg={'white'}>
				<HStack gap={4}>
					<Avatar.Root size='md'>
						<Avatar.Image src={"/avatar.png"} objectFit={'contain'}/>
						<Avatar.Fallback name={friendUsername}  />
					</Avatar.Root>
					<VStack align={'start'} spaceX={0}>
						<Text fontWeight={'bold'} fontSize={'lg'} >{friendUsername}</Text>
					</VStack>
				</HStack>
				<HStack gap={3} w={'100%'}>
					<Button
					 bg={'green.400'} 
					 size={'sm'} flex={1}
					  onClick={() => friendsService.acceptFriendRequest(requester?.id ?? 0).then(() => alert("Zaakceptowano zaproszenie"))}
					   cursor={'pointer'}>Akceptuj</Button>
					<Button
					 bg={'red.400'}
					  size={'sm'}
					   flex={1}
					    onClick={() => friendsService.declineFriendRequest(requester?.id ?? 0).then(() => alert("Odrzucono zaproszenie"))}
						 cursor={'pointer'}>Odrzuć</Button>
				</HStack>
			</Box>
		</>
	)
}

export default FriendInvitation;