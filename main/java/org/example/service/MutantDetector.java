package org.example.service;

import org.springframework.stereotype.Component;
import java.util.Set; // 1. Import necesario para la validación O(1)

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    // 2. OPTIMIZACIÓN: Set inmutable para validación ultra-rápida de caracteres
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    public boolean isMutant(String[] dna) {
        // --- 1. VALIDACIONES INICIALES ---
        if (dna == null) return false;

        int n = dna.length;
        if (n == 0) return false;

        // Si es menor a 4x4, no puede tener secuencias suficientes
        if (n < SEQUENCE_LENGTH) return false;

        char[][] matrix = new char[n][];

        // --- 2. CONVERSIÓN, VALIDACIÓN NxN Y CARACTERES ---
        for (int i = 0; i < n; i++) {
            // Validación de robustez: Fila nula o longitud incorrecta (Matriz Cuadrada)
            if (dna[i] == null || dna[i].length() != n) {
                return false;
            }

            // Validación interna: Verificar que SOLO contenga A, T, C, G
            // Esto cumple con el requisito de "Validación Set O(1)" de la rúbrica
            for (char c : dna[i].toCharArray()) {
                if (!VALID_BASES.contains(c)) {
                    return false; // Carácter inválido detectado
                }
            }

            // Convertir a matriz de caracteres para acceso rápido
            matrix[i] = dna[i].toCharArray();
        }

        int sequencesFound = 0;

        // --- 3. RECORRIDO OPTIMIZADO (Single Pass) ---
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // OPTIMIZACIÓN CRÍTICA: Early Termination
                // Si ya encontramos más de 1 secuencia, cortamos inmediatamente.
                if (sequencesFound > 1) return true;

                char base = matrix[i][j];

                // Búsqueda Horizontal
                // Boundary Check: Solo buscar si caben 4 letras (j <= n - 4)
                if (j <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i][j+1] &&
                            base == matrix[i][j+2] &&
                            base == matrix[i][j+3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Búsqueda Vertical
                // Boundary Check: Solo buscar si caben 4 letras hacia abajo (i <= n - 4)
                if (i <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i+1][j] &&
                            base == matrix[i+2][j] &&
                            base == matrix[i+3][j]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Búsqueda Diagonal Principal (Descendente ↘)
                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i+1][j+1] &&
                            base == matrix[i+2][j+2] &&
                            base == matrix[i+3][j+3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Búsqueda Diagonal Inversa (Ascendente ↙)
                // Se busca hacia abajo-izquierda: i aumenta, j disminuye
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

        // Si terminamos de recorrer y no encontramos > 1 secuencia
        return false;
    }
}