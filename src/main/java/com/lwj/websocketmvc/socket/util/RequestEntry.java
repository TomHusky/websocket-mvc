package com.lwj.websocketmvc.socket.util;


import com.lwj.websocketmvc.socket.enumerate.IocContainer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RequestEntry
 * @Author: lwj
 * @Description: 请求入参
 */
public class RequestEntry {

    /**
     * 方法参数值获取
     */
    public static Map<String, Object> fillParam(Method method, Object data) {
        if (method == null) {
            return null;
        }
        HashMap<String, Object> paramNameValueMap = new HashMap<>(1);
        for (Map.Entry<String, Class> entry : IocContainer.methodParamMap.get(method).entrySet()) {
            Class paramType = entry.getValue();
            String paramName = entry.getKey();
            //将json数据转成Object对象
            Object bean = GsonUtil.jsonToBean(data.toString(), paramType);
            paramNameValueMap.put(paramName, bean);
        }
        return paramNameValueMap;
    }
}
