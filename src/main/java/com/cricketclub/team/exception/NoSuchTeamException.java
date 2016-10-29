package com.cricketclub.team.exception;

import com.cricketclub.common.exception.ObjectNotFoundException;

public class NoSuchTeamException extends ObjectNotFoundException {
    public NoSuchTeamException() {
        super("Committee role not found");
    }

    public NoSuchTeamException(final Long teamId) {
        super("Team " + teamId + " not found");
    }
}
