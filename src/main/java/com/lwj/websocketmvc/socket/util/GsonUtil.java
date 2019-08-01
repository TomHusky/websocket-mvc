package com.lwj.websocketmvc.socket.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: SpringMVC
 * @Package: com.lwj.mvc.util
 * @ClassName: GsonUtil
 * @Author: lwj
 * @CreateDate: 2019/02/23 12:19
 * @UpdateDate: 2019/02/23 12:19
 * @Version: 1.0
 * @Description: gson装字符串
 */
public class GsonUtil {
    private static Gson gson =  new GsonBuilder()
            .setLenient()// json宽松
            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
            .serializeNulls() //智能null
            .disableHtmlEscaping() //默认是GSON把HTML 转义的
            .create();

    /**
     * 将object对象转成json字符串
     */
    public static String beanToJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * 将String转成泛型bean
     */
    public static <T> T jsonToBean(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }

    /**
     * json转map
     * StringToMap
     */
    public static <T> Map<String, T> jsonToMap(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
    }

    /**
     * map转json
     * MapToString
     */
    public static <T> String mapToJson(Map<String, T> map) {
        return gson.toJson(map);
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     */
    public static <T> List<T> jsonToList(String json) {
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * 转成list
     * 解决泛型问题
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成list中有map的
     */
    public static <T> List<Map<String, T>> jsonToListMap(String json) {
        return gson.fromJson(json, new TypeToken<List<Map<String, T>>>() {
        }.getType());
    }

    /**
     * 判断是否json
     */
    public static boolean isJson(String json) {
        try {
            new JsonParser().parse(json);
        } catch (JsonParseException e) {
            return false;
        }
        return true;
    }
}
