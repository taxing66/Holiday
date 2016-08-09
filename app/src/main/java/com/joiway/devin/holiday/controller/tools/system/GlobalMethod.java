package com.joiway.devin.holiday.controller.tools.system;

import com.joiway.devin.holiday.tools.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by Administrator on 2016/8/6.
 * clazz:son superClass: parents
 * clazz:parents superClass:GrandParents
 * clazz:GrandParents superClass:Objects
 * clazz:Objects;
 */
public class GlobalMethod {
    public static Field[] getAllField(Class<?> clazz){
        if(clazz == null) return null;
        if(clazz.equals(Object.class)) return null;

        Class superClass = clazz.getSuperclass();
        Field[] superField = null;
        if(!clazz.equals(Object.class)){
            superField = getAllField(superClass);
        }
        Field[] fields = clazz.getDeclaredFields();

        if(superField != null){
            Field[] all = new Field[superField.length + fields.length];
            System.arraycopy(superField,0,all,0,superField.length);
            System.arraycopy(fields,0,all,superField.length,fields.length);
            return all;
        }
        return fields;
    }

    /**
     * 查找类的方法包括所有继承的类（Object除外）
     * @param clazz
     * @param methodName
     * @return
     * clazz son superClass parents
     * clazz parents superClass GrandParents
     * class GrandParents superClass Object
     * class Object
     * 找到父类的方法就弹出了
     *
     */
    public static Method findMethod(Class<?> clazz, String methodName, Class... parameterTypes){
        if(clazz == null) return null;
        if(clazz.equals(Object.class)) return null;
        if(methodName == null) return null;
        if(methodName.length() < 1) return null;

        Class superClass = clazz.getSuperclass();
        Method superField = null;
        if(!clazz.equals(Object.class)){
            superField = findMethod(superClass, methodName);
            if(superField != null) return superField;
        }
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            //检查 方法名
            if(method.getName().equals(methodName)){
               LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"GlobalMethod","findMethod","name:"+methodName);
                //检查 传参 是否一样
                Class[] pt = method.getParameterTypes();
                 LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"GlobalMethod","findMethod",":"+between2ArrayObject(pt,parameterTypes));
                if(between2ArrayObject(pt,parameterTypes))
                    return method;
            }
        }
        return null;
    }
    /**
     * 比较两个数组是否相等
     * @return
     */
    public static final boolean between2ArrayObject(Object[] array1, Object[] array2){
        if(array1 != null && array2 != null){
            if(array1.length == array2.length){
                for (int i = 0; i < array1.length; i++) {
                    if(!array1[i].equals(array2[i])) return false;
                }
                return true;
            }else{
                return false;
            }
        }else{
            return array1 == null && array2 == null;
        }
    }

    public static byte[] randomBytes(int count) {
        byte buff[] = new byte[count];
        Random r = new Random();
        r.nextBytes(buff);
        return buff;
    }
}
