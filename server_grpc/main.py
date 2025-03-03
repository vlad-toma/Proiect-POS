import asyncio
from utilizatori.utilizatori_controller import serve_fastapi
from protos.server_grpc import serve_grpc

async def main():
    await asyncio.gather(
        serve_grpc(),
        serve_fastapi(),
    )


if __name__ == "__main__":
    asyncio.run(main())
