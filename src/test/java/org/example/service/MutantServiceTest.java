package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @Mock
    private MutantDetector detector;

    @InjectMocks
    private MutantService service;

    @Test
    void testAnalyzeNewDna() {
        String[] dna = {"ATGCGA"};
        when(repository.findByHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        boolean result = service.analyze(dna);

        assertTrue(result);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeCachedDna() {
        String[] dna = {"ATGCGA"};
        DnaRecord existing = new DnaRecord(1L, "hash", true);
        when(repository.findByHash(anyString())).thenReturn(Optional.of(existing));

        boolean result = service.analyze(dna);

        assertTrue(result);
        verify(repository, never()).save(any(DnaRecord.class));
    }
}