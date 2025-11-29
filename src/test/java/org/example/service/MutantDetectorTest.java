package org.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

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

    @Test
    void testHumanSimple() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testHumanWithOnlyOneHorizontalSequence() {
        String[] dna = {
                "AAAAGA",
                "CAGTCC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

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
        String[] dna = {
                "ATGC",
                "CAGT",
                "TGCA",
                "AAAA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testHugeMatrix_PerformanceCheck() {
        int n = 1000;
        String[] dna = new String[n];

        String row = "T".repeat(n);
        for(int i=0; i<n; i++) dna[i] = row;

        long startTime = System.currentTimeMillis();
        boolean isMutant = detector.isMutant(dna);
        long endTime = System.currentTimeMillis();

        assertTrue(isMutant, "Debería ser mutante");

        long duration = endTime - startTime;
        System.out.println("Performance Test 1000x1000: " + duration + "ms");

        assertTrue(duration < 800,
                "El algoritmo tardó " + duration + "ms, debería ser menos de 800ms para N=1000");
    }
    @Test
    void testInvalidCharactersInsideDetector() {
        String[] dna = {"ATGCGA", "CAGTXC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testNullRowInsideArray() {
        String[] dna = {"ATGCGA", null, "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testComplexMixedMutant() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertTrue(detector.isMutant(dna));
    }
}