package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.project.projectmanagementsystem.api.dto.validators.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@With
@Value
@Builder
public class Credentials {
    String login;
    String password;

    public boolean isEmailLogin(){
        Pattern pattern = Pattern.compile(EmailValidator.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(login);

        return matcher.matches();
    }
}
