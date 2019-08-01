package com.lwj.websocketmvc.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: lwj
 * @ClassName: UserLoginRequest
 * @Description: 用户登录的请求类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLoginRequest {

    private String userName;
}
