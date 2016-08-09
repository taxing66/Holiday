package com.joiway.devin.holiday.controller.tools.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.joiway.devin.holiday.controller.tools.data.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 潘阳君 on 2015/12/23.
 * 【豆浆框架】-【库层】
 * 【GSON模块】
 */
public class JsonUtils {

    public static final <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json,clazz);
    }

    public static final <T> List<T> fromJsonList(String json, Class<T> clazz) {
        return JSON.parseArray(json,clazz);
    }

    public static final Map<String, Object> fromJson(String json) {
        return JSON.parseObject(json, new TypeReference<HashMap<String, Object>>(){});
    }

    public static final String toJsonString(Map map){
        return JSON.toJSONString(map);
    }

    public static final String toJsonString(Object src) {
        return JSON.toJSONString(src);
    }

    public static final <T> T fromMap(Map map, Class<T> clazz){
        String json = toJsonString(map);
        return fromJson(json, clazz);
    }
    public static final <T> T fromMap(Map map, TypeReference<T> types){
        String json = toJsonString(map);
        return JSON.parseObject(json,types);
    }
    public static final Map<String,Object> toMap(Object object){
        Field[] fields = ReflectionUtils.getAllField(object.getClass());

        Map<String,Object> map = new HashMap<>();
        Object value;

        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            try{
                value = field.get(object);
            }catch (Exception e){
                e.printStackTrace();
                value=null;
            }
            if (value instanceof File) {
                map.put(name, ((File)value).getAbsolutePath());
            }else{
                map.put(name,value);
            }

            field.setAccessible(false);
        }

        return map;
    }


}
