import * as React from 'react';

import './App.css';

import AppRoutes from './routes/AppRoutes';
import { BetSlipProvider } from './context/BetSlipContext';

function App() {
  return (
    <BetSlipProvider>
      <AppRoutes />
    </BetSlipProvider>
  );

}

export default App;
