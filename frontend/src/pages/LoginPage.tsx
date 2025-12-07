import { Box, Heading, Input, Field, VStack, Center, Button, Flex, HStack, Text } from "@chakra-ui/react";
import { PasswordInput, PasswordStrengthMeter,} from "@/components/ui/password-input"
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { Toaster, toaster } from "@/components/ui/toaster";
import { AuthService } from "@/services/authService";
import { Navbar } from "./MainPage";

const LoginPage = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const handleLogin = async () => {
        if(!username || !password){
            toaster.create({
                description: "Please fill in all fields",
                type: "error",
            });
            return;
        }

        setIsLoading(true);
        try{
            await AuthService.login({username, password});

            toaster.create({
                description: "Logged in successfully!",
                type: "success", // fixed typo
            });

            setTimeout(() => {
                navigate('/'); //navigate to home page
            }, 1000);
        } catch (error: any){
            console.error("Login error: ", error);
            const message = error.response?.data || "Invalid credentials or server error";

            toaster.create({
                description: typeof message === 'string' ? message : "Login Failed",
                type: "error",
            });
        } finally{
            setIsLoading(false);
        }

    }


    return (
        <>
            <Navbar />
            <Box position="absolute" top="45%" left="50%" transform="translate(-50%, -50%)" 
                backgroundColor={'white'} height={'60vh'} width={'30vw'} borderRadius={12} boxShadow="lg">
                <Center paddingTop={10} h="100%">
                    <VStack w={"70%"} gap={10}>
                        <Heading color="brand.800">Welcome back!</Heading>
                        
                        <Field.Root required>
                            <Input 
                                placeholder="Enter your username" 
                                variant={'flushed'} 
                                size={'sm'}
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                disabled={isLoading}
                            />
                            <Field.ErrorText>This field is required</Field.ErrorText>
                        </Field.Root>

                        <Field.Root required>
                            <PasswordInput 
                                placeholder="Enter password" 
                                variant={'flushed'} 
                                size={'sm'}
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                disabled={isLoading}
                                onKeyDown={(e) => e.key === 'Enter' && handleLogin()}
                            />
                            <Field.ErrorText>This field is required</Field.ErrorText>
                        </Field.Root>

                        <Box w="100%">
                            <Flex justify="space-between" align="center">
                                <HStack>
                                    <Text fontSize="sm" color="gray.600">Don’t have an account?</Text>
                                    <Button
                                        variant={"plain"}
                                        colorPalette="cyan"
                                        onClick={() => navigate('/register')}
                                        disabled={isLoading}
                                    >
                                        Register
                                    </Button>
                                </HStack>
                                <Button 
                                    colorPalette={'cyan'} 
                                    variant={'solid'} 
                                    onClick={handleLogin}
                                    loading={isLoading}
                                    loadingText="Logging in..."
                                >
                                    Submit
                                </Button>
                            </Flex>
                        </Box>
                    </VStack>
                </Center>
            </Box>
           <Toaster />
        </>
    );
};

export default LoginPage;