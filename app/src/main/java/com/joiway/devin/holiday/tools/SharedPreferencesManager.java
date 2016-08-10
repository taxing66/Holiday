package com.joiway.devin.holiday.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 1/  editor
 * ['edɪtə]  英['edɪtə]  美['ɛdɪtɚ]
 * <p/>
 * n. 编者，编辑；社论撰写人；编辑装置
 * <p/>
 * 网络释义:
 * Editor - 编辑; 编辑器; 总编辑
 * text editor - 文本编辑器; 文字编辑器; 文本编辑
 * Graph Editor - 图形编辑器; 曲线编辑器; 图表编辑器
 * <p/>
 * 2/preferences
 * ['pref(ə)r(ə)nsɪz]  英['pref(ə)r(ə)nsɪz]  美['prɛfrənsɪz]
 * <p/>
 * n. 参数选择（preference的复数）；选择权
 * <p/>
 * 网络释义:
 * Preferences - 预设; 参数选择; 首选项
 * General Preferences - 一般设置; 常规设置; 一般设定
 * Contact Preferences - 联络资料设定; 这里可以变更你的个人资料以及联络方式等等; 联络设定
 * <p/>
 * SharedPreferences 管理工具类
 *
 * @author 陈德华
 * @create 2016-06-30
 * @editer 陈德华
 * @date 2016-06-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class SharedPreferencesManager {
    private static final String SHARE_PREFERENCE_XML_FILE_NAME ="holiday_share_preference";
    private static SharedPreferencesManager mSharedPreferencesManager;
    private SharedPreferences mSharedPreferences;
    private  SharedPreferences.Editor mEditor;
    private Context mContext;

    /**
     * 构造器私有化
     */
    private SharedPreferencesManager(){
    }

    public SharedPreferencesManager(Context applicationContext) {
        this.mContext = applicationContext;
        this.mSharedPreferences = applicationContext.getSharedPreferences(SHARE_PREFERENCE_XML_FILE_NAME,Context.MODE_PRIVATE);
        this.mEditor = mSharedPreferences.edit();
    }

    /**
     * 单例初始化
     *
     * @param ctx
     * @return
     */
    public static SharedPreferencesManager getInstance(Context ctx) {
        if (mSharedPreferencesManager == null) {
            synchronized (SharedPreferencesManager.class) {
                if (mSharedPreferencesManager == null) {
                    mSharedPreferencesManager = new SharedPreferencesManager(ctx.getApplicationContext());
                }
            }
        }
        return mSharedPreferencesManager;
    }

    /**
     * 获取 sharePreference 的编辑器
     * 适合同时提交多个轻易级文件的时候
     * @return
     */
    public  SharedPreferences.Editor put(){
        return mEditor;
    }

    /**
     * 封裝后不需要調用commit
     * @param key
     * @param value
     * @return
     */
   public SharedPreferencesManager put(String key,String value){
       mEditor.putString(key,value);
       mEditor.commit();
       return this;
   }

    /**
     * 封裝后不需要調用commit
     * @param key
     * @param value
     * @return
     */
    public SharedPreferencesManager put(String key,boolean value){
        mEditor.putBoolean(key,value);
        mEditor.commit();
        return this;
    }

    /**
     * 封裝后不需要調用commit
     * @param key
     * @param value
     * @return
     */
    public SharedPreferencesManager put(String key,int value){
        mEditor.putInt(key,value);
        mEditor.commit();
        return this;
    }

    /**
     * 封裝后不需要調用commit
     * @param key
     * @param value
     * @return
     */
    public SharedPreferencesManager put(String key,float value){
        mEditor.putFloat(key,value);
        mEditor.commit();
        return this;
    }

    /**
     * 封裝后不需要調用commit
     * @param key
     * @param value
     * @return
     */
    public SharedPreferencesManager put(String key,long value){
        mEditor.putLong(key,value);
        mEditor.commit();
        return this;
    }


    /**
     * 获取得到 sharePreference 实例,通过些实例去拿相应的值；
     * 可能自己传得不到数据时的初始化数据
     * @return
     */
    public SharedPreferences getSpf(){
        return mSharedPreferences;
    }

    /**
     * 默认值为空的取字符串方法
     * @param key
     * @return
     */
    public String getString(String key){
        return mSharedPreferences.getString(key,"");
    }

    /**
     * @param key
     * @return
     */
    public boolean getBoolean(String key){
        return mSharedPreferences.getBoolean(key,false);
    }

    public int getInt(String key){
        return mSharedPreferences.getInt(key,-1);
    }

    public float getFloat(String key){
        return mSharedPreferences.getFloat(key,-1);
    }

    public  long getLong(String key){
        return mSharedPreferences.getLong(key,-1);
    }

}
