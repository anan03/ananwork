package com.lvshandian.menshen.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;


/**
 * json数据解析工具，可防止数据结构与Json串不匹配导致的异常崩溃，依赖FastJson
 * 如果要解析的json数据根元素是一个对象，则使用{@link #json2Bean(String, Class)}
 * 如果要解析的json数据根元素是一个集合，则使用{@link #json2BeanList(String, Class)}
 */
public class JsonUtil {

    /**
     * 把json字符串转换为JavaBean
     *
     * @param json      json字符串
     * @param beanClass JavaBean的Class
     * @return
     */
    public static <T> T json2Bean(String json, Class<T> beanClass) {
        T bean = null;
        try {
            bean = JSON.parseObject(json, beanClass);
        } catch (Exception e) {
            Log.i("JsonUtil", "解析json数据时出现异常json = " + json, e);
        }
        return bean;
    }

    /**
     * 把json字符串转换为JavaBean。如果json的根节点就是一个集合，则使用此方法
     * 集合元素类的clazz文件
     *
     * @param json      json字符串
     * @param beanClass 集合元素类的clazz文件
     * @return T类的集合
     */
    public static <T> List<T> json2BeanList(String json, Class<T> beanClass) {
        List<T> tList = null;
        try {
            tList = JSON.parseArray(json, beanClass);
        } catch (Exception e) {
            Log.i("JsonUtil", "解析json数据时出现异常json = " + json, e);
        }
        return tList;
    }

    /**
     * 将对象序列化为Json字符
     *
     * @param obj 对象
     * @return String Json字符
     */
    public static final String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty);
    }
}
