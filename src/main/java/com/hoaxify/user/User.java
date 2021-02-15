package com.hoaxify.user;

import lombok.Data;

@Data
public class User {

    private String username;
    private String displayName;
    private String password;

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
