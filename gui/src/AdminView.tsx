import React, { useState, useEffect } from 'react';
import './Catalogs.css';

interface Nota {
  proba: string;
  nota: number;
}

interface Student {
  idCatalogPromovat: string | null;
  prenume: string;
  nume: string;
  stare: string;
  note: Nota[];
  medie: number;
}

interface Catalog {
  id: string;
  denumireProgramStudii: string;
  anDeStudiu: number;
  numeTitular: string;
  gradDidactic: string;
  studenti: Student[];
}

const API_URL = 'http://localhost:8081/api/academia/catalog';

const Catalogs: React.FC = () => {
  const [catalogs, setCatalogs] = useState<Catalog[]>([]);
  const [error, setError] = useState<string | null>(null);

  const fetchCatalogs = async () => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        throw new Error('No token found. Please log in.');
      }

      const response = await fetch(API_URL, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('Failed to fetch catalogs.');
      }

      const data = await response.json();

      // Procesăm datele pentru a le transforma în formatul dorit
      const catalogData = data._embedded.catalogDisciplinaDTOes.map((item: any) => ({
        id: item.id.timestamp.toString(), // Folosim timestamp ca identificator unic
        denumireProgramStudii: item.denumireProgramStudii,
        anDeStudiu: item.anDeStudiu,
        numeTitular: item.numeTitular,
        gradDidactic: item.gradDidactic,
        studenti: item.studenti.map((student: any) => ({
          prenume: student.prenume,
          nume: student.nume,
          stare: student.stare,
          idCatalogPromovat: student.idCatalogPromovat,
          note: student.note,
          medie: student.medie,
        })),
      }));

      setCatalogs(catalogData);
    } catch (err: any) {
      setError(err.message || 'An error occurred.');
    }
  };

  useEffect(() => {
    fetchCatalogs();
  }, []);

  if (error) {
    return <p className="error-message">{error}</p>;
  }

  return (
    <div className="catalog-container">
      <h1>Catalogs</h1>
      {catalogs.length > 0 ? (
        <div className="catalog-list">
          {catalogs.map((catalog) => (
            <div key={catalog.id} className="catalog-item">
              <h2>{catalog.denumireProgramStudii}</h2>
              <p>
                <strong>Year of Study:</strong> {catalog.anDeStudiu}
              </p>
              <p>
                <strong>Instructor:</strong> {catalog.numeTitular} ({catalog.gradDidactic})
              </p>
              <p>
                <strong>Total Students:</strong> {catalog.studenti.length}
              </p>
              <div className="student-list">
                <h3>Students:</h3>
                {catalog.studenti.length > 0 ? (
                  catalog.studenti.map((student, index) => (
                    <div key={index} className="student-item">
                      <p>
                        <strong>{student.prenume} {student.nume}</strong> - {student.stare}
                      </p>
                      <p>
                        <strong>Average grade:</strong> {student.medie}
                      </p>
                      <div>
                        <strong>Grades:</strong>
                        <ul>
                          {student.note.map((nota, idx) => (
                            <li key={idx}>
                              {nota.proba}: {nota.nota}
                            </li>
                          ))}
                        </ul>
                      </div>
                    </div>
                  ))
                ) : (
                  <p>No students enrolled in this catalog.</p>
                )}
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p>No catalogs available.</p>
      )}
    </div>
  );
};

export default Catalogs;
