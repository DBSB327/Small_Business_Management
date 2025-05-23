package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.UserRequest;
import com.example.smallbusinessmanagement.enums.UserRole;
import com.example.smallbusinessmanagement.model.User;
import com.example.smallbusinessmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id,
                                               @RequestParam UserRole role) {
        return ResponseEntity.ok(userService.updateUserRole(id, role));
    }
}
