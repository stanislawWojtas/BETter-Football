import type { Friendship } from "@/services/friendsService"
import { Avatar, Box, HStack, Text, VStack } from "@chakra-ui/react"
import { useNavigate } from "react-router-dom"

// Render a friend entry, tolerating incomplete requester/addressee payloads
const FriendCard = ({friend} : {friend: Friendship}) => {
	const navigate = useNavigate();
	const currentUserId = Number(localStorage.getItem('userId'));
	const requester = friend.requester ?? null;
	const addressee = friend.addressee ?? null;
	const otherUser = requester?.id === currentUserId ? addressee ?? requester : requester ?? addressee;
	const friendUsername = otherUser?.username ?? "Nieznany użytkownik";
	const friendId = otherUser?.id;

	const handleClick = () => {
		if (!friendId) return;
		navigate(`/friends/${friendId}/bets`, { state: { friendUsername } });
	};

	return (
		<>
			<Box border={"3px solid blue"} borderRadius={'lg'} p={4} bg={'white'} _hover={{boxShadow: 'md'}} cursor={"pointer"} onClick={handleClick}>
				<HStack gap={4}>
					<Avatar.Root size='md'>
						<Avatar.Image src={"/avatar.png"} objectFit={'contain'}/>
						<Avatar.Fallback name={friendUsername}  />
					</Avatar.Root>
					<VStack align={'start'} spaceX={0}>
						<Text fontWeight={'bold'} fontSize={'lg'} >{friendUsername}</Text>
						{/* TODO: zmień na prawdziwy budzet */}
						<Text fontSize={'sm'} color={otherUser.balance > 0 ? 'green.500' : 'red.500'}>{otherUser.balance}$</Text>
					</VStack>
				</HStack>
			</Box>
		</>
	)
}

export default FriendCard;