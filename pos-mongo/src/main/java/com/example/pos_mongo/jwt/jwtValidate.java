package com.example.pos_mongo.jwt;

import grpc.Mongo;

public class jwtValidate {
    public static Mongo.ValidateTokenResponse  validateToken(String authorizationHeader) throws jwtException{
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new jwtException("Header-ul de autorizare lipseste sau este invalid");
        }
        String token = authorizationHeader.substring(7);
        try {
            // Exemplu de inițializare
            GrpcClient grpcClient = new GrpcClient();
            Mongo.ValidateTokenResponse validationResponse = grpcClient.validateToken(token);
            grpcClient.shutdown();

            if (!validationResponse.getSuccess()) {
                throw new jwtException(validationResponse.getMessage());
            }

            return validationResponse;
        }
        catch (Exception e) {
            throw new jwtException(e.getMessage());
        }
    }

    public static class jwtException extends Exception {
        public jwtException(String message) {
            super(message);
        }
    }
}
