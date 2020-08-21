package com.example.service.impl;

import com.example.domain.User;
import com.example.domain.create.UserCreationModel;
import com.example.exceptions.ResourceAlreadyExistsException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User get(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> ResourceNotFoundException.createInstance(User.class, "id:" + userId));
    }

    @Override
    @Transactional
    public User create(UserCreationModel request) {
        final String email = request.getEmail();
        userRepository
                .findByEmail(email)
                .ifPresent((user) -> {
                    throw ResourceAlreadyExistsException.createInstance(User.class, "email", email);
                });

        final User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(request.getName());
        newUser.setType(request.getType());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User blockUser(Long userId) {
        User user = get(userId);
        if (user.getIsActive()) {
            user.setIsActive(false);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User unblockUser(Long userId) {
        User user = get(userId);
        if (!user.getIsActive()) {
            user.setIsActive(false);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public Optional<User> delete(Long userId) {
        Optional<User> user = Optional.ofNullable(get(userId));
        if (user.isPresent()) {
            userRepository.deleteById(userId);
        }
        return user;
    }
}
