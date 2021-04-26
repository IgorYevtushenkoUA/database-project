package com.project.database.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class UserCredentials {
    private String username;
    private String password;
}
