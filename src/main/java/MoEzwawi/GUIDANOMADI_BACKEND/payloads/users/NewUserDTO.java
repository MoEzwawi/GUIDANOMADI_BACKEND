package MoEzwawi.GUIDANOMADI_BACKEND.payloads.users;

import jakarta.validation.constraints.*;

public record NewUserDTO (
        @NotBlank(message = "firstName is a required field")
        @Size(min = 3, max = 30, message = "firstName must be between 3 and 30 characters")
        String firstName,
        @NotBlank(message = "firstName is a required field")
        @Size(min = 3, max = 30, message = "firstName must be between 3 and 30 characters")
        String lastName,
        @Email(message = "invalid email address")
        @NotBlank(message = "email is a required field")
        String email,
        @NotBlank(message = "password is a required field")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "La password deve avere almeno 6 caratteri, tra cui almeno una lettera e un numero")
        String password,
        String role
){
}
