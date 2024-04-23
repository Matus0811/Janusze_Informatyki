package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@With
@Value
@Builder
public class User{
    Long userId;
    String username;
    String password;
    String name;
    String surname;
    Gender gender;
    String email;
    String phone;
    List<String> roles;

    public enum Gender{
        MALE,FEMALE,UNDEFINED_GENDER
    }
}
