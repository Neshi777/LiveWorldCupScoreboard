package com.scoreboard.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MatchTest {

    @ParameterizedTest(name = "Create match between {0} and {1}")
    @CsvSource({
            "Team A, Team B",
            "Team C, Team D",
            "Team E, Team F"
    })
    public void shouldCreateMatch(String homeTeam, String awayTeam) {
        // Given
        Match match;

        // When
        match = new Match(homeTeam, awayTeam);

        // Then
        assertNotNull(match);
        assertNotNull(match.getId());
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(awayTeam, match.getAwayTeam());
        assertEquals(0, match.getAwayScore());
    }

}