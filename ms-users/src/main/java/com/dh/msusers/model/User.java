package com.dh.msusers.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class User {

    private String id;
    private String userName;
    private String email;
    private String firstName;
    private List<Bill> billList;

    public User(String id, String userName, String email, String firstName) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
    }


}
