from fastapi import FastAPI, status, HTTPException
from utilizatori.utilizatori_service import UtilizatoriService
from auth import jwt_manager
from utilizatori.utilizatori import Utilizatori
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

utilizatori_service = UtilizatoriService()

origins = [
    "http://localhost:5173",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
class LoginRequest(BaseModel):
    email: str
    parola: str


class LoginResponse(BaseModel):
    token: str


@app.post("/login", status_code=status.HTTP_200_OK)
async def read_root(arg: LoginRequest):
    try:
        email, role = utilizatori_service.login(arg.email, arg.parola)

        return LoginResponse(token=jwt_manager.generate_token(email, role))

    except Exception as e:
        if str(e) == "401":
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Email sau parola gresita")
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail=str(e)
        )


@app.get("/utilizatori", status_code=status.HTTP_200_OK)
async def get_utilizatori_all():
    try:
        useri = utilizatori_service.get_utilizatori_all()

        return useri
    except Exception as e:
        if str(e) == "204":
            raise HTTPException(
                status_code=status.HTTP_204_NO_CONTENT,
                detail="No content"
            )
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=str(e)
        )


@app.get("/utilizatori/{email}", status_code=status.HTTP_200_OK)
async def get_utilizatori_email(email):
    try:
        user = utilizatori_service.get_utilizatori_email(email)

        return user
    except Exception as e:
        if str(e) == "404":
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Not found")
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=str(e))


@app.post("/utilizatori", status_code=201)
async def post_utilizatori(utilizator: Utilizatori):
    try:
        user_creat = utilizatori_service.create_utilizator(utilizator)

        return user_creat
    except Exception as e:
        if str(e) == "409":
            raise HTTPException(status_code=409, detail="Conflict")
        if str(e) == "415":
            raise HTTPException(status_code=status.HTTP_415_UNSUPPORTED_MEDIA_TYPE, detail="Unsupported media type")
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=str(e))


@app.put("/utilizatori/{email}", status_code=status.HTTP_201_CREATED)
async def put_utilizatori(email: str, utilizator: Utilizatori):
    try:
        user = utilizatori_service.get_utilizatori_email(email)
        new = True
        if user is not None:
            new = False

        user = utilizatori_service.put_utilizator(email, utilizator)

        return user

    except Exception as e:
        if str(e) == "409":
            raise HTTPException(status_code=409, detail="Conflict")
        if str(e) == "415":
            raise HTTPException(status_code=status.HTTP_415_UNSUPPORTED_MEDIA_TYPE, detail="Unsupported media type")
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=str(e))


@app.patch("/utilizatori/{email}", status_code=status.HTTP_204_NO_CONTENT)
async def patch_utilizatori(email: str, utilizator: Utilizatori):
    try:
        user = utilizatori_service.patch_utilizator(email, utilizator)

        return user
    except Exception as e:
        if str(e) == "404":
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Not found")
        if str(e) == "409":
            raise HTTPException(status_code=status.HTTP_409_CONFLICT, detail="Conflict")
        if str(e) == "415":
            raise HTTPException(status_code=status.HTTP_415_UNSUPPORTED_MEDIA_TYPE, detail="Unsupported media type")
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=str(e))


@app.delete("/utilizatori/{email}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_utilizatori(email):
    try:
        utilizatori_service.delete_utilizatori(email)
    except Exception as e:
        if str(e) == "404":
            raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Not found")
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=str(e))


async def serve_fastapi():
    import uvicorn
    config = uvicorn.Config(app, host="0.0.0.0", port=8082, log_level="info")
    server = uvicorn.Server(config)
    print('server fastapi running')
    await server.serve()
