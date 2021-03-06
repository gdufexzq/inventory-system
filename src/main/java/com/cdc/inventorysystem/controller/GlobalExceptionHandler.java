package com.cdc.inventorysystem.controller;

import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.common.exceptions.AccountLockException;
import com.cdc.inventorysystem.common.exceptions.NoAuthException;
import com.cdc.inventorysystem.common.exceptions.NoRegisterException;
import com.cdc.inventorysystem.common.exceptions.ParameterException;
import com.cdc.inventorysystem.common.exceptions.SystemException;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截器
 */
@CrossOrigin(origins = "http://127.0.0.1:5500",
        maxAge = 3600, allowCredentials = "true",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS},
        allowedHeaders = "*")
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = SystemException.class)
    public ResponseVO systemException(Exception e) {
        logger.error("[op_rslt: error, system error.", e);
        return new ResponseVO(ResponseStatusEnum.SYSTEM_ERROR, "", e.getMessage());
    }

    @ExceptionHandler(value = NoRegisterException.class)
    public ResponseVO NoRegisterException(Exception e) {
        logger.info("[op_rslt: error, no register error.", e);
        return new ResponseVO(ResponseStatusEnum.NO_REGISTER, "", e.getMessage());
    }

    @ExceptionHandler(value = ParameterException.class)
    public ResponseVO ParameterException(Exception e) {
        logger.info("[op_rslt: error, parameter error.", e);
        return new ResponseVO(ResponseStatusEnum.PARAMETER_ERROR, "", e.getMessage());
    }

    @ExceptionHandler(value = NoAuthException.class)
    public ResponseVO noAuthException(Exception e) {
        logger.info("[op_rslt: error, no auth error.", e);
        return new ResponseVO(ResponseStatusEnum.NO_AUTH, "", e.getMessage());
    }
    
    @ExceptionHandler(value = AccountLockException.class)
    public ResponseVO accountLockException(Exception e) {
        logger.info("[op_rslt: error, account lock error.", e);
        return new ResponseVO(ResponseStatusEnum.ACCOUNT_LOCK, "", e.getMessage());
    }
}
