import { useState } from 'react'
import './App.css'
import { Routes, Route } from 'react-router-dom'
import Login from './Login'
import Catalogs from './Catalogs'
import { Navigate } from 'react-router-dom';


function App() {
  const [count, setCount] = useState(0)

  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/login" element={<Login />} />
      <Route path="/catalogs" element={<Catalogs />} />
    </Routes>
  )
}

export default App
