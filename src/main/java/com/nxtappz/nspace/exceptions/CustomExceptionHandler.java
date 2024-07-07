package com.nxtappz.nspace.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorData> validationList = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorData(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        log.error("invalid fields {}", ex.getMessage());
        return getResponseEntity(HttpStatus.BAD_REQUEST, validationList, ex, request, headers);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException ex, WebRequest request){
        return getResponseEntity(HttpStatus.BAD_REQUEST, null, ex, request, new HttpHeaders());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity handleDataNotFoundException(DataNotFoundException ex, WebRequest request){
        return getResponseEntity(HttpStatus.NOT_FOUND, null, ex, request, new HttpHeaders());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity handleConflictDataException(DataConflictException ex, WebRequest request){
        return getResponseEntity(HttpStatus.CONFLICT, null, ex, request, new HttpHeaders());
    }


    private ResponseEntity<Object> getResponseEntity(
            HttpStatus httpStatus, List<ErrorData> erros, Exception ex, WebRequest request, HttpHeaders headers) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .errors(erros)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return handleExceptionInternal(ex, errorResponse,
                headers, httpStatus, request);
    }
}
