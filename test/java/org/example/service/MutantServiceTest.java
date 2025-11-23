package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        // Simulamos que NO existe en base de datos
        when(repository.findByHash(anyString())).thenReturn(Optional.empty());
        // Simulamos que el detector dice que ES mutante
        when(detector.isMutant(dna)).thenReturn(true);

        boolean result = service.analyze(dna);

        assertTrue(result);
        // Verificamos que se guardó en la base de datos
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeCachedDna() {
        String[] dna = {"ATGCGA"};

        // CORRECCIÓN: Usamos el Builder en lugar de 'new DnaRecord(...)'
        // Esto evita errores si agregas o quitas campos en la entidad
        DnaRecord existing = DnaRecord.builder()
                .id(1L)
                .hash("hash_simulado")
                .isMutant(true)
                .build();

        // Simulamos que YA existe en base de datos
        when(repository.findByHash(anyString())).thenReturn(Optional.of(existing));

        boolean result = service.analyze(dna);

        assertTrue(result);
        // Verificamos que NUNCA se llame a save (porque ya estaba en caché)
        verify(repository, never()).save(any(DnaRecord.class));
    }
}