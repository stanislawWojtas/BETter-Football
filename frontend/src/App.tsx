import * as React from 'react';

import { getMessage } from './services/helloWorldService';
import './App.css';

function App() {
  const [message, setMessage] = React.useState<string>("");

  React.useEffect(() => {
    const load = async () => {
      setMessage(await getMessage());
    };
    load();
  },[])
  return (
    <>
      <div>
        <p>{message}</p>
      </div>
    </>
  )
}

export default App
