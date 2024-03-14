package com.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode
public class User {
    Long userId;
    String username;
    String password;
    String name;
    String surname;
    String email;
    String phone;
}
