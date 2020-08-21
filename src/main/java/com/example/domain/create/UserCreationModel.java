package com.example.domain.create;

import com.example.domain.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationModel {
    private String name;
    private String email;
    private UserType type;
    private String password;

    public UserCreationModel() {
        this(null, null, null, null);
    }

    public UserCreationModel(
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
