package com.example.configuration.exceptionhandling;

import com.example.exceptions.ResourceAlreadyExistsException;
import com.example.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class ExceptionMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDto> handleConstraintViolationException(final ConstraintViolationException e) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ApiErrorDto dto = new ApiErrorDto(status.value(), List.of(e.getMessage()));
        LOGGER.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;

        final Stream<String> firstSource = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()));
        final Stream<String> secondSource = exception
                .getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getObjectName(), error.getDefaultMessage()));

        final List<String> errors = Stream
                .concat(firstSource, secondSource)
                .collect(Collectors.toList());

        final ApiErrorDto dto = new ApiErrorDto(status.value(), errors);
        LOGGER.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleResourceNotFoundException(final ResourceNotFoundException e) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ApiErrorDto dto = new ApiErrorDto(status.value(), List.of(e.getMessage()));
        LOGGER.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorDto> handleResourceAlreadyExistsException(final ResourceAlreadyExistsException e) {
        final HttpStatus status = HttpStatus.CONFLICT;
        final ApiErrorDto dto = new ApiErrorDto(status.value(), List.of(e.getMessage()));
        LOGGER.info(dto.toString());
        return new ResponseEntity<>(dto, status);
    }

    @Data
    public static class ApiErrorDto {

        private final int status;
        private final List<String> messages;

        @JsonCreator
        public ApiErrorDto(
                @JsonProperty("status") final int status,
                @JsonProperty("messages") final List<String> messages
        ) {
            this.status = status;
            this.messages = messages;
        }

        public int getStatus() {
            return status;
        }

        public List<String> getMessages() {
            return messages;
        }
    }

}
