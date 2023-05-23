package com.greenbone.admin.exceptions;

import java.util.List;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private ErrorMessageCode errorMessageCode;
    private Object[] params;

    private List<AdditionalMessage> additionalMessages;

    public AppException(ErrorMessageCode errorMessageCode) {
        this.errorMessageCode = errorMessageCode;
    }

    public AppException(ErrorMessageCode errorMessageCode, Object[] params) {
        this(errorMessageCode);
        this.params = params;
    }

    public AppException(ErrorMessageCode errorMessageCode,
        List<AdditionalMessage> additionalMessages) {
        this(errorMessageCode);
        this.additionalMessages = additionalMessages;
    }
}
