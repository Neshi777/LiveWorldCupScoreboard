package com.scoreboard.model;

import com.scoreboard.exception.InvalidScoreException;
import com.scoreboard.exception.InvalidTeamNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Match {
    private static final Logger logger = LoggerFactory.getLogger(Match.class);
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final int startOrder;
    private final UUID id;
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    public Match(String homeTeam, String awayTeam) throws InvalidTeamNameException {
        validateTeamNames(homeTeam, awayTeam);
        this.startOrder = counter.incrementAndGet();
        this.id = UUID.randomUUID();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public void updateScore(int homeScore, int awayScore) throws InvalidScoreException {
        validateScore(homeScore, awayScore);
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    private void validateTeamNames(String homeTeam, String awayTeam) throws InvalidTeamNameException {
        validateTeamsNullOrBlank(homeTeam, awayTeam);

        validateDifferentTeams(homeTeam, awayTeam);
    }

    private void validateTeamsNullOrBlank(String homeTeam, String awayTeam) throws InvalidTeamNameException {
        if (homeTeam == null || homeTeam.isBlank() || awayTeam == null || awayTeam.isBlank()) {
            String errorMessage = String.format("Team names cannot be null or empty, home: '%s' , away: '%s'", homeTeam, awayTeam);
            logger.error(errorMessage);
            throw new InvalidTeamNameException(errorMessage);
        }
    }

    private void validateDifferentTeams(String homeTeam, String awayTeam) throws InvalidTeamNameException {
        if (homeTeam.equals(awayTeam)) {
            String errorMessage = String.format("Home team and away team cannot be the same, home: '%s', away: '%s'", homeTeam, awayTeam);
            logger.error(errorMessage);
            throw new InvalidTeamNameException(errorMessage);
        }
    }

    private void validateScore(int homeScore, int awayScore) throws InvalidScoreException {
        if (homeScore < 0 || awayScore < 0) {
            String errorMessage = String.format("Scores cannot be negative, home: %d - away: %d", homeScore, awayScore);
            logger.error(errorMessage);
            throw new InvalidScoreException(errorMessage);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public int getStartOrder() {
        return startOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return startOrder == match.startOrder && id.equals(match.id) && homeTeam.equals(match.homeTeam) && awayTeam.equals(match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startOrder, id, homeTeam, awayTeam);
    }
}
