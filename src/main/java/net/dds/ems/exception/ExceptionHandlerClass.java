package net.dds.ems.exception;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.ErrorEntity;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandlerClass {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    public @ResponseBody ErrorEntity handleEntityNotFound(EntityNotFoundException exception){
        return new ErrorEntity(null,exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public @ResponseBody ErrorEntity handleBadRequest(BadRequestException exception){
        return new ErrorEntity(null,exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody ErrorEntity handleValidationExceptions(IllegalArgumentException ex) {
        return new ErrorEntity(null, ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorEntity handleExceptions(Exception ex) {
        return new ErrorEntity(null, ex.getMessage());
    }
}
