# 🧪 Estrategia de Testing - Mutant Detector

Este proyecto cuenta con una cobertura de código superior al **80%**, verificada automáticamente con JaCoCo durante el build. La estrategia combina tests unitarios de alto rendimiento con tests de integración robustos.

## 1. Tests Unitarios (`MutantDetectorTest`)
Se probó exhaustivamente la lógica del algoritmo de detección de mutantes de forma aislada.
- **Casos Positivos:** Mutantes horizontales, verticales, diagonales y mixtos.
- **Casos Negativos:** Humanos sin secuencias o con solo una.
- **Casos Borde (Robustez):** - Caracteres inválidos.
  - Filas nulas.
- **Performance & Optimización:** - Se verifica la **Terminación Temprana (Early Termination)** mediante pruebas con matrices gigantes (1000x1000) asegurando tiempos de respuesta < 800ms.

## 2. Tests de Servicios (`MutantServiceTest`)
Se utilizaron **Mocks** (Mockito) para aislar la lógica de negocio.
- Verificación del cálculo de Hash SHA-256.
- Verificación de la lógica de **Caché** (si existe en DB, recupera el resultado sin re-procesar).
- Validación del comportamiento **Asíncrono** (`@Async`) para el guardado en base de datos.

## 3. Tests de Controladores (`MutantControllerTest`)
Pruebas de la capa web utilizando **MockMvc** para simular peticiones HTTP.
- Validación de códigos de estado HTTP (200, 403, 400).
- Validación de DTOs y constraints (`@ValidDnaSequence`).
- Verificación de respuestas JSON correctas ante inputs inválidos.

## 4. Tests de Integración End-to-End (`MutantControllerIntegrationTest`) 🌟
Se implementaron tests de integración real (`@SpringBootTest` con `TestRestTemplate`) que levantan el contexto completo de la aplicación y la base de datos H2.
- **Flujo Completo:** Petición HTTP -> Controlador -> Servicio -> Repositorio -> DB.
- **Manejo de Concurrencia:** Los tests gestionan la naturaleza asíncrona del guardado, esperando inteligentemente a que los hilos secundarios terminen de persistir los datos antes de realizar las aserciones (`waitForDbCount`).

## 5. Tests de Estadísticas (`StatsServiceTest`)
Verificación matemática de los cálculos de ratio.
- Casos de división por cero (0 humanos).
- Ratios con decimales periódicos y exactos.

---

## 📊 Reporte de Cobertura
Para generar el reporte visual de JaCoCo:
```bash
./gradlew test jacocoTestReport
# El reporte se generará en: build/reports/jacoco/test/html/index.html
