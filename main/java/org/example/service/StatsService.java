package org.example.service;

import lombok.RequiredArgsConstructor; // Importante
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Inyección de dependencias automática
public class StatsService {

    private final DnaRecordRepository repository; // 'final' es clave aquí

    public StatsResponse getStats() {
        long mutants = repository.countByIsMutant(true);
        long humans = repository.countByIsMutant(false);
        double ratio = humans == 0 ? 0 : (double) mutants / humans;

        return StatsResponse.builder()
                .countMutantDna(mutants)
                .countHumanDna(humans)
                .ratio(ratio)
                .build();
    }
}