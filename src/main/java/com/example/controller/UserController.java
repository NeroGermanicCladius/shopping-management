package com.example.controller;

import com.example.domain.User;
import com.example.domain.create.UserCreationModel;
import com.example.domain.dto.UserDto;
import com.example.mapper.MapperUtils;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Api(value = "Users", tags = "Users")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(
            value = "Create a user.",
            nickname = "createUser")
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @NotNull @RequestBody final UserCreationModel userCreationRequestDto) {
        LOGGER.debug("Registering User with email:{}", userCreationRequestDto.getEmail());

        final UserCreationModel request = MapperUtils.map(userCreationRequestDto, UserCreationModel.class);
        final User createdUser = userService.create(request);
        final UserDto result = MapperUtils.map(createdUser, UserDto.class);

        LOGGER.info("Done registering user with email:{}", result.getEmail());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ApiOperation(
            value = "Get all users.",
            nickname = "getAllUsers",
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        LOGGER.debug("Getting all users");

        List<UserDto> userDtos = MapperUtils.mapAll(userService.findAll(), UserDto.class);

        LOGGER.info("Done getting all users");
        return ResponseEntity.ok(userDtos);
    }

    @ApiOperation(
            value = "Get a user by id.",
            nickname = "getUserById",
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("{id}")
    public ResponseEntity<UserDto> get(@PathVariable @NotNull final Long id) {
        LOGGER.debug("Getting concrete user");
        Optional<User> user = Optional.ofNullable(userService.get(id));
        if (!user.isPresent()) {
            LOGGER.debug("Not found concrete user");
            return ResponseEntity.notFound().build();
        }
        final UserDto result = MapperUtils.map(user.get(), UserDto.class);

        LOGGER.debug("Done getting concrete user");
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ApiOperation(
            value = "Block a concrete user.",
            nickname = "blockUser",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("{id}")
    public ResponseEntity<UserDto> blockUser(@PathVariable @NotNull final Long id) {
        LOGGER.debug("Blocking concrete user");

        Optional<User> user = Optional.ofNullable(userService.blockUser(id));
        if (!user.isPresent()) {
            LOGGER.debug("Not found concrete user");
            return ResponseEntity.notFound().build();
        }

        LOGGER.debug("Done blocking concrete user");
        return ResponseEntity.ok(MapperUtils.map(user.get(), UserDto.class));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ApiOperation(
            value = "Unblock a concrete user.",
            nickname = "unblockUser",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("{id}")
    public ResponseEntity<UserDto> unblockUser(@PathVariable @NotNull final Long id) {
        LOGGER.debug("Blocking concrete user");

        Optional<User> user = Optional.ofNullable(userService.unblockUser(id));
        if (!user.isPresent()) {
            LOGGER.debug("Not found concrete user");
            return ResponseEntity.notFound().build();
        }

        LOGGER.debug("Done unblocking concrete user");
        return ResponseEntity.ok(MapperUtils.map(user.get(), UserDto.class));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ApiOperation(
            value = "Delete a user.",
            nickname = "deleteUser",
            authorizations = {@Authorization(value = "basicAuth")})
    @DeleteMapping("{id}")
    public ResponseEntity<UserDto> delete(@PathVariable @NotNull final Long id) {
        LOGGER.debug("Deleting concrete user");

        Optional<User> user = userService.delete(id);
        if (!user.isPresent()) {
            LOGGER.debug("Not found concrete user");
            return ResponseEntity.notFound().build();
        }

        LOGGER.debug("Done deleting concrete user");
        return ResponseEntity.ok(MapperUtils.map(user.get(), UserDto.class));
    }
}
