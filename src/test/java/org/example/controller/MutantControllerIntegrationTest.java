package org.example.controller;

import org.example.dto.DnaRequest;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MutantControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DnaRecordRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testFullFlow_Mutant_ShouldSaveToDb() throws InterruptedException {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        ResponseEntity<Void> response = restTemplate.postForEntity("/mutant", request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        waitForDbCount(1);

        assertEquals(1, repository.count());
        assertTrue(repository.findAll().get(0).isMutant());
    }

    @Test
    void testFullFlow_Human_ShouldSaveToDb() throws InterruptedException {
        String[] dna = {"ATGC", "CAGT", "TGCA", "GCAT"};
        DnaRequest request = new DnaRequest();
        request.setDna(dna);

        ResponseEntity<Void> response = restTemplate.postForEntity("/mutant", request, Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        waitForDbCount(1);

        assertEquals(1, repository.count());
        assertFalse(repository.findAll().get(0).isMutant());
    }

    private void waitForDbCount(int expectedCount) throws InterruptedException {
        int attempts = 0;
        while (repository.count() != expectedCount && attempts < 20) {
            Thread.sleep(100);
            attempts++;
        }
    }
}