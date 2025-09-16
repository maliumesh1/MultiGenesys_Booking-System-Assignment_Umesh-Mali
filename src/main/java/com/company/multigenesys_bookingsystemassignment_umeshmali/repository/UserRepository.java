package com.company.multigenesys_bookingsystemassignment_umeshmali.repository;


import com.company.multigenesys_bookingsystemassignment_umeshmali.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
