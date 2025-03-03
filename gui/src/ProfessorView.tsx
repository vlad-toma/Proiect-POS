import React, { useEffect, useState } from 'react';
import './Catalogs.css';

interface Discipline {
  cod: string;
  numeDisciplina: string;
  anStudiu: number;
  tipDisciplina: string;
  categorieDisciplina: string;
  tipExaminare: string;
}

interface ProfessorViewProps {
  emailId: string;
}

const API_URL = 'http://localhost:8080/api/academia/profesori';

const ProfessorView: React.FC<ProfessorViewProps> = ({ emailId }) => {
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
        const disciplineData = data._embedded?.disciplineDTOes || [];
        setDiscipline(disciplineData);
      } catch (err: any) {
        setError(err.message || 'An error occurred.');
      }
    };

    fetchDiscipline();
  }, [emailId]);

  if (error) {
    return <p className="error-message">{error}</p>;
  }

  return (
    <div className="catalog-container">
      <h1>My Disciplines</h1>
      {discipline.length > 0 ? (
        <div className="catalog-list">
          {discipline.map((disciplina) => (
            <div key={disciplina.cod} className="catalog-item">
              <h2>{disciplina.numeDisciplina}</h2>
              <p>
                <strong>Year:</strong> {disciplina.anStudiu}
              </p>
              <p>
                <strong>Type:</strong> {disciplina.tipDisciplina}
              </p>
              <p>
                <strong>Category:</strong> {disciplina.categorieDisciplina}
              </p>
              <p>
                <strong>Examination Type:</strong> {disciplina.tipExaminare}
              </p>
            </div>
          ))}
        </div>
      ) : (
        <p>No disciplines found.</p>
      )}
    </div>
  );
};

export default ProfessorView;
