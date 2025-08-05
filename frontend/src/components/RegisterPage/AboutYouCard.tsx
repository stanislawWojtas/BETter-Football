import { Box, Input, VStack, Center, Button, Flex, Grid, InputGroup, Span } from "@chakra-ui/react";
import { MAX_CHARACTERS } from "@/pages/RegisterPage";

interface AboutYouCardProps{
    onFirstNameChange: (firstName: string) => void;
    onLastNameChange: (lastName: string) => void;
    onUsernameChange: (userName: string) => void;
    username: string;
    onClickNext: () => void
}

export const AboutYouCard = ({onFirstNameChange, onLastNameChange, onUsernameChange, username, onClickNext}: AboutYouCardProps) => {
    return (
        <Center>
            <VStack w={"70%"} gap={8} paddingTop={10}>
                <Grid templateColumns={"repeat(2, 1fr)"} gap={3}>
                    <Input 
                        placeholder='First name'
                        maxLength={MAX_CHARACTERS}
                        onChange={(e) => onFirstNameChange(e.currentTarget.value.slice(0, MAX_CHARACTERS))}
                    />
                    <Input 
                        placeholder='Last name'
                        maxLength={MAX_CHARACTERS}
                        onChange={(e) => { onLastNameChange(e.currentTarget.value.slice(0, MAX_CHARACTERS))}}
                    />
                </Grid>
                <InputGroup 
                    endElement={
                            <Span color="fg.muted" textStyle="xs">
                                {username.length} / {MAX_CHARACTERS}
                            </Span>
                    }>
                    <Input
                        placeholder="Username"
                        value={username}
                        onChange={(e) => { onUsernameChange(e.currentTarget.value.slice(0, MAX_CHARACTERS))}}
                    />
                </InputGroup>
                <Box w="100%">
                    <Flex justify="flex-end">
                        <Button colorPalette={'cyan'} variant={'solid'} onClick={onClickNext}>Next</Button>
                    </Flex>
                </Box>
            </VStack>
        </Center>
    );
}