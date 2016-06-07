package com.cricketclub.user.dto;


import com.cricketclub.common.dto.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class UserList extends BaseDomain {

    private final List<User> users;

    public UserList(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
