package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        // 1. VALIDACIÓN ANTI-NULL (Esto arregla el NullPointerException)
        if (dna == null) return false;

        int n = dna.length;
        if (n == 0) return false;

        // Validación básica de tamaño
        if (n < SEQUENCE_LENGTH) return false;

        char[][] matrix = new char[n][];

        // Convertir a char[][] para acceso rápido
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int sequencesFound = 0;

        // Recorrido optimizado
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // Early Termination
                if (sequencesFound > 1) return true;

                char base = matrix[i][j];

                // 1. Horizontal
                if (j <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i][j+1] &&
                            base == matrix[i][j+2] &&
                            base == matrix[i][j+3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // 2. Vertical
                if (i <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i+1][j] &&
                            base == matrix[i+2][j] &&
                            base == matrix[i+3][j]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // 3. Diagonal Principal
                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i+1][j+1] &&
                            base == matrix[i+2][j+2] &&
                            base == matrix[i+3][j+3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // 4. Diagonal Inversa
                if (i <= n - SEQUENCE_LENGTH && j >= SEQUENCE_LENGTH - 1) {
                    if (base == matrix[i+1][j-1] &&
                            base == matrix[i+2][j-2] &&
                            base == matrix[i+3][j-3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }
            }
        }
        return false;
    }
}