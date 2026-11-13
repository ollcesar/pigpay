package com.pigpay.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS: o navegador só deixa a tela (porta 5173) chamar a API (porta 8080)
 * se o backend avisar que aceita esse endereço. Aqui liberamos só endereços
 * locais: o próprio PC e a rede de casa (para testar no celular).
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(
                        "http://localhost:*",
                        "http://127.0.0.1:*",
                        "http://192.168.*",
                        "http://10.*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
