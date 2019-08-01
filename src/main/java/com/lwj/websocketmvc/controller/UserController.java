package com.lwj.websocketmvc.controller;


import com.lwj.websocketmvc.entity.User;
import com.lwj.websocketmvc.request.UserLoginRequest;
import com.lwj.websocketmvc.socket.annotation.SocketController;
import com.lwj.websocketmvc.socket.annotation.SocketRequestMapping;
import com.lwj.websocketmvc.util.ApiResult;
import com.lwj.websocketmvc.util.ApiResultUtil;

/**
 * @Author: lwj
 * @Package: com.lwj.websocketmvc
 * @ClassName: UserController
 * @Description: 用户控制器
 */
@SocketController
@SocketRequestMapping("user")
public class UserController {

    @SocketRequestMapping("/login")
    public ApiResult login(UserLoginRequest userLoginRequest){

        return ApiResultUtil.success("hello");
    }

    @SocketRequestMapping("/getUser")
    public ApiResult getUser(UserLoginRequest userLoginRequest){
        //响应数据
        User user = new User().setUserName(userLoginRequest.getUserName()).setPassWord("123456");
        return ApiResultUtil.success(user);
    }
}
