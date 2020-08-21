package com.example.controller;

import com.example.model.domain.User;
import com.example.model.dto.UserCreationRequestDto;
import com.example.model.dto.UserDto;
import com.example.model.mapper.MapperUtils;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Api(value = "Users", tags = "Users")
@RestController
@RequestMapping("/api/v1/user")
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
    public ResponseEntity<UserDto> registerUser(@Valid @NotNull @RequestBody final UserCreationRequestDto userCreationRequestDto) {
        LOGGER.debug("Registering User with email:{}", userCreationRequestDto.getEmail());

        final UserCreationRequestDto request = MapperUtils.map(userCreationRequestDto, UserCreationRequestDto.class);
        final User createdUser = userService.create(request);
        final UserDto result = MapperUtils.map(createdUser, UserDto.class);

        LOGGER.info("Done registering user with email:{}", result.getEmail());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "Get all users.",
            nickname = "getAllUsers",
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        LOGGER.debug("Getting all users");

        final List<UserDto> userDtos = MapperUtils.mapAll(userService.findAll(), UserDto.class);

        LOGGER.info("Done getting all users");
        return ResponseEntity.ok(userDtos);
    }

    @ApiOperation(
            value = "Get a user by id.",
            nickname = "getUserById",
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("/get")
    public ResponseEntity<UserDto> get(@RequestParam @NotNull final Long id) {
        LOGGER.debug("Getting concrete user");

        final User user = userService.get(id);
        final UserDto userDto = MapperUtils.map(user, UserDto.class);

        LOGGER.debug("Done getting concrete user");
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation(
            value = "Block a concrete user.",
            nickname = "blockUser",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/block")
    public ResponseEntity<UserDto> blockUser(@RequestParam @NotNull final Long id) {
        LOGGER.debug("Blocking concrete user");

        final User blockedUser = userService.blockUser(id);
        final UserDto blockedUserDto = MapperUtils.map(blockedUser, UserDto.class);

        LOGGER.debug("Done blocking concrete user");
        return ResponseEntity.ok(blockedUserDto);
    }

    @ApiOperation(
            value = "Unblock a concrete user.",
            nickname = "unblockUser",
            authorizations = {@Authorization(value = "basicAuth")})
    @PostMapping("/unblock")
    public ResponseEntity<UserDto> unblockUser(@RequestParam @NotNull final Long id) {
        LOGGER.debug("Blocking concrete user");

        final User unblockedUser = userService.unblockUser(id);
        final UserDto unblockedUserDto = MapperUtils.map(unblockedUser, UserDto.class);

        LOGGER.debug("Done unblocking concrete user");
        return ResponseEntity.ok(unblockedUserDto);
    }

    @ApiOperation(
            value = "Delete a user.",
            nickname = "deleteUser",
            authorizations = {@Authorization(value = "basicAuth")})
    @DeleteMapping("/delete")
    public ResponseEntity<UserDto> delete(@RequestParam @NotNull final Long id) {
        LOGGER.debug("Deleting concrete user");

        final User deletedUser = userService.delete(id);
        final UserDto deletedUserDto = MapperUtils.map(deletedUser, UserDto.class);

        LOGGER.debug("Done deleting concrete user");
        return ResponseEntity.ok(deletedUserDto);
    }
}
