package MoEzwawi.GUIDANOMADI_BACKEND.payloads.comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCommentDTO(
        @NotNull(message = "Please provide the ID of the post for this comment")
        Long postId,
        @NotBlank(message = "Comments must not be blank")
        String text
) {
}