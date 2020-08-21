package com.example.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {
    private String name;
    private String email;
    private UserType type;
    private String password;

    public UserCreationRequest() {
        this(null, null, null, null);
    }

    public UserCreationRequest(
            final String name,
            final String email,
            final UserType type,
            final String password
    ) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.password = password;
    }
}
