package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MutantService {

    @Autowired
    private DnaRecordRepository repository;

    @Autowired
    private MutantDetector detector;

    public boolean analyze(String[] dna) {
        // 1. Generar Hash (Simple concatenación para ejemplo, ideal usar SHA256)
        String hash = String.join("", dna);

        // 2. Verificar Cache
        Optional<DnaRecord> existing = repository.findByHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        // 3. Calcular
        boolean isMutant = detector.isMutant(dna);

        // 4. Guardar
        DnaRecord record = DnaRecord.builder()
                .hash(hash)
                .sequence(String.join(",", dna))
                .isMutant(isMutant)
                .build();
        repository.save(record);

        return isMutant;
    }
}