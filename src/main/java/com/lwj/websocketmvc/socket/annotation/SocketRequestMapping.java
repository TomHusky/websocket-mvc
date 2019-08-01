package com.lwj.websocketmvc.socket.annotation;

import java.lang.annotation.*;

/**
 * @Author: lwj
 * @ClassName: SocketRequestMapping
 * @Description: 用于处理Socket请求的注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketRequestMapping {
    String value() default "";

    String method() default "";
}
