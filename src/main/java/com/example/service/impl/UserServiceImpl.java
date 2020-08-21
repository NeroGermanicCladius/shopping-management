package com.example.service.impl;

import com.example.configuration.security.UserPrincipal;
import com.example.exceptions.ResourceAlreadyExistsException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.domain.User;
import com.example.model.dto.UserCreationRequestDto;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String email) {
        final Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No user with email:%s has been found", email));
        }
        return new UserPrincipal(userOpt.get());
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
    public User create(UserCreationRequestDto request) {
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
        newUser.setPassword(encode(request.getPassword()));
        newUser.setIsActive(true);

        return userRepository.save(newUser);
    }

    private String encode(final String password) {
        return passwordEncoder.encode(password);
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
    public User delete(Long userId) {
        final User user = get(userId);

        userRepository.deleteById(userId);

        return user;
    }
}
