import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import StudentView from './StudentView';
import ProfessorView from './ProfessorView';
import AdminView from './AdminView';

const Catalog: React.FC = () => {
  const [role, setRole] = useState<string | null>(null);
  const [emailId, setEmailId] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1])); // Decodăm payload-ul JWT
      setRole(payload.role);
      setEmailId(payload.sub); // Extragem `sub` și îl convertim la număr
    } catch (err) {
      setError('Invalid token format.');
      navigate('/login');
    }
  }, [navigate]);

  if (error) {
    return <p>{error}</p>;
  }

  if (!role || !emailId) {
    return <p>Loading...</p>;
  }

  // Redirecționăm în funcție de rol
  switch (role) {
    case 'student':
      return <StudentView emailId={emailId} />;
    case 'profesor':
      return <ProfessorView emailId={emailId} />;
    case 'admin':
      return <AdminView />;
    default:
      return <p>Invalid role. Please log in again.</p>;
  }
};

export default Catalog;
