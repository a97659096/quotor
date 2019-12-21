package com.quotorcloud.quotor.common.core.exception;

import com.quotorcloud.quotor.common.core.constant.enums.ExceptionEnum;

public class MyException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public MyException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public MyException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public MyException(String message){
        super(message);
    }

    public MyException(){
        super();
    }
}
