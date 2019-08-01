package com.lwj.websocketmvc.socket.util;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName: MethodBean
 * @Author: lwj
 */
public class MethodBean {

    //方法属于的对象
    private Object object;
    //方法本身
    private Method method;
    //方法的参数
    private Map<String,Object> map;

    public MethodBean() {
    }

    public MethodBean(Object object, Method method, Map<String,Object> map) {
        this.object = object;
        this.method = method;
        this.map = map;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String,Object> getMap() {
        return map;
    }

    public void setMap(Map<String,Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "MethodBean{" +
                "object=" + object +
                ", method=" + method +
                ", map=" + map +
                '}';
    }
}
