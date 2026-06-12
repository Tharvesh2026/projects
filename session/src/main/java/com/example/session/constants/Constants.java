package com.example.session.constants;

/**
 * Application Constants
 *
 * Centralized location for:
 * - HTTP Status Codes
 * - Application Error Codes
 * - Error Messages
 * - Response Keys
 * - Header Names
 */
public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("Utility class");
    }

    // =====================================================
    // HTTP STATUS CODES
    // =====================================================

    public static final class HttpStatus {

        private HttpStatus() {}

        // Success
        public static final int OK = 200;
        public static final int CREATED = 201;
        public static final int ACCEPTED = 202;
        public static final int NO_CONTENT = 204;

        // Client Errors
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int METHOD_NOT_ALLOWED = 405;
        public static final int CONFLICT = 409;
        public static final int UNSUPPORTED_MEDIA_TYPE = 415;
        public static final int TOO_MANY_REQUESTS = 429;

        // Server Errors
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int BAD_GATEWAY = 502;
        public static final int SERVICE_UNAVAILABLE = 503;
        public static final int GATEWAY_TIMEOUT = 504;
    }

    // =====================================================
    // APPLICATION ERROR CODES
    // =====================================================

    public static final class ErrorCode {

        private ErrorCode() {}

        // Generic
        public static final String UNKNOWN_ERROR = "GEN-500";
        public static final String VALIDATION_FAILED = "VAL-400";

        // Authentication
        public static final String INVALID_CREDENTIALS = "AUTH-401";
        public static final String INVALID_TOKEN = "AUTH-402";
        public static final String TOKEN_EXPIRED = "AUTH-403";
        public static final String ACCESS_DENIED = "AUTH-404";

        // Resource
        public static final String USER_NOT_FOUND = "USR-404";
        public static final String CUSTOMER_NOT_FOUND = "CUS-404";
        public static final String RESOURCE_NOT_FOUND = "RES-404";
        public static final String RESOURCE_EXISTS = "RES-409";

        // Database
        public static final String DATABASE_ERROR = "DB-500";
        public static final String CONNECTION_FAILURE = "DB-501";
        public static final String TRANSACTION_FAILED = "DB-502";

        // File
        public static final String FILE_NOT_FOUND = "FILE-404";
        public static final String FILE_UPLOAD_FAILED = "FILE-500";
        public static final String FILE_SIZE_EXCEEDED = "FILE-413";

        // Integration
        public static final String API_FAILURE = "INT-500";
        public static final String EMAIL_FAILURE = "MAIL-500";
        public static final String PAYMENT_FAILURE = "PAY-500";
    }

    // =====================================================
    // ERROR MESSAGES
    // =====================================================

    public static final class ErrorMessage {

        private ErrorMessage() {}

        // Generic
        public static final String INTERNAL_SERVER_ERROR =
                "An unexpected system error occurred.";

        public static final String SERVICE_UNAVAILABLE =
                "Service is temporarily unavailable.";

        public static final String INVALID_REQUEST =
                "Invalid request.";

        public static final String VALIDATION_FAILED =
                "Request validation failed.";

        // Authentication
        public static final String INVALID_CREDENTIALS =
                "Invalid username or password.";

        public static final String INVALID_TOKEN =
                "Authentication token is invalid.";

        public static final String TOKEN_EXPIRED =
                "Authentication token has expired.";

        public static final String ACCESS_DENIED =
                "Access denied.";

        // Resource
        public static final String USER_NOT_FOUND =
                "User not found.";

        public static final String CUSTOMER_NOT_FOUND =
                "Customer not found.";

        public static final String RESOURCE_NOT_FOUND =
                "Requested resource was not found.";

        public static final String RESOURCE_EXISTS =
                "Resource already exists.";

        // Database
        public static final String DATABASE_ERROR =
                "Database operation failed.";

        public static final String CONNECTION_FAILURE =
                "Unable to connect to database.";

        public static final String TRANSACTION_FAILED =
                "Database transaction failed.";

        // File
        public static final String FILE_NOT_FOUND =
                "File not found.";

        public static final String FILE_UPLOAD_FAILED =
                "File upload failed.";

        // Integration
        public static final String API_FAILURE =
                "External service communication failed.";

        public static final String EMAIL_FAILURE =
                "Email delivery failed.";

        public static final String PAYMENT_FAILURE =
                "Payment processing failed.";
    }

    // =====================================================
    // RESPONSE JSON KEYS
    // =====================================================

    public static final class ResponseKey {

        private ResponseKey() {}

        public static final String SUCCESS = "success";
        public static final String STATUS = "status";
        public static final String ERROR_CODE = "errorCode";
        public static final String MESSAGE = "message";
        public static final String DATA = "data";
        public static final String TIMESTAMP = "timestamp";
        public static final String TRACE_ID = "traceId";
    }

    // =====================================================
    // HTTP HEADERS
    // =====================================================

    public static final class Header {

        private Header() {}

        public static final String AUTHORIZATION = "Authorization";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String ACCEPT = "Accept";
        public static final String USER_AGENT = "User-Agent";
        public static final String REQUEST_ID = "X-Request-Id";
        public static final String CORRELATION_ID = "X-Correlation-Id";
    }

    // =====================================================
    // CONTENT TYPES
    // =====================================================

    public static final class ContentType {

        private ContentType() {}

        public static final String JSON = "application/json";
        public static final String XML = "application/xml";
        public static final String TEXT = "text/plain";
        public static final String HTML = "text/html";
        public static final String PDF = "application/pdf";
        public static final String CSV = "text/csv";
    }
}