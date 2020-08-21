package com.example.service;

import com.example.model.domain.User;
import com.example.model.dto.UserCreationRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User get(Long userId);

    User create(UserCreationRequestDto request);

    List<User> findAll();

    User blockUser(Long userId);

    User unblockUser(Long userId);

    User delete(Long userId);
}
