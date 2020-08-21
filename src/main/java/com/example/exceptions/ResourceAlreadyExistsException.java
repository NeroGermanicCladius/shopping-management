package com.example.exceptions;

import java.util.Map;
import java.util.stream.Collectors;

public class ResourceAlreadyExistsException extends RuntimeException {

    private ResourceAlreadyExistsException(
            final String resourceType,
            final String identifier,
            final Throwable cause
    ) {
        super(String.format("%s already exists by %s", resourceType, identifier), cause);
    }

    private ResourceAlreadyExistsException(
            final Class<?> resourceType,
            final String identifier,
            final Throwable cause
    ) {
        super(String.format("%s already exists by %s", resourceType.getSimpleName(), identifier), cause);
    }

    public static ResourceAlreadyExistsException createInstance(
            final Class<?> resourceType,
            final String fieldName,
            final Object fieldValue
    ) {
        return new ResourceAlreadyExistsException(
                resourceType,
                formatFieldValue(fieldName, fieldValue),
                null
        );
    }

    public static ResourceAlreadyExistsException createInstance(
            final Class<?> resourceType,
            final Map<String, Object> fieldValueMap
    ) {
        return createInstance(resourceType, fieldValueMap, null);
    }

    public static ResourceAlreadyExistsException createInstance(
            final Class<?> resourceType,
            final Map<String, Object> fieldValueMap,
            final Throwable cause
    ) {
        return new ResourceAlreadyExistsException(resourceType, fieldValueToString(fieldValueMap), cause);
    }

    public static ResourceAlreadyExistsException createInstance(
            final String resourceType,
            final Map<String, Object> fieldValueMap,
            final Throwable cause
    ) {
        return new ResourceAlreadyExistsException(resourceType, fieldValueToString(fieldValueMap), cause);
    }

    private static String fieldValueToString(final Map<String, Object> fieldValueMap) {
        return fieldValueMap.entrySet().stream().map((entry) -> {
            return formatFieldValue(entry.getKey(), entry.getValue());
        }).collect(Collectors.joining(", "));
    }

    private static String formatFieldValue(final String key, final Object value) {
        return String.format("%s: %s", key, value instanceof String ? String.format("'%s'", value) : value);
    }
}