package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio para la detección de mutantes.
 * Gestiona el análisis de secuencias de ADN, la verificación en caché (Base de Datos)
 * y el almacenamiento de nuevos resultados.
 */
@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector detector;

    /**
     * Analiza una secuencia de ADN para determinar si corresponde a un mutante.
     * <p>
     * Este método primero verifica si el ADN ya ha sido analizado previamente consultando
     * la base de datos mediante su Hash. Si existe, retorna el resultado almacenado.
     * Si no, ejecuta el algoritmo de detección, guarda el resultado y lo retorna.
     * </p>
     *
     * @param dna Array de Strings que representa la secuencia de ADN (Matriz NxN).
     * @return {@code true} si el ADN es mutante, {@code false} si es humano.
     */
    public boolean analyze(String[] dna) {
        // 1. Calcular Hash SHA-256 para identificación única
        String hash = calculateHash(dna);

        // 2. Verificar Cache en BD (Evitar re-procesamiento)
        Optional<DnaRecord> existing = repository.findByHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        // 3. Analizar ADN (Ejecutar algoritmo)
        boolean isMutant = detector.isMutant(dna);

        // 4. Guardar resultado en la base de datos
        DnaRecord record = DnaRecord.builder()
                .hash(hash)
                .sequence(String.join(",", dna))
                .isMutant(isMutant)
                .build();

        repository.save(record);

        return isMutant;
    }

    /**
     * Calcula un Hash SHA-256 único para la secuencia de ADN proporcionada.
     * Esto permite realizar búsquedas rápidas en la base de datos y garantizar la integridad.
     *
     * @param dna Array de Strings con la secuencia de ADN.
     * @return String con el hash hexadecimal calculado.
     * @throws RuntimeException si el algoritmo SHA-256 no está disponible.
     */
    private String calculateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder sb = new StringBuilder();
            for (String s : dna) {
                sb.append(s);
            }
            byte[] encodedHash = digest.digest(sb.toString().getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular hash SHA-256", e);
        }
    }
}