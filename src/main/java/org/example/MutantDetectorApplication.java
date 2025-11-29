package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class MutantDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutantDetectorApplication.class, args);

        System.out.println("----------------------------------------------------------");
        System.out.println(" 🚀 Servidor iniciado correctamente");
        System.out.println(" 📄 Swagger: http://localhost:8080/swagger-ui.html");
        System.out.println(" 💾 H2 Console: http://localhost:8080/h2-console");
        System.out.println("----------------------------------------------------------");
    }
}