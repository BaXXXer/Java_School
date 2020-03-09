package edu.tsystems.javaschool.logapp.api.exceptions;

public class EntityNotFoundException extends BusinessLogicException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entity, int id) {
        super("Enity " + entity + " with id '" + id + "' not found");
    }


}
