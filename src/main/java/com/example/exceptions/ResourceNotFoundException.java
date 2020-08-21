package com.example.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    protected ResourceNotFoundException(final String message) {
        super(message);
    }

    protected ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected ResourceNotFoundException(final Throwable cause) {
        super(cause);
    }

    public static ResourceNotFoundException createInstance(final Class<?> resourceType, final String identifier) {
        return createInstance(resourceType, identifier, null);
    }

    public static ResourceNotFoundException createInstance(final String resourceType, final String identifier) {
        return createInstance(resourceType, identifier, null);
    }

    public static ResourceNotFoundException createInstance(
            final Class<?> resourceType,
            final String identifier,
            final Throwable cause
    ) {
        return new ResourceNotFoundException(String.format(
                "No %s can be found by given %s.",
                resourceType.getSimpleName(),
                identifier
        ), cause);
    }

    public static ResourceNotFoundException createInstance(
            final String resourceType,
            final String identifier,
            final Throwable cause
    ) {
        return new ResourceNotFoundException(
                String.format("No %s can be found by given %s.", resourceType, identifier),
                cause
        );
    }
}
