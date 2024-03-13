package MoEzwawi.GUIDANOMADI_BACKEND.payloads.posts;

import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.BadRequestException;

public record NewPostDTO(
        String text,
        String imageUrl
) {
    public NewPostDTO{
        if(text == null || text.isBlank()){
            if(imageUrl == null || imageUrl.isBlank()){
                throw new BadRequestException("At least one of text or imageUrl must be provided.");
            }
        }
    }
}