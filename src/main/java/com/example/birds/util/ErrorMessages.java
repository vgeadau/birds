package com.example.birds.util;

/**
 * Utility class that holds the error messages.
 */
public final class ErrorMessages {

    public static final String BIRD_NOT_FOUND = "Bird not found!";
    public static final String SIGHTING_NOT_FOUND = "Sighting not found!";

    public static final String NULL_PARAMETER_ERROR = "Method doesn't accept null parameters!";
    public static final String ORPHAN_RECORDS_ERROR = "Orphan sightings record detected!";

    public static final String INVALID_DATETIME = "Invalid dateTime provided!";

    /**
     * private constructor that throws exception in order to prevent instantiating through reflexion.
     */
    private ErrorMessages() {
        throw new UnsupportedOperationException();
    }
}
