// File: src/main/java/com/concesionaria/exception/DuplicateResourceException.java
package com.concesionaria.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String msg) {
        super(msg);
    }
}
