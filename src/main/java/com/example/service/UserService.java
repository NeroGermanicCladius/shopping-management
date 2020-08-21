package com.example.service;

import com.example.domain.User;
import com.example.domain.create.UserCreationModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User get(Long userId);

    User create(UserCreationModel request);

    List<User> findAll();

    User blockUser(Long userId);

    User unblockUser(Long userId);

    Optional<User> delete(Long userId);
}
