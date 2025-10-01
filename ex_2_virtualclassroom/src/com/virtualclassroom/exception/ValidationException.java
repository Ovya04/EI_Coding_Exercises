package com.virtualclassroom.exception;

public class ValidationException extends Exception {
    private final String fieldName;
    private final String invalidValue;
    
    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
        this.invalidValue = null;
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.fieldName = null;
        this.invalidValue = null;
    }
    
    public ValidationException(String fieldName, String invalidValue, String message) {
        super(String.format("Validation failed for field '%s' with value '%s': %s", 
                           fieldName, invalidValue, message));
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public String getInvalidValue() {
        return invalidValue;
    }
}
