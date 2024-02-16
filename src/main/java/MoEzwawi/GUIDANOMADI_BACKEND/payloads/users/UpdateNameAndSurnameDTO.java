package MoEzwawi.GUIDANOMADI_BACKEND.payloads.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateNameAndSurnameDTO(
        @NotBlank(message = "firstName is a required field")
        @Size(min = 3, max = 30, message = "firstName must be between 3 and 30 characters")
        String firstName,
        @NotBlank(message = "lastName is a required field")
        @Size(min = 3, max = 30, message = "lastName must be between 3 and 30 characters")
        String lastName
) {
}
