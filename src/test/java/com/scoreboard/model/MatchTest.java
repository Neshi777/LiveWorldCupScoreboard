package com.scoreboard.model;

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
                Arguments.of("Team A", "") // Empty away team
        );
    }

}