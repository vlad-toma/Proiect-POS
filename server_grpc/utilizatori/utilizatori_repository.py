import mariadb
from utilizatori.utilizatori import Utilizatori
class DBManager:
    def __init__(self):
        self.conn = mariadb.connect(
            user="root",
            password="password",
            host="localhost",
            port=3307,
            database="academia"
        )
        self.cursor = self.conn.cursor(dictionary=True)

    def validate_user(self, email, password):
        query = "SELECT email, rol FROM Utilizatori WHERE email = ? AND parola = ?"
        self.cursor.execute(query, (email, password))
        return self.cursor.fetchone()

    def get_role(self, email):
        query = "SELECT rol FROM Utilizatori WHERE email = ?"
        self.cursor.execute(query, (email,))
        return self.cursor.fetchone()

    def get_utilizatori_all(self):
        query = "SELECT id, email, rol FROM Utilizatori"
        self.cursor.execute(query)
        return self.cursor.fetchall()

    def get_utilizatori_email(self, email):
        query = "SELECT id, email, rol FROM Utilizatori WHERE email = ?"
        self.cursor.execute(query, (email,))
        return self.cursor.fetchone()

    def create_utilizator(self, utilizator: Utilizatori):
        query = "INSERT INTO Utilizatori (email, parola, rol) VALUES (?, ?, ?)"
        self.cursor.execute(query, (utilizator.email, utilizator.parola, utilizator.rol))
        self.conn.commit()
        query = "SELECT id, email, rol FROM Utilizatori WHERE email = ?"
        self.cursor.execute(query, (utilizator.email,))
        return self.cursor.fetchone()

    def exista_email(self, email):
        query = "SELECT id FROM Utilizatori WHERE email = ?"
        self.cursor.execute(query, (email,))
        return self.cursor.fetchone()

    def update_all(self, email, utilizator: Utilizatori):
        query = "UPDATE Utilizatori SET id = ?, email = ?, parola = ?, rol = ? WHERE email = ?"
        self.cursor.execute(query, (utilizator.id, utilizator.email, utilizator.parola, utilizator.rol, email))
        self.conn.commit()
        query = "SELECT id, email, rol FROM Utilizatori WHERE email = ?"
        self.cursor.execute(query, (utilizator.email,))
        return self.cursor.fetchone()

    def update(self, email, utilizator: Utilizatori):
        updates = {}
        if utilizator.id is not None:
            updates["id"] = utilizator.id
        if utilizator.email is not None:
            updates["email"] = utilizator.email
        if utilizator.parola is not None:
            updates["parola"] = utilizator.parola
        if utilizator.rol is not None:
            updates["rol"] = utilizator.rol

        set_clause = ", ".join(f"{col} = ?" for col in updates.keys())
        query = f"UPDATE Utilizatori SET {set_clause} WHERE email = ?"

        self.cursor.execute(query, list(updates.values()) + [email])
        self.connection.commit()

        query = "SELECT id, email, rol FROM Utilizatori WHERE email = ?"
        if utilizator.email is None:
            self.cursor.execute(query, (email,))
        else:
            self.cursor.execute(query, (utilizator.email,))
        return self.cursor.fetchone()

    def delete_email(self, email):
        query = "DELETE FROM Utilizatori WHERE email = ?"
        self.cursor.execute(query, (email,))
        self.conn.commit()
        query = "SELECT id FROM Utilizatori WHERE email = ?"
        self.cursor.execute(query, (email,))
        return self.cursor.fetchone()

