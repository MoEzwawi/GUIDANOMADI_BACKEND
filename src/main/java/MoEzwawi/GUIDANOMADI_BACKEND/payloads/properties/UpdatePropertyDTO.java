package MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties;

import java.time.LocalDate;

public record UpdatePropertyDTO(
        String title,
        String description,
        String whyIsPerfect,
        Integer numberOfRooms,
        Integer sizeSqMeters,
        Integer floorNumber,
        Integer numberOfToilets,
        Double price,
        LocalDate availableFrom
) {
}
