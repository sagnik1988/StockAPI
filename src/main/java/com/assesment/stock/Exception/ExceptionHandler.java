package com.assesment.stock.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.InvalidInputException.class)
    public ResponseEntity<StockApiError> handle(final ApiException.InvalidInputException e) {
        final StockApiError error = StockApiError.builder()
                .message(e.getMessage())
                .code(ApiException.ErrorCodes.BAD_REQUEST.code())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }
}
