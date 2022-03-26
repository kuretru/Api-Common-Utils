package com.kuretru.microservices.web.controller;

import com.kuretru.microservices.web.constant.code.ResponseCodes;
import com.kuretru.microservices.web.constant.code.ServiceErrorCodes;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.entity.ApiResponse;
import com.kuretru.microservices.web.entity.OAuth2ErrorResponse;
import com.kuretru.microservices.web.exception.OAuth2Exception;
import com.kuretru.microservices.web.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

/**
 * 统一异常处理控制器
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionController {

    // 框架异常处理

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);
            stringBuilder.append(fieldError.getObjectName()).append('.');
            stringBuilder.append(fieldError.getField()).append(':');
            stringBuilder.append(fieldError.getDefaultMessage()).append((i + 1 < fieldErrors.size()) ? "; " : "");
        }
        return ApiResponse.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, stringBuilder.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<?> bindExceptionHandler(HttpServletResponse response, BindException e) {
        ResponseCodes codes = UserErrorCodes.REQUEST_PARAMETER_ERROR;
        StringBuilder result = new StringBuilder();
        for (FieldError error : e.getFieldErrors()) {
            result.append("字段");
            result.append(error.getField());
            result.append('：');
            result.append(error.getDefaultMessage());
            result.append(',');
            if (error.getRejectedValue() == null) {
                codes = UserErrorCodes.MISSING_REQUIRED_PARAMETERS;
            }
        }
        result.deleteCharAt(result.length() - 1);
        return ApiResponse.build(codes, result.toString());
    }


    // 业务异常处理

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.BadRequest.class)
    public ApiResponse<?> badRequestHandler(ServiceException.BadRequest e) {
        return ApiResponse.build(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ServiceException.Unauthorized.class)
    public ApiResponse<?> unauthorizedHandler(ServiceException.Unauthorized e) {
        return ApiResponse.build(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ServiceException.Forbidden.class)
    public ApiResponse<?> forbiddenHandler(ServiceException.Forbidden e) {
        return ApiResponse.build(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ServiceException.NotFound.class)
    public ApiResponse<?> notFoundHandler(ServiceException.NotFound e) {
        return ApiResponse.build(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ApiResponse<?> undeclaredThrowableExceptionHandler(HttpServletResponse response, UndeclaredThrowableException e) {
        if (e.getCause() instanceof ServiceException serviceException) {
            // 判断是否是业务异常
            if (serviceException instanceof ServiceException.BadRequest) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            } else if (serviceException instanceof ServiceException.Unauthorized) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else if (serviceException instanceof ServiceException.Forbidden) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            } else if (serviceException instanceof ServiceException.NotFound) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
            return ApiResponse.build(serviceException.getCode(), serviceException.getMessage());
        } else {
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ApiResponse.build(ServiceErrorCodes.SYSTEM_EXECUTION_ERROR, buildExceptionMessage(e));
        }
    }


    // OAuth2异常处理

    @ExceptionHandler(OAuth2Exception.class)
    public OAuth2ErrorResponse oAuth2ErrorResponseHandler(HttpServletResponse response, OAuth2Exception e) {
        response.setStatus(e.getErrorEnum().getHttpStatus().value());
        return new OAuth2ErrorResponse(e.getErrorEnum(), e.getMessage());
    }


    // 其他异常处理

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.InternalServerError.class)
    public ApiResponse<?> internalServerErrorHandler(ServiceException.InternalServerError e) {
        return ApiResponse.build(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> exceptionHandler(Exception e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();
        return ApiResponse.build(ServiceErrorCodes.SYSTEM_EXECUTION_ERROR, buildExceptionMessage(e));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Error.class)
    public ApiResponse<?> errorHandler(Error e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();
        return ApiResponse.build(ServiceErrorCodes.SYSTEM_EXECUTION_ERROR, buildExceptionMessage(e));
    }

    private String buildExceptionMessage(Throwable e) {
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }

}
