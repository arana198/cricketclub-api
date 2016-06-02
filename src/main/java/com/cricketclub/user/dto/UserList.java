package com.cricketclub.user.dto;


import com.cricketclub.common.dto.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class UserList extends BaseDomain {

    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
