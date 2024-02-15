package MoEzwawi.GUIDANOMADI_BACKEND.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{

    public NotFoundException(UUID id) {
        super("User "+id+" not found.");
    }
    public NotFoundException(Long id) {
        super("Element "+id+" not found.");
    }
    public NotFoundException(String message) {
        super(message);
    }
}