package MoEzwawi.GUIDANOMADI_BACKEND.payloads.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailDTO(
        @Email(message = "invalid email address")
        @NotBlank(message = "email is a required field")
        String email
) {
}
