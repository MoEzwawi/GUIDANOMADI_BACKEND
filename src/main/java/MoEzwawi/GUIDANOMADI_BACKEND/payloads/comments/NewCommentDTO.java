package MoEzwawi.GUIDANOMADI_BACKEND.payloads.comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCommentDTO(
        @NotBlank(message = "Comments must not be blank")
        String text
) {
}