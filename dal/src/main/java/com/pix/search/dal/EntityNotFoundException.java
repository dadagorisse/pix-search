package com.pix.search.dal;

public class EntityNotFoundException extends DataAccessException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
