package com.lwj.websocketmvc.exception;

/**
 * @Author: lwj
 * @ClassName: AppKeyInvalidException
 * @Description: 自定义异常类
 */
public class AppKeyInvalidException extends RuntimeException {
    public AppKeyInvalidException(){
        super("参数有误，请检查");
    }

    public AppKeyInvalidException(String message){
        super(message);
    }
}
