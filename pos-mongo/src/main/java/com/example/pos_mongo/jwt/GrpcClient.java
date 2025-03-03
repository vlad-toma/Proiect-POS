package com.example.pos_mongo.jwt;

import grpc.AuthServiceGrpc;
import grpc.Mongo;
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

    public Mongo.ValidateTokenResponse validateToken(String token) {
        Mongo.ValidateTokenRequest request = Mongo.ValidateTokenRequest.newBuilder()
                .setToken(token)
                .build();
        return stub.validateToken(request);
    }

    public void shutdown() {
        channel.shutdown();
    }
}
