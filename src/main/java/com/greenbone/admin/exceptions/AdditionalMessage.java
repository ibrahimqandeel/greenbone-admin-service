package com.greenbone.admin.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdditionalMessage {

    private int code;
    private String messageKey;
}
