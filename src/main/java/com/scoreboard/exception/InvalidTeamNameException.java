package com.scoreboard.exception;

public class InvalidTeamNameException extends Exception {
    public InvalidTeamNameException(String message) {
        super(message);
    }
}