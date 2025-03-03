from pydantic import BaseModel


class Utilizatori(BaseModel):
    id: int
    email: str
    parola: str
    rol: str
