package com.scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FootballScoreServiceTest {
    private FootballScoreService service;

    @BeforeEach
    public void setUp() {
        service = new FootballScoreService();
    }

    @ParameterizedTest(name = "Start match between {0} and {1}")
    @CsvSource({
            "Mexico, Canada",
            "Spain, Brazil"
    })
    public void shouldStartMatch(String homeTeam, String awayTeam) {
        // Given
        UUID matchId;

        // When
        matchId = service.startMatch(homeTeam, awayTeam);

        // Then
        assertNotNull(matchId, "Match should be started - not null matchId");
    }
}
