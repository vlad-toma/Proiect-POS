import React, { useEffect, useState } from 'react';

interface Discipline {
  cod: string;
  numeDisciplina: string;
  anStudiu: number;
  tipDisciplina: string;
  categorieDisciplina: string;
  tipExaminare: string;
}

interface StudentViewProps {
  emailId: string;
}

const API_URL = 'http://localhost:8080/api/academia/studenti';

const StudentView: React.FC<StudentViewProps> = ({ emailId }) => {
  const [discipline, setDiscipline] = useState<Discipline[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchDiscipline = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) throw new Error('No token found.');

        const response = await fetch(`${API_URL}/${emailId}/discipline`, {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          throw new Error('Failed to fetch disciplines.');
        }

        const data = await response.json();
        setDiscipline(data);
      } catch (err: any) {
        setError(err.message || 'An error occurred.');
      }
    };

    fetchDiscipline();
  }, [emailId]);

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <div>
      <h1>My Disciplines</h1>
      {discipline.length > 0 ? (
        <ul>
          {discipline.map((disciplina) => (
            <li key={disciplina.cod}>
              <strong>{disciplina.numeDisciplina}</strong> - Year {disciplina.anStudiu} - {disciplina.tipDisciplina}
            </li>
          ))}
        </ul>
      ) : (
        <p>No disciplines found.</p>
      )}
    </div>
  );
};

export default StudentView;
