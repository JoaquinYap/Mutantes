# GLOBAL DESARROLLO DE SOFTWARE

> Mutan Detector API
> Examen Mercadolibre - Backend Developer

API REST desarrollada en Java con Spring Boot para detectar si un humano es mutante basándose en su secuencia de ADN. El proyecto sigue una arquitectura en capas, cuenta con optimizaciones de rendimiento, persistencia de datos y alta cobertura de pruebas.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Coverage](https://img.shields.io/badge/Coverage->80%25-success.svg)]()

---

## 👤 Datos del Alumno

* **Nombre:** Joaquin Yapura
* **Legajo:** 51154
* **Comisión:** 3K10
* **Año:** 2025

---

## 🚀 Características Principales

1.  **Algoritmo Optimizado:**
    * Detección de secuencias horizontales, verticales y diagonales.
    * **Early Termination:** El algoritmo se detiene inmediatamente al encontrar más de una secuencia, mejorando drásticamente el rendimiento.
    * Validaciones robustas para matrices NxN y caracteres válidos (A, T, C, G).

2.  **Arquitectura y Tecnologías:**
    * **Spring Boot 3.3.5**: Framework principal.
    * **H2 Database**: Base de datos en memoria para persistencia rápida.
    * **JPA/Hibernate**: Mapeo objeto-relacional.
    * **Gradle**: Gestor de dependencias y construcción.
    * **Lombok**: Para reducción de código repetitivo (boilerplate).
    * **Swagger/OpenAPI**: Documentación interactiva automática.

3.  **Seguridad y Eficiencia:**
    * Generación de **Hash SHA-256** para cada ADN analizado, evitando duplicados en la base de datos y mejorando la velocidad de respuesta para ADNs ya conocidos.

---

## 🛠️ Instalación y Ejecución

### Prerrequisitos
* Java JDK 17 instalado.
* Git instalado.

### Paso a Paso

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/JoaquinYap/Mutantes.git](https://github.com/JoaquinYap/Mutantes.git)
    cd nombre-repo
    ```

2.  **Compilar y Ejecutar (Usando Gradle Wrapper):**
    * En Windows:
        ```powershell
        ./gradlew bootRun
        ```
    * En Linux/Mac:
        ```bash
        ./gradlew bootRun
        ```

3.  **Verificar funcionamiento:**
    Una vez iniciado, la aplicación correrá en el puerto `8080`.

---

## 📚 Documentación de la API

La API cuenta con documentación interactiva generada con **Swagger UI**. Puedes probar los endpoints directamente desde el navegador.

👉 **Acceder a Swagger:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Endpoints Principales

#### 1. Detectar Mutante
* **URL:** `POST /mutant`
* **Descripción:** Envía una secuencia de ADN para ser analizada.
* **Ejemplo MUTANTE:**
    ```json
    
    {
      "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
      ]
    }
    
 * **Ejemplo MUTANTE HORIZONTAL:**
    ```json

    {
    "dna": [
        "AAAAAA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
    }
    
 * **Ejemplo MUTANTE VERTICAL:**
   ```json
    { 
   "dna": [
       "ATGCGA",
       "AAGTGC",
       "ATATGT",
       "AGAAGG",
       "ACCCCT",
       "ATCACT"
   ]
   }
   
* **Ejemplo HUMANO:**
  ```json
    {
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATTT",
        "AGACGG",
        "CCTCTA",
        "TCACTG"
    ]
    }

    ```
* **Respuestas:**
    * `200 OK`: Es un **Mutante**.
    * `403 Forbidden`: Es un **Humano**.
    * `400 Bad Request`: El ADN es inválido (formato incorrecto, caracteres no válidos, matriz no cuadrada).

#### 2. Estadísticas
* **URL:** `GET /stats`
* **Descripción:** Devuelve las estadísticas de las verificaciones realizadas.
* **Respuesta (JSON):**
    ```json
    {
        "count_mutant_dna": 40,
        "count_human_dna": 100,
        "ratio": 0.4
    }
    ```

---

## 💾 Base de Datos (H2 Console)

Puedes inspeccionar los registros guardados en la base de datos en memoria.

* **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* **JDBC URL:** `jdbc:h2:mem:testdb`
* **User Name:** `sa`
* **Password:** (dejar vacío)

> **Nota:** La tabla principal es `dna_records`. Verás que el campo `hash` almacena el identificador único SHA-256 y `sequence` guarda el ADN completo.

---

## 🧪 Testing y Cobertura

El proyecto incluye una suite completa de tests unitarios y de integración utilizando **JUnit 5** y **Mockito**. Se ha verificado una cobertura de código superior al 80% utilizando **JaCoCo**.

### Ejecutar Tests
```bash
./gradlew test
