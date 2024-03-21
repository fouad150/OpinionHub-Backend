package com.youcode.opinionhub.payload.request;


import com.youcode.opinionhub.enums.Role;
import com.youcode.opinionhub.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "firstname is required")
    @NotNull(message = "firstname is required")
    private String name;
    @NotBlank(message = "username is required")
    @NotNull(message = "username is required")
    private String usedName;
    @NotBlank(message = "email is required")
    @Email(message = "email format is not valid")
    @NotNull(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    @NotNull(message = "password is required")
    @StrongPassword
    private String password;
    @NotNull
    private Role role;
}
