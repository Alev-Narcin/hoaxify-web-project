package com.hoaxify.ws.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

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
