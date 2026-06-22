package com.example.session.constants;

/**
 * Standard HTTP Response Status Codes
 * and Application Error Codes.
 */
public final class WebResponseCodes {

    private WebResponseCodes() {
        // Prevent instantiation
    }

    // =====================================================
    // HTTP SUCCESS CODES (2xx)
    // =====================================================

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NO_CONTENT = 204;

    // =====================================================
    // HTTP REDIRECTION CODES (3xx)
    // =====================================================

    public static final int MOVED_PERMANENTLY = 301;
    public static final int FOUND = 302;
    public static final int NOT_MODIFIED = 304;
    public static final int TEMPORARY_REDIRECT = 307;
    public static final int PERMANENT_REDIRECT = 308;

    // =====================================================
    // HTTP CLIENT ERROR CODES (4xx)
    // =====================================================

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int CONFLICT = 409;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int TOO_MANY_REQUESTS = 429;

    // =====================================================
    // HTTP SERVER ERROR CODES (5xx)
    // =====================================================

    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;

    // =====================================================
    // APPLICATION SPECIFIC ERROR CODES (1000+)
    // =====================================================

    public static final int VALIDATION_FAILED = 1001;
    public static final int INVALID_REQUEST_DATA = 1002;
    public static final int RESOURCE_ALREADY_EXISTS = 1003;
    public static final int RESOURCE_NOT_FOUND = 1004;

    public static final int AUTHENTICATION_FAILED = 1101;
    public static final int INVALID_TOKEN = 1102;
    public static final int TOKEN_EXPIRED = 1103;
    public static final int ACCESS_DENIED = 1104;

    public static final int DATABASE_ERROR = 1201;
    public static final int CONNECTION_FAILURE = 1202;
    public static final int TRANSACTION_FAILED = 1203;

    public static final int FILE_UPLOAD_FAILED = 1301;
    public static final int FILE_NOT_FOUND = 1302;
    public static final int FILE_SIZE_EXCEEDED = 1303;

    public static final int UNKNOWN_ERROR = 9999;
}