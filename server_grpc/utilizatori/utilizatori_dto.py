from pydantic import BaseModel


class UtilizatoriDTO(BaseModel):
    id: int
    email: str
    rol: str
