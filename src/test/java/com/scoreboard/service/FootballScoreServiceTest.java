package com.scoreboard.service;

import com.scoreboard.exception.ScoreServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void shouldStartMatch(String homeTeam, String awayTeam) throws ScoreServiceException {
        // Given
        UUID matchId;

        // When
        matchId = service.startMatch(homeTeam, awayTeam);

        // Then
        assertNotNull(matchId, "Match should be started - not null matchId");
    }

    @ParameterizedTest(name = "Throw ScoreServiceException when adding existing match")
    @CsvSource({
            "Mexico, Canada"
    })
    public void shouldThrowExceptionWhenStartingMatchExists(String homeTeam, String awayTeam) throws ScoreServiceException {
        // Given
        service.startMatch(homeTeam, awayTeam); //match first start

        // When & Then
        assertThrows(ScoreServiceException.class, () -> service.startMatch(homeTeam, awayTeam)); //2nd start try
    }

    @ParameterizedTest(name = "Throw ScoreServiceException when adding invalid team name match")
    @MethodSource("invalidTeamNamesProvider")
    public void shouldThrowExceptionWhenStartingMatchWithIncorrectNames(String homeTeam, String awayTeam) {
        // When & Then:
        assertThrows(ScoreServiceException.class, () -> service.startMatch(homeTeam, awayTeam));
    }

    private static Stream<Arguments> invalidTeamNamesProvider() {
        return Stream.of(
                Arguments.of("", "Team B"), // Empty home team
                Arguments.of("Team A", ""), // Empty away team
                Arguments.of(null, "Team A"), // Null home team
                Arguments.of("Team A", null), // Null away team
                Arguments.of("Team A", "Team A") // Same home and away team
        );
    }
}
