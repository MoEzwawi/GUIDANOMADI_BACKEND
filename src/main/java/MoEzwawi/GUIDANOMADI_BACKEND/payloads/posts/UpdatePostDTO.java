package MoEzwawi.GUIDANOMADI_BACKEND.payloads.posts;

import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.BadRequestException;
import jakarta.validation.constraints.NotNull;

public record UpdatePostDTO(
        String text,
        String imageUrl
) {
    public UpdatePostDTO{
        if(text == null || text.isBlank()){
            if(imageUrl == null || imageUrl.isBlank()){
                throw new BadRequestException("At least one of text or imageUrl must be provided.");
            }
        }
    }
}