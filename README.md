# GLOBAL DESARROLLO DE SOFTWARE

> **Mutant Detector API**
> Examen Mercadolibre - Backend Developer

API REST desarrollada en Java con Spring Boot para detectar si un humano es mutante basándose en su secuencia de ADN. El proyecto sigue una arquitectura en capas, cuenta con optimizaciones de rendimiento, persistencia de datos y alta cobertura de pruebas.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)
[![Coverage](https://img.shields.io/badge/Coverage->80%25-success.svg)]()

---

## 🚀 Deploy / Nube

La API se encuentra desplegada y accesible públicamente en **Render**.

👉 **URL Base:** [PON_AQUI_TU_URL_DE_RENDER]  
*(Ejemplo: https://mutantes-api-joaquin.onrender.com)*

- **Swagger UI (Documentación):** [PON_AQUI_TU_URL_DE_RENDER]/swagger-ui.html
- **Health Check:** [PON_AQUI_TU_URL_DE_RENDER]/actuator/health

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
    * **Early Termination:** El algoritmo se detiene inmediatamente al encontrar más de una secuencia.
    * **Validación O(1):** Verificación eficiente de caracteres válidos (A, T, C, G) utilizando Sets.
    * Validaciones robustas para matrices NxN.

2.  **Arquitectura y Tecnologías:**
    * **Spring Boot 3.3.5**: Framework principal.
    * **H2 Database**: Base de datos en memoria para persistencia rápida.
    * **JPA/Hibernate**: Mapeo objeto-relacional.
    * **Gradle**: Gestor de dependencias y construcción.
    * **Lombok**: Para reducción de código repetitivo (boilerplate).
    * **Swagger/OpenAPI**: Documentación interactiva automática.
    * **Docker**: Contenerización para despliegue universal.

3.  **Seguridad y Eficiencia:**
    * Generación de **Hash SHA-256** para cada ADN analizado, evitando duplicados en la base de datos y mejorando la velocidad de respuesta para ADNs ya conocidos (Caché en BD).

---

## 🛠️ Instalación y Ejecución

### Prerrequisitos
* Java JDK 17 instalado.
* Git instalado.
* Docker (Opcional, si deseas ejecutar con contenedores).

### Opción 1: Ejecución Local con Gradle

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/JoaquinYap/Mutantes.git](https://github.com/JoaquinYap/Mutantes.git)
    cd Mutantes
    ```

2.  **Compilar y Ejecutar:**
    * En Windows:
        ```powershell
        ./gradlew bootRun
        ```
    * En Linux/Mac:
        ```bash
        ./gradlew bootRun
        ```

3.  **Verificar:** La app correrá en `http://localhost:8080`.

### Opción 2: Ejecución con Docker 🐳

Si prefieres no instalar Java/Gradle localmente, puedes usar Docker.

1.  **Construir la imagen:**
    ```bash
    docker build -t mutantes-api .
    ```

2.  **Ejecutar el contenedor:**
    ```bash
    docker run -p 8080:8080 mutantes-api
    ```

La aplicación estará disponible en `http://localhost:8080`.

---

## 📚 Documentación de la API

La API cuenta con documentación interactiva generada con **Swagger UI**.

👉 **Local:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
👉 **Nube:** [PON_AQUI_TU_URL_DE_RENDER]/swagger-ui.html

### Endpoints Principales

#### 1. Detectar Mutante
* **URL:** `POST /mutant`
* **Descripción:** Envía una secuencia de ADN para ser analizada.
* **Body (JSON):**
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
    ```
* **Respuestas:**
    * `200 OK`: Es un **Mutante**.
    * `403 Forbidden`: Es un **Humano**.
    * `400 Bad Request`: Datos inválidos (Matriz no cuadrada, caracteres erróneos, etc.).

#### 2. Estadísticas
* **URL:** `GET /stats`
* **Descripción:** Devuelve estadísticas de las verificaciones.
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

Puedes inspeccionar los registros guardados en la base de datos en memoria (solo en ejecución local).

* **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* **JDBC URL:** `jdbc:h2:mem:testdb`
* **User Name:** `sa`
* **Password:** *(dejar vacío)*

> **Nota:** La tabla principal es `dna_records`. El campo `dna_hash` almacena el identificador único SHA-256 (índice único).

---

## 🧪 Testing y Cobertura

El proyecto incluye una suite completa de tests unitarios y de integración. Se ha configurado una regla de calidad estricta que requiere **mínimo 80% de cobertura** para aprobar el build.

### Ejecutar Tests
```bash
./gradlew test
