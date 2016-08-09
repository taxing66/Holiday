package com.joiway.devin.holiday.controller.tools.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * 用途描述
 * 视图的工具
 *
 * @author 潘阳君
 * @create 2016/3/16
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class ViewUtils {
    public static final void removeSelfFromParent(View self){
        ViewParent parent = self.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(self);
        }
    }
}
