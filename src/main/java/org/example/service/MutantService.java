package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector detector;

    @Cacheable(value = "dnaAnalysis", key = "#dna.toString()")
    public boolean analyze(String[] dna) {
        String dnaHash = calculateHash(dna);

        Optional<DnaRecord> existing = repository.findByDnaHash(dnaHash);
        if (existing.isPresent()) {
            log.info("ADN encontrado en base de datos. Hash: {}", dnaHash);
            return existing.get().isMutant();
        }

        boolean isMutant = detector.isMutant(dna);

        CompletableFuture.runAsync(() -> {
            try {
                DnaRecord record = DnaRecord.builder()
                        .dnaHash(dnaHash)
                        .sequence(String.join(",", dna))
                        .isMutant(isMutant)
                        .build();
                repository.save(record);
                log.debug("Registro guardado asíncronamente. Hash: {}", dnaHash);
            } catch (Exception e) {
                log.error("Error guardando registro asíncrono", e);
            }
        });

        return isMutant;
    }

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