package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector detector;

    public boolean analyze(String[] dna) {
        // 1. Calcular Hash SHA-256
        String hash = calculateHash(dna);

        // 2. Verificar Cache en BD
        Optional<DnaRecord> existing = repository.findByHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        // 3. Analizar ADN
        boolean isMutant = detector.isMutant(dna);

        // 4. Guardar resultado
        DnaRecord record = DnaRecord.builder()
                .hash(hash)
                .sequence(String.join(",", dna))
                .isMutant(isMutant)
                .build();

        repository.save(record);

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