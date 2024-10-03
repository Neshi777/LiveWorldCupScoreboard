package com.scoreboard.service;

import com.scoreboard.exception.InvalidScoreException;
import com.scoreboard.exception.ScoreServiceException;
import com.scoreboard.model.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FootballScoreServiceIntegrationTest {
    private FootballScoreService service;
    private static final int DEFAULT_SCORE = 0;

    @BeforeEach
    public void setup() {
        service = new FootballScoreService();
    }

    @Test
    public void shouldGetStartingScoresMatchesSummaryInProperOrder() throws ScoreServiceException {
        // Given
        service.startMatch("Mexico", "Canada");
        service.startMatch("Spain", "Brazil");
        service.startMatch("Germany", "France");
        service.startMatch("Uruguay", "Italy");
        service.startMatch("Argentina", "Australia");

        // When
        List<Match> summary = service.getSummary();

        // Then
        List<String> expectedOrder = List.of(
                "Argentina - Australia",
                "Uruguay - Italy",
                "Germany - France",
                "Spain - Brazil",
                "Mexico - Canada"
        );

        List<String> actualOrder = summary.stream()
                .map(match -> match.getHomeTeam() + " - " + match.getAwayTeam())
                .toList();

        Assertions.assertEquals(actualOrder, expectedOrder, "The match order is incorrect");
    }

    @ParameterizedTest(name = "Update score for match between {0} and {1} with scores {2} and {3}")
    @CsvSource({
            "Argentina, Australia, 2, 1",
            "Uruguay, Italy, 3, 2",
            "Germany, Canada, 1, 1"
    })
    public void shouldUpdateMatchScore(String homeTeam, String awayTeam, int homeScore, int awayScore) throws ScoreServiceException, InvalidScoreException {
        // Given
        UUID matchId = service.startMatch(homeTeam, awayTeam);
        Match match = findMatchById(matchId);
        assertNotNull(match, "Match should be started");

        // Validate initial scores
        assertEquals(DEFAULT_SCORE, match.getHomeScore(), "Initial home score should be 0.");
        assertEquals(DEFAULT_SCORE, match.getAwayScore(), "Initial away score should be 0.");

        // When
        service.updateScore(matchId, homeScore, awayScore);

        // Fetch the match again to ensure we get the updated state
        Match updatedMatch = findMatchById(matchId);

        // Then
        assertEquals(homeScore, updatedMatch.getHomeScore(), "Home score should be updated correctly.");
        assertEquals(awayScore, updatedMatch.getAwayScore(), "Away score should be updated correctly.");
    }

    private Match findMatchById(UUID id) {
        return service.getSummary().stream()
                .filter(match -> match.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}