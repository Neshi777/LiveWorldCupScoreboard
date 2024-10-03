package com.scoreboard.service;

import com.scoreboard.exception.InvalidTeamNameException;
import com.scoreboard.exception.MatchAlreadyExistsException;
import com.scoreboard.exception.ScoreServiceException;
import com.scoreboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FootballScoreService {
    private static final Logger logger = LoggerFactory.getLogger(FootballScoreService.class);
    private final Map<UUID, Match> matches = new HashMap<>();

    public UUID startMatch(String homeTeam, String awayTeam) throws ScoreServiceException {
        try {
            validateMatchExists(homeTeam, awayTeam);
            Match match = new Match(homeTeam, awayTeam);
            matches.put(match.getId(), match);
            logger.info("Match of ID: {} started", match.getId());
            return match.getId();
        } catch (InvalidTeamNameException | MatchAlreadyExistsException e) {
            throw new ScoreServiceException("Error starting match: " + e.getMessage(), e);
        }
    }

    private void validateMatchExists(String homeTeam, String awayTeam) throws MatchAlreadyExistsException {
        boolean matchExists = matches.values().stream()
                .anyMatch(existingMatch ->
                        existingMatch.getHomeTeam().equals(homeTeam) &&
                                existingMatch.getAwayTeam().equals(awayTeam)
                );
        if (matchExists) {
            String errorMessage = String.format("Match already on scoreboard, home: %s - away: %s", homeTeam, awayTeam);
            logger.error(errorMessage);
            throw new MatchAlreadyExistsException(errorMessage);
        }
    }
}
