import { Box, Heading, VStack, Center, Button, Flex, Avatar, Dialog, Portal, For, CloseButton, Grid } from "@chakra-ui/react";

interface ChooseAvatarCardProps {
    selectedAvatar: string;
    firstName: string;
    lastName: string;
    finishDisabled: boolean;
    handleFinish: () => void;
    open: boolean;
    setOpen: (open: boolean) => void;
    handleClick: (avatar: string) => void;
    avatars: string[];
    handleAvatarButtonClick: () => void;
}

export const ChooseAvatarCard = ({ selectedAvatar, firstName, lastName, finishDisabled, handleFinish, open, setOpen, handleClick, avatars, handleAvatarButtonClick}: ChooseAvatarCardProps) => {
    return (
                <>
                <Center paddingTop={15}>
                    <VStack gap={10}>
                        <Heading>Select avatar from available ones!</Heading>
                            <Avatar.Root size={"2xl"} onClick={() => handleAvatarButtonClick()} className="clickable-avatar">
                                <Avatar.Image src={`avatars/${selectedAvatar}`}/>
                                <Avatar.Fallback name={firstName+lastName} />
                            </Avatar.Root>
                        <Box w="100%" paddingTop={20}>
                            <Flex justify="flex-end">
                                    <Button 
                                        colorPalette={'cyan'} 
                                        disabled={finishDisabled} 
                                        variant={'solid'}
                                        onClick={handleFinish}
                                    >
                                        Finish
                                    </Button>
                            </Flex>
                        </Box>
                    </VStack>
                </Center>
                <Dialog.Root
                        placement={"center"}
                        motionPreset="slide-in-bottom"
                        open={open}
                        onOpenChange={(details) => setOpen(details.open)}
                    >
                        <Portal>
                        <Dialog.Backdrop />
                        <Dialog.Positioner>
                            <Dialog.Content color={"white"} backgroundColor={"cyan.700"}>
                            <Dialog.Header>
                                <Dialog.Title>Choose your favourite!</Dialog.Title>
                            </Dialog.Header>
                            <Dialog.Body>
                                <Grid templateColumns="repeat(3, 1fr)" gap="6" alignItems={"center"} justifyContent={"center"}>
                                    <For each={avatars}>
                                        {(avatar) => (
                                            <Avatar.Root size={"2xl"} onClick={() => handleClick(avatar)} className="clickable-avatar">
                                                <Avatar.Image src={`avatars/${avatar}`}/>
                                                <Avatar.Fallback />
                                            </Avatar.Root>
                                        )}
                                    </For>
                                </Grid>
                            </Dialog.Body>
                            <Dialog.CloseTrigger asChild>
                                <CloseButton size="sm" />
                            </Dialog.CloseTrigger>
                            </Dialog.Content>
                        </Dialog.Positioner>
                        </Portal>
                    </Dialog.Root>
                </>
    );
}