package com.example.pos;

import com.example.pos.jwt.GrpcClient;
import com.example.pos.jwt.jwtValidate;
import grpc.Sql;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.pos")
public class PosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosApplication.class, args);
	}

}
