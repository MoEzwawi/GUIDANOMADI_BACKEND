package MoEzwawi.GUIDANOMADI_BACKEND.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{

    public NotFoundException(UUID id) {
        super("Cliente "+id+" non trovato.");
    }
    public NotFoundException(long id) {
        super("Elemento "+id+" non trovato.");
    }
    public NotFoundException(String message) {
        super(message);
    }
}