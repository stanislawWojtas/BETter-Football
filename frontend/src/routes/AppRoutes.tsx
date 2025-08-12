import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from '@/pages/LoginPage';
import RegisterPage from '@/pages/RegisterPage';
import Navbar from '@/pages/MainPage';
import MatchesDashboard from '@/pages/MatchesDashboard';

const AppRoutes = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Navbar />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/matches" element={<MatchesDashboard />} />
    </Routes>
  </BrowserRouter>
);

export default AppRoutes;