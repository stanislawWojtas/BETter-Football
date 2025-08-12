import { useLocation } from "react-router-dom";
import Navbar from "./MainPage";
import { Breadcrumb } from "@chakra-ui/react"

const MatchesDashboard = () => {
    const location = useLocation();

    const pathParts = location.pathname.split("/").filter(Boolean);


    return (
        <>
            <Navbar />
            <Breadcrumb.Root key={'md'} size={'md'} padding={4}>
                <Breadcrumb.List >
                    <Breadcrumb.Item >
                        <Breadcrumb.Link href="/" color={'blue.500'}>Home</Breadcrumb.Link>
                    </Breadcrumb.Item>
                    {pathParts.map((part, index) => {
                        const linkPath = "/" + pathParts.slice(0, index + 1).join("/");
                        return (
                            <>
                                <Breadcrumb.Separator color={'blue.500'}/>
                                <Breadcrumb.Item>
                                    <Breadcrumb.Link color={'blue.500'} href={linkPath}>{part}</Breadcrumb.Link>
                                </Breadcrumb.Item>
                            </>     
                        );
                    })}
                </Breadcrumb.List>
            </Breadcrumb.Root>
        </>
    );
}

export default MatchesDashboard;