package jw.cn.com.jwutils.controller.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型辅助工具
 *
 * @author 林佳楠
 * @create 2016-06-01
 * @editor 林佳楠
 * @date 2016-06-01
 * @docVersion 适用于代码规范v1.0.0 alpha版本
 */
public class GenericUtil {

    /**
     * 获取泛型类的泛型参数类型
     *
     * @param classTarget 泛型类
     * @return 该泛型类的泛型参数Type数组
     */
    public static Type[] getParameterizedTypes(Class classTarget){
        Type superClass = classTarget.getGenericSuperclass();
        if(superClass instanceof ParameterizedType){
            return ((ParameterizedType) superClass).getActualTypeArguments();
        }else{
            throw new IllegalArgumentException("不支持获取泛型的类型:" + classTarget.getName());
        }
    }

    /**
     * 打印泛型类泛型参数信息
     * @param obj 打印对象
     */
    public static void doPrintClassInfo(Object obj) {
        doPrintClassInfo(obj.getClass().getGenericSuperclass());
    }

    /**
     * 打印泛型类泛型参数信息
     * @param classRequest 打印Type
     */
    public static void doPrintClassInfo(Type classRequest) {
        if (classRequest instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) classRequest).getActualTypeArguments();
            for (Type type : types) {
                doPrintClassInfo(type);
            }
        } else {
        }
    }
}
