package com.company.multigenesys_bookingsystemassignment_umeshmali.exception;


public class ApiException extends RuntimeException {
    public ApiException() { super(); }
    public ApiException(String message) { super(message); }
    public ApiException(String message, Throwable cause) { super(message, cause); }
}
