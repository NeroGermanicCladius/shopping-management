package com.example.model.dto;

import com.example.model.domain.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequestDto {
    private String name;
    private String email;
    private UserType type;
    private String password;

    public UserCreationRequestDto() {
        this(null, null, null, null);
    }

    public UserCreationRequestDto(
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
