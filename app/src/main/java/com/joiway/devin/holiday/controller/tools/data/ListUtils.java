package com.joiway.devin.holiday.controller.tools.data;

import java.util.List;

/**
 * Created by 潘阳君 on 2015/12/29.
 * 【豆浆框架】-【工具集】
 */
public class ListUtils {
    /**
     * 删除List的Item
     * @param src
     * @param start
     * 开始删除的位置
     * @param end
     * 停止删除的位置
     * @deprecated
     */
    @Deprecated
    public static final void delList(List src, int start, int end){
        if(start >= end || start >= src.size() || end > src.size() ||start < 0 || end < 0) return;

        int count = end - start;
        for (int i = start; i <count; i++) {
            src.remove(i);
        }
    }

    /**
     * 比较两个数组是否相等
     * @return
     */
    public static final boolean between2ArrayObject(Object[] array1, Object[] array2){
        if(array1 != null && array2 != null){
            if(array1.length == array2.length){
                for (int i = 0; i < array1.length; i++) {
                    if(!array1.equals(array2)) return false;
                }
                return true;
            }else{
                return false;
            }
        }else{
            return array1 == null && array2 == null;
        }
    }
}