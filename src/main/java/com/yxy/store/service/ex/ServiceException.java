package com.yxy.store.service.ex;
/* 业务层异常的基类：throws new ServiceException("业务层产生未知的异常") */
public class ServiceException extends RuntimeException{
    public ServiceException() {
        super();
    }


    // 最常用
    public ServiceException(String message) {
        super(message);
    }
    // 最常用
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }


    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
