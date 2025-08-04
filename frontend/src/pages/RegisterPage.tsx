import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, Tabs, Center, Flex, Spinner } from "@chakra-ui/react";
import { Toaster, toaster } from "@/components/ui/toaster";
import { validateName, validateUsername, validateConfirmedPassword, validateEmail, validatePassword } from '@/utils/UserDataValidator';
import { AboutYouCard } from '@/components/RegisterPage/AboutYouCard';
import { SetSecretsCard } from '@/components/RegisterPage/SetSecretsCard';
import { ChooseAvatarCard } from '@/components/RegisterPage/ChooseAvatarCard';

// avatarów jeszcze nie ma
const avatars = Array.from({ length: 9 }, (_, i) => `avatar${i + 1}.jpg`);
export const MAX_CHARACTERS = 30;

const RegisterPage = () => {
    const navigate = useNavigate();
    const [open, setOpen] = React.useState(false);
    const [selectedAvatar, setSelectedAvatar] = React.useState("avatar1.jpg");
    const [finishDisabled, setFinishDisabled] = React.useState(true);
    const [chooseAvatarDisabled, setChooseAvatarDisabled] = React.useState(true);
    const [aboutYouDisabled, setAboutYouDisabled] = React.useState(false);
    const [setSecretsDisabled, setSetSecretsDisabled] = React.useState(false);
    const [firstName, setFirstName] = React.useState("");
    const [lastName, setLastName] = React.useState("");
    const [username, setUsername] = React.useState("");
    const [emailAddress, setEmailAddress] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [confirmedPassword, setConfirmedPassword] = React.useState("");
    const [goToAvatarSelectionDisabled, setGoToAvatarSelectionDisabled] = React.useState(true);
    const [isLoading, setIsLoading] = React.useState(false);


    React.useEffect(() => {
        const allValid = 
            validateName(firstName) &&
            validateName(lastName) && 
            validateUsername(username) &&
            validateEmail(emailAddress) &&
            validatePassword(password) &&
            validateConfirmedPassword(confirmedPassword, password);
        setGoToAvatarSelectionDisabled(!allValid);
    }, [firstName, lastName, username, emailAddress, password, confirmedPassword]);

    const handleFinish = () => {
        toaster.create({
          description: "Registered successfully!",
          type: "success",
          closable: true,
        });
        setTimeout(() => {
            navigate('/login');
        }, 2000);
    };

    const handleAvatarButtonClick = () => {
        setOpen(true);
        setFinishDisabled(false);
    }

    const handleClick = (avatar: string) => {
        setOpen(false);
        setSelectedAvatar(avatar);
    };

    const cards = [
    {
        title: "About you",
        content: <AboutYouCard 
                    onFirstNameChange={setFirstName} 
                    onLastNameChange={setLastName}
                    onUsernameChange={setUsername}
                    username={username}
                    onClickNext={() => setValue(cards[1].title)}/>,
        disabled: aboutYouDisabled
    },
    {
        title: "Set secrets",
        content: <SetSecretsCard 
                    onEmailChange={setEmailAddress}
                    onPasswordChange={setPassword}
                    onRepeatPasswordChange={setConfirmedPassword}
                    onClickNext={() => { 
                        setChooseAvatarDisabled(false);
                        setSetSecretsDisabled(true);
                        setAboutYouDisabled(true);
                        setValue(cards[2].title);
                    }}
                    goToAvatarSelectionDisabled={goToAvatarSelectionDisabled}
                />,
        disabled: setSecretsDisabled
    },
    {
        title: "Choose avatar",
        content: <ChooseAvatarCard 
                    selectedAvatar={selectedAvatar}
                    firstName={firstName}
                    lastName={lastName}
                    finishDisabled={finishDisabled}
                    handleFinish={handleFinish}
                    open={open}
                    setOpen={setOpen}
                    handleClick={handleClick}
                    avatars={avatars}
                    handleAvatarButtonClick={handleAvatarButtonClick}
                />,
        disabled: chooseAvatarDisabled
    },
    ];

    const [value, setValue] = React.useState<string | null>(cards[0].title);

    return (
        <>
        {isLoading ? 
            <Center position="fixed" inset={0} zIndex={1000}>
                <Spinner size="xl" />
            </Center>
        :
        <>
        <Box position="absolute" top="45%" left="50%" transform="translate(-50%, -50%)" 
            backgroundColor={'white'} height={'60vh'} width={'40vw'} borderRadius={12}>
            <Center paddingTop={5}>
                <Flex minH="dvh" w={"90%"}>
                <Tabs.Root colorPalette={'cyan'} defaultValue={cards[0].title} width="full" value={value} onValueChange={(e) => setValue(e.value)}>
                    <Tabs.List>
                    {cards.map((item, index) => (
                        <Tabs.Trigger w={"33%"} color={'cyan.700'} key={index} value={item.title} disabled={item.disabled}>
                        {item.title}
                        </Tabs.Trigger>
                    ))}
                    </Tabs.List>
                    <Box pos="relative" minH="200px" width="full">
                    {cards.map((item, index) => (
                        <Tabs.Content
                        key={index}
                        value={item.title}
                        position="absolute"
                        inset="0"
                        _open={{
                            animationName: "fade-in, scale-in",
                            animationDuration: "300ms",
                        }}
                        _closed={{
                            animationName: "fade-out, scale-out",
                            animationDuration: "120ms",
                        }}
                        >
                        {item.content}
                        </Tabs.Content>
                    ))}
                    </Box>
                </Tabs.Root>
                </Flex>
            </Center>
        </Box>
        <Toaster />
        </>
        }
        </>
    );
};

export default RegisterPage;