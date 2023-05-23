package com.greenbone.admin.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorMessageCode {
    INTERNAL_SERVER_ERROR(9000, HttpStatus.INTERNAL_SERVER_ERROR.value(), "error.general.message"),
    COMPUTER_ALREADY_ASSIGNED(9001, HttpStatus.CONFLICT.value(), "error.computer.already.assigned"),
    COMPUTER_NOT_ASSIGNED_TO_EMPLOYEE(9002, HttpStatus.PRECONDITION_FAILED.value(), "error.computer.not.assigned.to.employee"),
    RESOURCE_NOT_FOUND_ERROR(9002, HttpStatus.NOT_FOUND.value(), "error.resource.notfound");

    private int httpStatus;
    private int errorCode;
    private String messageKey;

    ErrorMessageCode(int httpStatus, String messageKey) {
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;
    }

    ErrorMessageCode(int errorCode, int httpStatus, String messageKey) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.messageKey = messageKey;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
