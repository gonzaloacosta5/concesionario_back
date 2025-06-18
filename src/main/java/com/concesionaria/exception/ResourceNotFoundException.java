// File: src/main/java/com/concesionaria/exception/ResourceNotFoundException.java
package com.concesionaria.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
