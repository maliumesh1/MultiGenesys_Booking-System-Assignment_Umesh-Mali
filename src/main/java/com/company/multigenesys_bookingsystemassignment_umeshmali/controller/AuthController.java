package com.company.multigenesys_bookingsystemassignment_umeshmali.controller;

import com.company.multigenesys_bookingsystemassignment_umeshmali.dto.CreateUserRequest;
import com.company.multigenesys_bookingsystemassignment_umeshmali.dto.UserResponse;
import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.User;
import com.company.multigenesys_bookingsystemassignment_umeshmali.enums.Role;
import com.company.multigenesys_bookingsystemassignment_umeshmali.service.UserService;
import com.company.multigenesys_bookingsystemassignment_umeshmali.util.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        User user = userService.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }

        Set<String> roles = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        // ✅ Fix argument order (username, id, roles)
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), roles);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest req) {
        // Always force ROLE_USER
        Set<Role> roles = Set.of(Role.ROLE_USER);

        User created = userService.createUser(req.getUsername(), req.getPassword(), roles);

        UserResponse resp = new UserResponse(
                created.getId(),
                created.getUsername(),
                created.getRoles().stream().map(Enum::name).collect(Collectors.toSet())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;

        public LoginRequest() {}

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class TokenResponse {
        private final String token;
        public TokenResponse(String token) { this.token = token; }
        public String getToken() { return token; }
    }
}
