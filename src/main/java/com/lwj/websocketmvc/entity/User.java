package com.lwj.websocketmvc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: lwj
 * @ClassName: User
 * @Description: 用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User {
    private String userName;
    private String passWord;
}
