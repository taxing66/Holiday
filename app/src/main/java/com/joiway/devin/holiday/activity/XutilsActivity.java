package com.joiway.devin.holiday.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.tools.ToastManager;
import com.joiway.devin.holiday.tools.ToastTool;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 *使用xutils3 布局定义属性的界面
 * @author 陈德华
 * @create 2016-08-15
 * @editer 陈德华
 * @date 2016-08-15
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
@ContentView(R.layout.activity_xutils)
public class XUtilsActivity extends AbsActivity {
    @ViewInject(R.id.btn_one)
    private Button btnOne;
    @ViewInject(R.id.et_two)
    private EditText etTwo;
    @ViewInject(R.id.ft_text)
    private Fragment mFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
    @Event(value = {R.id.btn_one},type = View.OnLongClickListener.class)
    private void onClick(View v){
        switch (v.getId()){
            case R.id.btn_one:
                ToastManager.showToastShort(this,"xUtil button one");
                break;
        }
    }
}
