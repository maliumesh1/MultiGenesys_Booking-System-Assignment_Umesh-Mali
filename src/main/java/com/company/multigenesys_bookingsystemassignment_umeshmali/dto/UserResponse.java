package com.company.multigenesys_bookingsystemassignment_umeshmali.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private Set<String> roles;
}
