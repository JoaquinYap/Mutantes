package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "dna_records", indexes = {
        @Index(name = "idx_dna_hash", columnList = "dna_hash"), // Cambiar nombre columna índice
        @Index(name = "idx_is_mutant", columnList = "isMutant")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DnaRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CAMBIO AQUÍ: de 'hash' a 'dnaHash' y en la columna a 'dna_hash'
    @Column(name = "dna_hash", unique = true, nullable = false)
    private String dnaHash;

    @Lob
    private String sequence;

    private boolean isMutant;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}