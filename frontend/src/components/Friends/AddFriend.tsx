import { friendsService } from "@/services/friendsService";
import { Box, Button, Input } from "@chakra-ui/react";
import { useState } from "react";

const AddFriend = () => {

	const [username, setUsername] =  useState<string>("");

	const handleAddFriend = () => {
		if(username.trim() === ""){
			alert("Podaj nazwę użytkownika");
			return;
		}
		friendsService.sendFriendRequest(username).then(() => alert("Wysłano zaproszenie"));
	}

	return(
		<>
			<Box m={'auto'} w={80} border={'2px solid blue'} borderRadius={'lg'} p={4} bg={'white'} >
				<Input placeholder="username znajomego" value={username} onChange={(e) => setUsername(e.target.value)} />
				<Button m={8} onClick={handleAddFriend} color={'white'} bg={'blue.400'} fontWeight={'bold'} _hover={{ bg: 'blue.500' }} >Wyślij zaproszenie</Button>
			</Box>
		</>
	)
}

export default AddFriend;