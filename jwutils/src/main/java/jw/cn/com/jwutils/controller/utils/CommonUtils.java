package jw.cn.com.jwutils.controller.utils;

/**
 * 拥有常用数据操作的工具类
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class CommonUtils {

    /**
     * 比较两个数组是否相等
     * @return
     */
    public static final boolean compareToArray(Object[] array1, Object[] array2){
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
}
