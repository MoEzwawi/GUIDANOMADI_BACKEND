package MoEzwawi.GUIDANOMADI_BACKEND.payloads.users;

import jakarta.validation.constraints.*;

public record NewUserDTO (
        @NotBlank(message = "firstName is a required field")
        @Size(min = 3, max = 30, message = "firstName must be between 3 and 30 characters")
        String firstName,
        @NotBlank(message = "lastName is a required field")
        @Size(min = 3, max = 30, message = "lastName must be between 3 and 30 characters")
        String lastName,
        @Email(message = "invalid email address")
        @NotBlank(message = "email is a required field")
        String email,
        @NotBlank(message = "password is a required field")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "The password must be at least 6 characters long, including at least one letter and one number.")
        String password,
        String role
){
}
