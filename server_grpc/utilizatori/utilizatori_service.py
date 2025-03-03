from utilizatori.utilizatori_repository import DBManager
from utilizatori.utilizatori_dto import UtilizatoriDTO
from utilizatori.utilizatori import Utilizatori

class UtilizatoriService():
    def __init__(self):
        self.dbManager = DBManager()
    def login(self, email: str, parola: str):
        row = self.dbManager.validate_user(email, parola)
        if row is None:
            raise Exception('401')
        return row['email'], row['rol']

    def get_utilizatori_all(self):
        rows = self.dbManager.get_utilizatori_all()
        if rows is None:
            raise Exception("204")
        return [UtilizatoriDTO(id=row['id'], email=row['email'], rol=row['rol']) for row in rows]

    def get_utilizatori_email(self, email):
        row = self.dbManager.get_utilizatori_email(email)
        if row is None:
            raise Exception("404")
        try:
            return UtilizatoriDTO(id=row['id'], email=row['email'], rol=row['rol'])
        except KeyError as e:
            raise Exception(f"Unexpected data structure: {e}")

    def create_utilizator(self, utilizator: Utilizatori):
        row = self.dbManager.exista_email(utilizator.email)
        if row is not None:
            raise Exception("409")

        em = utilizator.email.split('@')
        if len(em) != 2:
            raise Exception("415")
        ail = em[1].split('.')
        if len(ail) not in range(2, 4):
            raise Exception("415")

        if utilizator.rol not in ['admin', 'profesor', 'student']:
            raise Exception("415")

        row = self.dbManager.create_utilizator(utilizator)
        return UtilizatoriDTO(id=row['id'], email=row['email'], rol=row['rol'])

    def put_utilizator(self, email, utilizator: Utilizatori):
        row = self.dbManager.exista_email(utilizator.email)
        if row is None:
            try:
                user = self.create_utilizator(utilizator)
                return user
            except Exception as e:
                raise e

        em = utilizator.email.split('@')
        if len(em) != 2:
            raise Exception("415")
        ail = em[1].split('.')
        if len(ail) not in range(2, 4):
            raise Exception("415")

        if utilizator.rol not in ['admin', 'profesor', 'student']:
            raise Exception("415")

        row = self.dbManager.update_all(email, utilizator)
        return UtilizatoriDTO(id=row['id'], email=row['email'], rol=row['rol'])

    def patch_utilizator(self, email, utilizator: Utilizatori):
        row = self.dbManager.exista_email(utilizator.email)
        if row is None:
            raise Exception("404")

        if utilizator.email is not None:
            em = utilizator.email.split('@')
            if len(em) != 2:
                raise Exception("415")
            ail = em[1].split('.')
            if len(ail) not in range(2, 4):
                raise Exception("415")
        if utilizator.rol is not None:
            if utilizator.rol not in ['admin', 'profesor', 'student']:
                raise Exception("415")

        row = self.dbManager.update(email, utilizator)
        return UtilizatoriDTO(id=row['id'], email=row['email'], rol=row['rol'])

    def delete_utilizatori(self, email):
        row = self.dbManager.exista_email(email)
        if row is None:
            raise Exception("404")

        row = self.dbManager.delete_email(email)
