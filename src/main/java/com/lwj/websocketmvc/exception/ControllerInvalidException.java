package com.lwj.websocketmvc.exception;

/**
 * @Author: lwj
 * @ClassName: ControllerInvalidException
 * @Description: 初始化自定义控制器时的异常
 */
public class ControllerInvalidException extends RuntimeException {

    public ControllerInvalidException(){
        super("控制器编写有问题，请检查");
    }

    public ControllerInvalidException(String message){
        super(message);
    }
}
