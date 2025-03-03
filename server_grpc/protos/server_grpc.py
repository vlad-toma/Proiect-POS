from concurrent import futures
import grpc
import jwt
import datetime
import protos.idm_pb2 as idm_pb2
import protos.idm_pb2_grpc as idm_pb2_grpc


class AuthService(idm_pb2_grpc.AuthServiceServicer):
    def __init__(self, secret_key):
        self.secret_key = secret_key
        self.blacklist = set()

    async def ValidateToken(self, request, context):
        token = request.token
        if token in self.blacklist:
            return idm_pb2.ValidateTokenResponse(success=False, message="Token is blacklisted")

        try:
            payload = jwt.decode(token, self.secret_key, algorithms=["HS256"])


            if payload.get("exp") < datetime.datetime.now().timestamp():
                return idm_pb2.ValidateTokenResponse(success=False, message="Token has expired")

            return idm_pb2.ValidateTokenResponse(
                success=True,
                message="Token is valid",
                role=payload.get("role"),
                userId=payload.get("sub")
            )
        except jwt.ExpiredSignatureError:
            self.blacklist.add(token)
            return idm_pb2.ValidateTokenResponse(success=False, message="Token has expired")
        except jwt.InvalidTokenError:
            self.blacklist.add(token)
            return idm_pb2.ValidateTokenResponse(success=False, message="Token is invalid")


async def serve_grpc():
    server = grpc.aio.server(futures.ThreadPoolExecutor(max_workers=10))  # Server asincron gRPC
    idm_pb2_grpc.add_AuthServiceServicer_to_server(AuthService("macaroane"), server)
    server.add_insecure_port('[::]:50051')
    await server.start()
    print("gRPC server running on port 50051")
    await server.wait_for_termination()

