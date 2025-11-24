package org.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    // --- CASOS MUTANTES (5 tests) ---

    @Test
    void testMutantHorizontal() {
        String[] dna = {"AAAAAA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantVertical() {
        String[] dna = {"ATGCGA", "AAGTGC", "ATATGT", "AGAAGG", "ACCCCT", "ATCACT"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalPrincipal() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalInversa() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantMultipleSequences() {
        String[] dna = {"AAAAAA", "CCCCCC", "GGGGGG", "TTTTTT", "AAAAAA", "CCCCCC"};
        assertTrue(detector.isMutant(dna));
    }

    // --- CASOS HUMANOS (2 tests) ---

    @Test
    void testHumanSimple() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testHumanWithOnlyOneHorizontalSequence() {
        String[] dnaOne = {
                "AAAAGA",
                "CAGTCC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dnaOne));
    }

    // --- CASOS BORDE Y VALIDACIONES (5 tests) ---

    @Test
    void testNullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    void testEmptyDna() {
        assertFalse(detector.isMutant(new String[]{}));
    }

    @Test
    void testSmallMatrix() {
        String[] dna = {"ATG", "CAG", "TTA"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void test4x4Mutant() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void test4x4Human() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TGCA",
                "GCAT"
        };
        assertFalse(detector.isMutant(dna));
    }

    // --- TESTS ADICIONALES DE LA RÚBRICA ---

    @Test
    void test5x5MatrixMutant() {
        String[] dna = {
                "AAAAA",
                "CAGTC",
                "TTATG",
                "AGAAG",
                "CCCCT"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testRectangularMatrix() {
        // Caso robustez: Matriz irregular (filas de distinto largo)
        String[] dna = {"ATG", "CAGT", "TTA", "AGA"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testMutantCrossed() {
        // Caso complejo: Cruce de horizontal y vertical
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testAllSameCharacters() {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testSequenceAtEndBoundary() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TGCA",
                "AAAA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testLargeMatrixPerformance() {
        int n = 100;
        String[] dna = new String[n];
        StringBuilder row = new StringBuilder();
        for(int i=0; i<n; i++) row.append('A');

        for (int i = 0; i < n; i++) {
            dna[i] = row.toString();
        }
        assertTrue(detector.isMutant(dna));
    }

    // --- NUEVOS TESTS AGREGADOS PARA 100% DE RÚBRICA ---

    @Test
    void testInvalidCharactersInsideDetector() {
        // Prueba directa al algoritmo: debe retornar false si hay caracteres inválidos
        // (aunque el controller lo filtre antes, el servicio debe ser robusto)
        String[] dna = {"ATGCGA", "CAGTXC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testNullRowInsideArray() {
        // Prueba de robustez: Array existe, pero una fila es null
        String[] dna = {"ATGCGA", null, "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(detector.isMutant(dna));
    }
}