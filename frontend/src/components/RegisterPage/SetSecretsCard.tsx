import { Box, Input, Field, VStack, Center, Button, Flex } from "@chakra-ui/react";
import { PasswordInput, PasswordStrengthMeter,} from "@/components/ui/password-input";
import { getPasswordStrength } from "@/utils/PasswordStrengthMeter";

interface SetSecretsCardProps{
    onEmailChange: (email: string) => void;
    onPasswordChange: (password: string) => void;
    onRepeatPasswordChange: (confirmedPassword: string) => void;
    onClickNext: () => void;
    goToAvatarSelectionDisabled: boolean;
    password: string
}

export const SetSecretsCard = ( {onEmailChange, onPasswordChange, onRepeatPasswordChange, onClickNext, goToAvatarSelectionDisabled, password}: SetSecretsCardProps) => {
     return (
        <Center>
            <VStack w={"70%"} gap={5}>
                <Field.Root required>
                    <Input 
                        placeholder="Enter your email" 
                        variant={'flushed'} 
                        size={'sm'}
                        onChange={(e) => { onEmailChange(e.currentTarget.value)}}
                    />
                    <Field.ErrorText>This field is required</Field.ErrorText>
                </Field.Root>
                <Field.Root required>
                    <PasswordInput 
                        placeholder="Enter password" 
                        variant={'flushed'} 
                        size={'sm'}
                        onChange={(e) => { onPasswordChange(e.currentTarget.value)}}
                    />
                    <PasswordStrengthMeter w={'80%'} value={getPasswordStrength(password)} paddingTop={2} align={'left'} />
                    <Field.ErrorText>This field is required</Field.ErrorText>
                </Field.Root>
                <Field.Root required>
                    <PasswordInput 
                        placeholder="Repeat password" 
                        variant={'flushed'} 
                        size={'sm'}
                        onChange={(e) => { onRepeatPasswordChange(e.currentTarget.value)}}
                    />
                    <Field.ErrorText>The passwords must match.</Field.ErrorText>
                </Field.Root>
                <Box w="100%">
                    <Flex justify="flex-end">
                        <Button 
                            colorPalette={'cyan'} 
                            variant={'solid'} 
                            disabled={goToAvatarSelectionDisabled}
                            onClick={onClickNext}>
                                Next
                        </Button>
                    </Flex>
                </Box>
            </VStack>,
        </Center>
     );
}