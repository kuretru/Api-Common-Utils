package com.kuretru.microservices.web.exception;

import com.kuretru.microservices.web.constant.code.ResponseCodes;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public class ServiceException extends Exception {

    @Getter
    private final ResponseCodes code;

    private ServiceException(ResponseCodes code, String message) {
        super(message);
        this.code = code;
    }

    public static ServiceException build(ResponseCodes code, String message) {
        return new ServiceException(code, message);
    }

}