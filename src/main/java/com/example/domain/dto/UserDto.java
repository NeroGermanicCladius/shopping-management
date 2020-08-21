package com.example.domain.dto;

import com.example.domain.UserType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private UserType type;
    private Boolean isActive;

    public UserDto() {
        this(null, null, null, null, null);
    }

    @JsonCreator
    public UserDto(
            @JsonProperty("id") final Long id,
            @JsonProperty("name") final String name,
            @JsonProperty("email") final String email,
            @JsonProperty("type") final UserType type,
            @JsonProperty("isActive") final Boolean isActive
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.isActive = isActive;
    }
}
