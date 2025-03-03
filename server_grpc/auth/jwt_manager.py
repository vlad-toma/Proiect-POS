import jwt
import datetime
import uuid

SECRET_KEY = "macaroane"


def generate_token(email, role):
    payload = {
        "iss": "http://grpc-server-url",
        "sub": email,
        "role": role,
        "jti": str(uuid.uuid4()),
        "exp": datetime.datetime.now() + datetime.timedelta(hours=1)
    }
    return jwt.encode(payload, SECRET_KEY, algorithm="HS256")
