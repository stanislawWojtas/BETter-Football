import { Box, Button, HStack, Flex, Spacer, Link } from "@chakra-ui/react"
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
  const navigate = useNavigate();

  const GoToLoginPage = () => {
    navigate("/login")
  }

  return (
    <Box bg="blue.500" px={4} py={3} color="white" borderBottomLeftRadius={6} borderBottomRightRadius={6}>
      <Flex align="center">
        <Box color={'brand.100'} fontWeight="bold">BETter football</Box>
        <Spacer />
        <HStack spaceX={4}>
          <Link color={'brand.100'} href="/">Home</Link>
          <Link color={'brand.100'} href="/about">O nas</Link>
          <Link color={'brand.100'} onClick={() => {navigate("/login")}}>Kontakt</Link>
          <Button backgroundColor={'brand.100'} color={'brand.800'} size="sm" rounded="2xl" variant={'subtle'} onClick={GoToLoginPage}>Zaloguj</Button>
        </HStack>
      </Flex>
    </Box>
  )
}

export default Navbar