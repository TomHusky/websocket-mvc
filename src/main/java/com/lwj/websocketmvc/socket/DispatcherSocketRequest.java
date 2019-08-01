package com.lwj.websocketmvc.socket;

import com.lwj.websocketmvc.socket.bean.SocketRequest;
import com.lwj.websocketmvc.socket.enumerate.IocContainer;
import com.lwj.websocketmvc.socket.util.DispatcherUtil;
import com.lwj.websocketmvc.socket.util.GsonUtil;
import com.lwj.websocketmvc.socket.util.MethodBean;
import com.lwj.websocketmvc.socket.util.RequestEntry;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: lwj
 * @ClassName: DispatcherSocketRequest
 * @Description: 用于处理webSocket的消息，分发给相对于的处理器
 */
public class DispatcherSocketRequest {

    /**
     * 请求分发
     */
    public static void dispatcher(String request, CustomerWebSocket socket) {
        try {
            //获取socket统一请求对象
            SocketRequest socketRequest = GsonUtil.jsonToBean(request, SocketRequest.class);
            String requestURI = socketRequest.getUrl();
            //获取对象
            Object obj = IocContainer.urlObjMap.get(requestURI);
            if (obj == null) {
                socket.sendMessage("请求地址不存在");
            } else {
                //获取请求方法
                Method method = IocContainer.methodMap.get(requestURI);
                //获取请求值
                Map<String, Object> paramNameValueMap = RequestEntry.fillParam(method, socketRequest.getData());
                MethodBean methodBean = new MethodBean(obj, method, paramNameValueMap);
                //获取响应的数据和类型
                Map<String, String> responseMap = DispatcherUtil.response(methodBean);
                String urlOrStr = responseMap.get("data");
                //返回数据
                socket.sendMessage(urlOrStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
