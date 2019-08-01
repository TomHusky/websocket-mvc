package com.lwj.websocketmvc.socket.enumerate;


import com.lwj.websocketmvc.socket.util.GsonUtil;
import com.lwj.websocketmvc.util.ApiResult;
import com.lwj.websocketmvc.util.ApiResultUtil;

/**
 * @Author: lwj
 * @ClassName: WebSocketCode
 */
public class WebSocketCode {
    private WebSocketCode(){

    }
    public static String connectSuccess(){
        ApiResult<String> success = ApiResultUtil.success("连接成功");
        return  GsonUtil.beanToJson(success);
    }
}
