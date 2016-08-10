package com.joiway.devin.holiday.controller.tools.data;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by 潘阳君 on 2015/12/25.
 * 【豆浆框架】-【工具集】
 * 【反射工具】
 */
public class ReflectionUtils {
    /**
     * 获取所有父类字段，但除Object外
     * @param clazz
     * @return
     */
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
     * 查找类的字段包括所有继承的类，使用默认方法
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field findField(Class<?> clazz, String fieldName){
        if(clazz == null) return null;
        if(clazz.equals(Object.class)) return null;
        if(fieldName == null) return null;
        if(fieldName.length() < 1) return null;

        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            return clazz.getField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查找类的字段包括所有继承的类（Object除外），使用遍历查找
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field findFieldWithLoopThrough(Class<?> clazz, String fieldName){
        if(clazz == null) return null;
        if(clazz.equals(Object.class)) return null;
        if(fieldName == null) return null;
        if(fieldName.length() < 1) return null;

        Class superClass = clazz.getSuperclass();
        Field superField = null;
        if(!clazz.equals(Object.class)){
            superField = findFieldWithLoopThrough(superClass, fieldName);
            if(superField != null) return superField;
        }
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if(field.getName().equals(fieldName)) return field;
        }
        return null;
    }

    /**
     * 查找类的方法包括所有继承的类（Object除外）
     * @param clazz
     * @param methodName
     * @return
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

                //检查 传参
                Class[] pt = method.getParameterTypes();
                if(ListUtils.between2ArrayObject(pt,parameterTypes))
                    return method;
            }
        }
        return null;
    }

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
     * 获取 泛型类
     * @param ownerType
     * 被封装的Class
     * @param declaredClass
     * 源生Class
     * @param paramIndex
     *
     * @return
     */
    public static Type getParameterizedType(

            final Type ownerType,
            final Class<?> declaredClass,
            int paramIndex) {

        Class<?> clazz = null;
        ParameterizedType pt = null;
        Type[] ats = null;
        TypeVariable<?>[] tps = null;
        if (ownerType instanceof ParameterizedType) {
            pt = (ParameterizedType) ownerType;
            clazz = (Class<?>) pt.getRawType();
            ats = pt.getActualTypeArguments();
            tps = clazz.getTypeParameters();
        } else {
            clazz = (Class<?>) ownerType;
        }
        if (declaredClass == clazz) {
            if (ats != null) {
                return ats[paramIndex];
            }
            return Object.class;
        }

        Type[] types = clazz.getGenericInterfaces();
        if (types != null) {
            for (int i = 0; i < types.length; i++) {
                Type t = types[i];
                if (t instanceof ParameterizedType) {
                    Class<?> cls = (Class<?>) ((ParameterizedType) t).getRawType();
                    if (declaredClass.isAssignableFrom(cls)) {
                        try {
                            return getTrueType(getParameterizedType(t, declaredClass, paramIndex), tps, ats);
                        } catch (Throwable ignored) {
                        }
                    }
                }
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            if (declaredClass.isAssignableFrom(superClass)) {
                return getTrueType(
                        getParameterizedType(clazz.getGenericSuperclass(),
                                declaredClass, paramIndex), tps, ats);
            }
        }

        throw new IllegalArgumentException("FindGenericType:" + ownerType +
                ", declaredClass: " + declaredClass + ", index: " + paramIndex);

    }


    private static Type getTrueType(

            Type type,
            TypeVariable<?>[] typeVariables,
            Type[] actualTypes) {

        if (type instanceof TypeVariable<?>) {
            TypeVariable<?> tv = (TypeVariable<?>) type;
            String name = tv.getName();
            if (actualTypes != null) {
                for (int i = 0; i < typeVariables.length; i++) {
                    if (name.equals(typeVariables[i].getName())) {
                        return actualTypes[i];
                    }
                }
            }
            return tv;
            // }else if (type instanceof Class<?>) {
            // return type;
        } else if (type instanceof GenericArrayType) {
            Type ct = ((GenericArrayType) type).getGenericComponentType();
            if (ct instanceof Class<?>) {
                return Array.newInstance((Class<?>) ct, 0).getClass();
            }
        }
        return type;
    }

    /**
     * 检查类是否有实现接口
     * @return
     */
    public static boolean hasImplInterface(Class src, Class interfaze){
        if (src != null && interfaze != null) {
            //得到该类所有实现的接口
            Class[] interfaces = src.getInterfaces();
            if (interfaces != null) {
                for(Class infc:interfaces){
                    if(interfaze.equals(infc)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
