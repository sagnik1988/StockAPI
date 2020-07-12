package com.assesment.stock.Exception;

public class ApiException extends RuntimeException {

    public ApiException(final String message) {
        super(message);
    }

    public ApiException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static final class InvalidInputException extends ApiException {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    public enum ErrorCodes {

        BAD_REQUEST(400);

        private int code;

        ErrorCodes(int code) {
            this.code = code;
        }

        public int code() {
            return this.code;
        }
    }

}
