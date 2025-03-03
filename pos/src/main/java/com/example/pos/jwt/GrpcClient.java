package com.example.pos.jwt;

import grpc.AuthServiceGrpc;
import grpc.Sql;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    private final ManagedChannel channel;
    private final AuthServiceGrpc.AuthServiceBlockingStub stub;

    public GrpcClient() {
        // Configurează conexiunea cu serverul Python
        this.channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        this.stub = AuthServiceGrpc.newBlockingStub(channel);
    }

    public Sql.ValidateTokenResponse validateToken(String token) {
        Sql.ValidateTokenRequest request = Sql.ValidateTokenRequest.newBuilder()
                .setToken(token)
                .build();
        return stub.validateToken(request);
    }

    public void shutdown() {
        channel.shutdown();
    }
}
