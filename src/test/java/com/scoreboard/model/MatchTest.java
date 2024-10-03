package com.scoreboard.model;

import com.scoreboard.exception.InvalidScoreException;
import com.scoreboard.exception.InvalidTeamNameException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @ParameterizedTest(name = "Create match between {0} and {1}")
    @CsvSource({
            "Team A, Team B",
            "Team C, Team D",
            "Team E, Team F"
    })
    public void shouldCreateMatch(String homeTeam, String awayTeam) throws InvalidTeamNameException {
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

    @ParameterizedTest(name = "Throw InvalidTeamNameException for home team: {0} and away team: {1}")
    @MethodSource("invalidTeamNamesProvider")
    public void shouldThrowInvalidTeamNamesExceptions(String homeTeam, String awayTeam) {
        // Given - no specific setup needed for this test case

        // When & Then
        assertThrows(InvalidTeamNameException.class, () -> new Match(homeTeam, awayTeam));
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

    @ParameterizedTest(name = "Update score for match between {0} and {1} to {2} - {3}")
    @CsvSource({
            "Team A, Team B, 2, 1",
            "Team C, Team D, 3, 3",
            "Team E, Team F, 0, 5"
    })
    public void shouldUpdateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) throws InvalidTeamNameException, InvalidScoreException {
        // Given
        Match match = new Match(homeTeam, awayTeam);

        // When
        match.updateScore(homeScore, awayScore);

        // Then
        assertEquals(homeScore, match.getHomeScore());
        assertEquals(awayScore, match.getAwayScore());
    }

    @ParameterizedTest(name = "Throw InvalidScoreException for scores {0} - {1}")
    @CsvSource({
            "-1, 1",   // Invalid home score
            "1, -1",   // Invalid away score
            "-10, -5"  // Both scores invalid
    })
    public void shouldThrowInvalidScoreUpdate(int homeScore, int awayScore) throws InvalidTeamNameException {
        // Given
        Match match = new Match("Team A", "Team B");

        // When & Then
        assertThrows(InvalidScoreException.class, () -> match.updateScore(homeScore, awayScore));
    }

}