package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        int n = dna.length;
        char[][] matrix = new char[n][n];

        // Convertir a char[][]
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int sequencesFound = 0;

        // Búsqueda optimizada
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sequencesFound > 1) return true; // Early termination

                // Horizontal
                if (j + SEQUENCE_LENGTH <= n && check(matrix[i][j], matrix[i][j+1], matrix[i][j+2], matrix[i][j+3])) {
                    sequencesFound++;
                    continue;
                }
                // Vertical
                if (i + SEQUENCE_LENGTH <= n && check(matrix[i][j], matrix[i+1][j], matrix[i+2][j], matrix[i+3][j])) {
                    sequencesFound++;
                    continue;
                }
                // Diagonal
                if (i + SEQUENCE_LENGTH <= n && j + SEQUENCE_LENGTH <= n && check(matrix[i][j], matrix[i+1][j+1], matrix[i+2][j+2], matrix[i+3][j+3])) {
                    sequencesFound++;
                    continue;
                }
                // Diagonal Inversa
                if (i + SEQUENCE_LENGTH <= n && j - SEQUENCE_LENGTH + 1 >= 0 && check(matrix[i][j], matrix[i+1][j-1], matrix[i+2][j-2], matrix[i+3][j-3])) {
                    sequencesFound++;
                    continue;
                }
            }
        }
        return sequencesFound > 1;
    }

    private boolean check(char c1, char c2, char c3, char c4) {
        return c1 == c2 && c2 == c3 && c3 == c4;
    }
}