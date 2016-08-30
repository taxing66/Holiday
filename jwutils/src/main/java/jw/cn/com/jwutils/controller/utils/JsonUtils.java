package jw.cn.com.jwutils.controller.utils;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *JSON 处理工具类
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class JsonUtils {

    public static String toJsonStr(Object object){
        return JSON.toJSONString(object);
    }

    public static String toJsonStr(Map map){
        return JSON.toJSONString(map);
    }

    /**
     * 通过字符串映射成相应的json数据到相应的实体
     * @param content  字符内容
     * @param clz  实体类对象
     * @param <T> 实体类
     * @return  映射后的实体对象
     */
    public static<T>  T getJson(String content,Class<T> clz){
        return JSON.parseObject(content,clz);
    }

    /**
     * 把封装有网络请求入参的实体解析成map类型
     * @param object 实体字段变量类型
     * @return
     */
    public static final Map<String,Object> getFieldMap(Object object){
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
