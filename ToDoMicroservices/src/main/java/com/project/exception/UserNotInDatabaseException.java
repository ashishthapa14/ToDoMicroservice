package com.project.exception;

public class UserNotInDatabaseException extends Exception {
    public UserNotInDatabaseException(String msg) {
        super(msg);
    }
}
