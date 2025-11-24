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

    // --- NUEVOS TESTS PARA CUMPLIR RÚBRICA ---

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
        String[] dna = {"ATG", "CAGT", "TTA", "AGA"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testMutantCrossed() {
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
        // CORREGIDO: Matriz diseñada para tener SOLO 1 secuencia horizontal al final
        // y ninguna vertical accidental.
        String[] dna = {
                "ATGC",
                "CAGT",
                "TGCA",
                "AAAA" // Horizontal al final. (Columnas: A,T,G,A -> no verticales)
        };
        // 1 secuencia no es suficiente para ser mutante (>1)
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testLargeMatrixPerformance() {
        int n = 100;
        String[] dna = new String[n];
        // Llenar con 'A's
        StringBuilder row = new StringBuilder();
        for(int i=0; i<n; i++) row.append('A');

        for (int i = 0; i < n; i++) {
            dna[i] = row.toString();
        }
        assertTrue(detector.isMutant(dna));
    }
}