package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.UserRequest;
import com.example.smallbusinessmanagement.enums.UserRole;
import com.example.smallbusinessmanagement.model.User;
import com.example.smallbusinessmanagement.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    @Transactional
    public User updateUserRole(Long userId, UserRole newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        user.setRole(newRole);
        return userRepository.save(user);
    }
}