package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        // 1. VALIDACIONES BÁSICAS
        if (dna == null) return false;

        int n = dna.length;
        if (n == 0) return false;

        // Si es menor a 4x4, no puede ser mutante
        if (n < SEQUENCE_LENGTH) return false;

        char[][] matrix = new char[n][];

        // 2. CONVERSIÓN Y VALIDACIÓN NxN (Aquí estaba el error)
        for (int i = 0; i < n; i++) {
            // Validar que cada fila tenga longitud 'n' (Matriz Cuadrada)
            // Si una fila es más corta o larga, no es válida para el examen.
            if (dna[i].length() != n) {
                return false;
            }
            matrix[i] = dna[i].toCharArray();
        }

        int sequencesFound = 0;

        // 3. RECORRIDO OPTIMIZADO
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // Early Termination
                if (sequencesFound > 1) return true;

                char base = matrix[i][j];

                // Horizontal
                if (j <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i][j+1] &&
                            base == matrix[i][j+2] &&
                            base == matrix[i][j+3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Vertical
                if (i <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i+1][j] &&
                            base == matrix[i+2][j] &&
                            base == matrix[i+3][j]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Diagonal Principal
                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i+1][j+1] &&
                            base == matrix[i+2][j+2] &&
                            base == matrix[i+3][j+3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Diagonal Inversa
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