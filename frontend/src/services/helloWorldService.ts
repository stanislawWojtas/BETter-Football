interface Message{
    message: string;
}

export const getMessage = async (): Promise<string> => {
    const response = await fetch("http://localhost:8080/api/message", {credentials: "include"});
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data: Message = await response.json();
    return data.message;
}