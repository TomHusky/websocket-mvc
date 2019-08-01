package com.lwj.websocketmvc.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Api统一返回结果
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ApiResult<T> implements Serializable {

    private int code;

    private String message;

    private T data;
}
