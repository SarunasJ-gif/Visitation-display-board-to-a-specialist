package com.sarunas.Visitation.display.board.to.a.specialist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {SpecialistNotFoundException.class,
                               VisitNotFoundException.class,
                               VisitIsFinishedException.class,
                               VisitHappeningNowException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundExceptions(Exception e) {
        ErrorMessage errorMessage = getErrorMessage(e, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {SpecialistExistsException.class,
                               ForbiddenDeleteSpecialistException.class,
                               SpecialistIsBusyException.class})
    public ResponseEntity<ErrorMessage> handleForbiddenException(Exception e) {
        ErrorMessage errorMessage = getErrorMessage(e, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    private ErrorMessage getErrorMessage(Exception e, HttpStatus httpStatus) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setHttpStatus(httpStatus);
        errorMessage.setStackTrace(e.getStackTrace());
        errorMessage.setTimestamp(LocalDateTime.now());
        return errorMessage;
    }
}
