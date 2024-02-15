package MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateAddressDTO(
        @NotBlank(message = "street is a required field")
        String street,
        @Pattern(regexp = "\\\\d+[a-zA-Z]?", message = "Invalid street number")
        String streetNumber,
        @NotBlank(message = "zipCode is a required field")
        @Pattern(regexp = "\\d{5}(-\\d{4})?", message = "Invalid ZIP code")
        String zipCode,
        String city,
        @NotBlank(message = "provinceOrStateCode is a required field")
        @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid state abbreviation")
        String provinceOrStateCode,
        String country
) {
}
