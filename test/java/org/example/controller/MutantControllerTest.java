package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DnaRequest;
import org.example.service.MutantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testIsMutant_Returns200() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        when(service.analyze(dna)).thenReturn(true);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testIsHuman_Returns403() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        when(service.analyze(dna)).thenReturn(false);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testInvalidDna_Returns400_InvalidCharacters() throws Exception {
        // ADN con números o letras inválidas
        String[] dna = {"1234", "CAGT", "TTAT", "AGAC"};
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        // El validador @ValidDnaSequence debería capturar esto antes de llegar al servicio
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidDna_Returns400_NullDna() throws Exception {
        DnaRequest request = new DnaRequest();
        request.setDna(null);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidDna_Returns400_NonSquare() throws Exception {
        // Matriz no cuadrada
        String[] dna = {"ATG", "CAG", "TTA", "AGA"};
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}