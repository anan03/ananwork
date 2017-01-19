package com.lvshandian.partylive.utils;

import com.alibaba.fastjson.JSON;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.bean.CustomdateBean;
import com.tandong.sa.json.Gson;
import com.tandong.sa.json.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sll on 2016/11/24.
 */

/**
 * 把Map转成javaBean的工具类
 *
 * @author sll
 * @time 2016/11/24 13:32
 */
public class JavaBeanMapUtils {
    public static <T> T mapToBean(Map map, Class<T> clazz) {
        return JSON.parseObject(new JSONObject(map).toString(), clazz);
    }

    /**
     * 将实体类转为json串，再转成Map
     *
     * @author sll
     * @time 2016/11/28 16:20
     */
    public static Map beanToMap(AppUser appUser) {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        JSONObject jsonObject;
        //这两句代码必须的，为的是初始化出来gson这个对象，才能拿来用
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            jsonObject = new JSONObject(gson.toJson(appUser, AppUser.class));
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogUtils.e("HttpClientUtils", e.toString());
        }
        return null;
    }

}
