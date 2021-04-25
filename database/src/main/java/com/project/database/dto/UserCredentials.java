package com.project.database.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Builder @ToString
public class UserCredentials {
    private String username;
    private String password;
}
