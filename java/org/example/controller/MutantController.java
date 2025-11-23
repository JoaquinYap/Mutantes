package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.service.MutantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutant")
@Tag(name = "Mutant Detector", description = "API para detectar mutantes")
@RequiredArgsConstructor
public class MutantController {

    private final MutantService service;

    @Operation(summary = "Analizar ADN", description = "Detecta si un humano es mutante basándose en su secuencia de ADN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante"),
            @ApiResponse(responseCode = "403", description = "Es Humano"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido (formato incorrecto)")
    })
    @PostMapping
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = service.analyze(request.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}