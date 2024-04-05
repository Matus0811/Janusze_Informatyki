package org.project.projectmanagementsystem.domain;


import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class UserData {
    String name;
    String email;
}
