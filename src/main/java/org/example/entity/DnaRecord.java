package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "dna_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DnaRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String hash; // Hash SHA-256 o concatenado

    @Lob
    private String sequence;

    private boolean isMutant;
}