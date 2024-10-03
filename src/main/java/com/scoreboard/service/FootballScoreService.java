package com.scoreboard.service;

import com.scoreboard.exception.InvalidTeamNameException;
import com.scoreboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FootballScoreService {
    private static final Logger logger = LoggerFactory.getLogger(FootballScoreService.class);
    private final Map<UUID, Match> matches = new HashMap<>();

    public UUID startMatch(String homeTeam, String awayTeam) throws InvalidTeamNameException {
        Match match = new Match(homeTeam, awayTeam);
        matches.put(match.getId(), match);
        logger.info("Match of ID: {} started", match.getId());
        return match.getId();
    }
}
