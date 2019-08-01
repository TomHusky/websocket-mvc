package com.lwj.websocketmvc.socket.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lwj
 * @Description: 分发帮助类
 */
public class DispatcherUtil {

    /**
     * 用于分发
     *
     * @param methodBean
     * @return
     */
    public static Map<String, String> response(MethodBean methodBean) throws InvocationTargetException, IllegalAccessException {
        HashMap<String, String> responseMap = new HashMap<>(1);
        Method method = methodBean.getMethod();
        Object object = methodBean.getObject();
        //获取方法参数
        Object[] param = getParam(methodBean.getMap());
        //执行请求方法获取执行结果
        Object invoke = runMethod(method, object, param);
        responseMap.put("data", GsonUtil.beanToJson(invoke));
        return responseMap;
    }

    private static Object runMethod(Method method, Object object, Object[] param) throws InvocationTargetException, IllegalAccessException {
        Object invoke = null;
        if (param != null && param.length > 0) {
            invoke = method.invoke(object, param);
        } else {
            invoke = method.invoke(object);
        }
        return invoke;
    }

    private static Object[] getParam(Map<String, Object> map) {
        if (map == null || map.size() < 1) {
            return null;
        }
        List<Object> values = new ArrayList<>(map.values());
        return values.toArray();
    }
}
