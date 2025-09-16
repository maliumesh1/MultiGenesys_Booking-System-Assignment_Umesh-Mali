package com.company.multigenesys_bookingsystemassignment_umeshmali.service;




import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.User;
import com.company.multigenesys_bookingsystemassignment_umeshmali.enums.Role;
import com.company.multigenesys_bookingsystemassignment_umeshmali.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public java.util.Optional<User> findByUsername(String username) { return repo.findByUsername(username); }

    public User save(User u) { return repo.save(u); }

    public User createUser(String username, String rawPassword, Set<Role> roles) {
        if (repo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        User u = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .roles(roles)
                .enabled(true)
                .build();
        return repo.save(u);
    }

    // helper to convert role names (strings) to Role enums:
    public static Set<Role> parseRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return Set.of(Role.ROLE_USER);
        }
        return roleNames.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    // tolerate both "ADMIN" / "ROLE_ADMIN"
                    if (s.startsWith("ROLE_")) return Role.valueOf(s);
                    return Role.valueOf("ROLE_" + s);
                })
                .collect(Collectors.toSet());
    }
}
