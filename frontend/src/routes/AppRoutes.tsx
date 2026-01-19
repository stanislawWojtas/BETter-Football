import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from '@/pages/LoginPage';
import RegisterPage from '@/pages/RegisterPage';
import Navbar from '@/pages/MainPage';
import MyBetsPage from '@/pages/MyBetsPage';
import FriendsPage from '@/pages/Friends';
import FriendBetsPage from '@/pages/FriendBetsPage';

const AppRoutes = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Navbar />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path='/my-bets' element={<MyBetsPage/>} />
      <Route path='/friends' element={<FriendsPage />} />
      <Route path='/friends/:friendId/bets' element={<FriendBetsPage />} />
    </Routes>
  </BrowserRouter>
);

export default AppRoutes;