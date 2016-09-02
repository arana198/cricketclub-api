package com.cricketclub.user.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class UserList extends BaseDomain {
    private final List<User> users;

    @JsonCreator
    public UserList(@JsonProperty(value = "users", required = true) List<User> users) {
        this.users = users;
    }
}
