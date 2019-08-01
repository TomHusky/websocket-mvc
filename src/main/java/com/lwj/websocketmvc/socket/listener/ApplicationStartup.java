package com.lwj.websocketmvc.socket.listener;

import com.lwj.websocketmvc.exception.ControllerInvalidException;
import com.lwj.websocketmvc.socket.annotation.SocketController;
import com.lwj.websocketmvc.socket.annotation.SocketRequestMapping;
import com.lwj.websocketmvc.socket.enumerate.IocContainer;
import com.lwj.websocketmvc.socket.util.ClassUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lwj
 * @ClassName: ApplicationStartup
 * @Description: spring容器初始化监听类
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${web.socket.mvc.controller.package}")
    private String basePackage;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            //2.初始化控制器Bean
            initController();
            //3.初始化映射器
            handlerMapping();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控制器
     */
    private void initController() throws IllegalAccessException, InstantiationException {
        //扫包
        if (basePackage == null) {
            throw new RuntimeException("请在properties文件中添加扫包范围,名称为web.socket.mvc.controller.package");
        } else {
            List<Class<?>> classList = ClassUtil.getClasses(basePackage);
            for (Class clazz : classList) {
                Annotation controller = clazz.getDeclaredAnnotation(SocketController.class);
                if (controller != null) {
                    Object o = clazz.newInstance();
                    String name = toLowerCaseFirstOne(clazz.getSimpleName());
                    IocContainer.objMap.put(name, o);
                }
            }
        }
    }

    /**
     * 处理器映射
     */
    private void handlerMapping() {
        for (Map.Entry entry : IocContainer.objMap.entrySet()) {
            Object controller = entry.getValue();
            String oneUrl = "/";

            Class<?> clazz = controller.getClass();
            SocketRequestMapping requestMapping = clazz.getDeclaredAnnotation(SocketRequestMapping.class);
            //判断类上是否加入RequestMapping注解
            if (requestMapping != null) {
                String value = requestMapping.value();
                //判断RequestMapping中的路径是否有加 "/ "
                if (!value.startsWith("/")) {
                    oneUrl = oneUrl + requestMapping.value();
                } else {
                    oneUrl = requestMapping.value();
                }
            }
            //获取控制器上所有方法
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                SocketRequestMapping mRequestMapping = method.getDeclaredAnnotation(SocketRequestMapping.class);
                if (mRequestMapping != null) {
                    if ("".equals(mRequestMapping.value().trim())) {
                        throw new ControllerInvalidException("方法映射地址不能为空");
                    }
                    String url = oneUrl + mRequestMapping.value();
                    IocContainer.methodMap.put(url, method);
                    IocContainer.urlObjMap.put(url, controller);
                    //保存方法参数
                    saveMethodParam(method);
                }
            }
        }
    }

    /**
     * 获取方法的参数名和类型,只允许方法有一个参数
     */
    private void saveMethodParam(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length != 1) {
            throw new ControllerInvalidException("requestMapping修饰的方法有且只能有一个参数");
        }
        Parameter parameter = parameters[0];
        Map<String, Class> map = new HashMap<>(1);
        map.put(parameter.getName(), parameter.getType());
        IocContainer.methodParamMap.put(method, map);
    }

    /**
     * 首字母转小写
     */
    private String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
}
