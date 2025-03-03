package com.example.pos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS pentru toate endpoint-urile
                .allowedOrigins("http://localhost:5173") // Permite originile de la care sunt permise cererile (ex: aplicația React)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Permite doar metodele specificate
                .allowedHeaders("*") // Permite toate headerele
                .allowCredentials(true); // Permite utilizarea de credențiale (cookie-uri, sesiuni)
    }
}