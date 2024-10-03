package com.scoreboard.service;

import com.scoreboard.exception.ScoreServiceException;
import com.scoreboard.model.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FootballScoreServiceIntegrationTest {
    private FootballScoreService service;

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

}