import { Box, Heading, Input, Field, VStack, Center, Button, Flex } from "@chakra-ui/react";
import { PasswordInput, PasswordStrengthMeter,} from "@/components/ui/password-input"

const LoginPage = () => {
    return (
        <Box position="absolute" top="45%" left="50%" transform="translate(-50%, -50%)" 
            backgroundColor={'white'} height={'60vh'} width={'30vw'} borderRadius={12}>
            <Center paddingTop={10}>
                <VStack w={"70%"} gap={10}>
                    <Heading>Welcome again!</Heading>
                    <Field.Root required>
                        <Input placeholder="Enter your email" variant={'flushed'} size={'sm'}/>
                        <Field.ErrorText>This field is required</Field.ErrorText>
                    </Field.Root>
                    <Field.Root required>
                            <PasswordInput placeholder="Enter password" variant={'flushed'} size={'sm'}/>
                            <PasswordStrengthMeter w={'80%'} value={3} paddingTop={2} align={'left'} />
                        <Field.ErrorText>This field is required</Field.ErrorText>
                    </Field.Root>
                      <Box w="100%">
                        <Flex justify="flex-end">
                        <Button colorPalette={'cyan'} variant={'solid'}>Submit</Button>
                        </Flex>
                    </Box>
                </VStack>
            </Center>
        </Box>
    );
};

export default LoginPage;