package com.scoreboard.model;

import java.util.Objects;
import java.util.UUID;

public class Match {
    private final UUID id;
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    public Match(String homeTeam, String awayTeam) {
        this.id = UUID.randomUUID();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return homeScore == match.homeScore && id.equals(match.id) && homeTeam.equals(match.homeTeam) && awayTeam.equals(match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, homeTeam);
    }
}
