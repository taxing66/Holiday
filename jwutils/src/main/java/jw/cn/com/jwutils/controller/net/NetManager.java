package jw.cn.com.jwutils.controller.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.LogManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jw.cn.com.jwutils.Config;
import jw.cn.com.jwutils.controller.interfaces.ICallbackBase;
import jw.cn.com.jwutils.controller.utils.CrypUtils;
import jw.cn.com.jwutils.controller.utils.FileUtils;
import jw.cn.com.jwutils.controller.utils.JsonUtils;
import jw.cn.com.jwutils.controller.utils.LogUtils;
import jw.cn.com.jwutils.controller.utils.ReflectionUtils;
import jw.cn.com.jwutils.controller.utils.SSLUtils;
import jw.cn.com.jwutils.model.bean.NetIncomingParameterBean;
import jw.cn.com.jwutils.model.bean.NetOutputParameterBean;

/**
 * the application net request of manager
 *
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class NetManager extends NetBase {
    private static NetManager sNetManager;

    private NetManager() {

    }

    public static NetManager getInstance() {
        if (sNetManager == null) {
            synchronized (NetManager.class) {
                if (sNetManager == null) {
                    return new NetManager();
                }
            }
        }
        return sNetManager;
    }

    public  abstract class NetCallback<Entity> implements ICallbackBase<Entity> {

    }

    /**
     * post方法
     *
     * @param url    访问的方法
     * @param params 访问实体数据
     * @param callback    服务器返回实体数据
     * @return
     */
    public static <B extends NetOutputParameterBean> Callback.Cancelable httpPost(String url, Object params, NetCallback<B> callback) throws Throwable {
        return getInstance().doHttp(Method.POST, url, params, callback);
    }

    /**
     * post方法
     *
     * @param url    访问的方法
     * @param params 访问实体数据
     * @param callback    服务器返回实体数据
     * @return
     */
//    public static <B extends NetOutputParameterBean> Callback.Cancelable httpsPost(String url, Object params, NetCallback<B> callback) throws Throwable {
//        return getInstance().doHttps(Method.POST, url, params, callback, SSLUtils.getSSLSocketFactory(E.getHttpsKey(), E.getHttpsTruststore(), E.mHttpsKeyPwd));
//    }

    /**
     * post获取File同步方法,有在请求前入参AES加密
     *
     * @param url    访问的方法
     * @param params 访问实体数据
     * @return
     */
    public static File downloadHttpPostSync(String url, Object params) throws Throwable {
        return getInstance().doHttpSync(Method.POST, url, getNetIncomingParameterBean(params), File.class);
    }

    /**
     * post同步方法 出参进行AES解密
     *
     * @param url    访问的方法
     * @param params 访问实体数据
     * @param clz    服务器返回实体数据
     * @return
     */
    public static <B extends NetOutputParameterBean> NetOutputParameterBean httpPostSync(String url, Object params, TypeReference<B> clz) throws Throwable {
        String jsonStr = getInstance().doHttpSync(Method.POST, url, getNetIncomingParameterBean(params), String.class);
        return getNetOutputParameterBean(jsonStr, clz);
    }

    /**
     * post同步方法，上传文件时的请求
     *
     * @param url    访问的方法
     * @param params 访问实体数据
     * @param clz   服务器返回实体数据
     * @return
     */
    public static <B extends NetOutputParameterBean> NetOutputParameterBean httpPostSync(String url, Object params, File file, TypeReference<B> clz) throws Throwable {
        String jsonStr = getInstance().doHttpSync(Method.POST, url, getNetIncomingParameterBean(params,file), String.class);
        return getNetOutputParameterBean(jsonStr, clz);
    }

    /**
     * 获取网络响应出参实体
     * @param responseStr
     * @param clz
     * @param <B>
     * @return
     */
    private static <B extends NetOutputParameterBean> NetOutputParameterBean getNetOutputParameterBean(String responseStr, TypeReference<B> clz) {
        String jsonStr;
        try {
            if (!TextUtils.isEmpty(responseStr)) {
                jsonStr = CrypUtils.decryptAes(responseStr);
                return JSON.parseObject(jsonStr, clz);
            }
        } catch (Exception e) {
             LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"NetManager","getNetOutputParameterBean","service back decryption fail."+e);
        }
        return null;
    }

    /**
     * 获取加密的网络接口访问实体
     * params 中的file 可以不用添加进去，另外携带在file字段上
     *
     * @param params
     * @return
     */
    private static NetIncomingParameterBean getNetIncomingParameterBean(Object params) {
        String json = JsonUtils.toJsonStr(JsonUtils.getFieldMap(params));
        try {
            NetIncomingParameterBean accessBean = new NetIncomingParameterBean();
            accessBean.setParams(CrypUtils.encryptAes(json));
            String sm = toSM(accessBean);
            accessBean.setSm(sm);
            return accessBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取加密的网络接口访问实体
     *
     * @param params
     * @return
     */
    private static NetIncomingParameterBean getNetIncomingParameterBean(Object params, File file) {
        String json = JsonUtils.toJsonStr(JsonUtils.getFieldMap(params));
        NetIncomingParameterBean accessBean = new NetIncomingParameterBean();
        try {
            accessBean.setParams(CrypUtils.encryptAes(json));
            String sm = toSM(accessBean);
            accessBean.setSm(sm);
            accessBean.setFile(file);
            return accessBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加SM计算
     *
     * @param params
     * @return
     */
    private static <P> String toSM(P params) {
        ArrayList<String> parameterList = new ArrayList<>();

        Field[] fields = ReflectionUtils.getAllField(params.getClass());

        //设值
        for (Field field : fields) {
            String name = field.getName();
            if (!name.equals("sm") && !name.equals("file")) {
                parameterList.add(name);
            }
        }
        return toSM(params, parameterList);
    }

    public static <P> String toSM(P params, ArrayList<String> paramName) {
        return toSM(params, paramName, Config.KEY_STRING_SMKey);
    }

    /**
     * @param params
     * @param paramName
     * @param x         计算SM 的因子
     * @return
     */
    private static <P> String toSM(P params, ArrayList<String> paramName, String x) {
        String smValue = "";

        Class pClass = params.getClass();

        //排序计算
        Collections.sort(paramName);
        for (String name : paramName) {
            Object value;
            try {
                Field field = ReflectionUtils.findFieldWithLoopThrough(pClass, name);
                if (field == null)
                    throw new NoSuchFieldException(pClass.getName() + " 找不到字段：" + name);

                field.setAccessible(true);
                value = field.get(params);

                if (value == null) continue;

                if (value instanceof File) {
                    String md5;
                    try {
                        md5 = CrypUtils.md5(FileUtils.getBytes((File) value));
                    } catch (Exception e) {
                        e.printStackTrace();
                        md5 = "0";
                    }
                    smValue += md5;
                } else {

                    //如果类型是 基本类型 则转成String
                    if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                        smValue += String.valueOf(value);
                    } else {
                        //如果是 其他Object类型
                        smValue += JsonUtils.toJsonStr(value);
                    }
                }
                field.setAccessible(false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String s1 = getEgNum(smValue);
        try {
            s1 = CrypUtils.md5((s1 + x).getBytes());//密码因子，增加破解难度
            return CrypUtils.md5(s1.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getEgNum(String s) {
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m1 = p.matcher(s);
        return m1.replaceAll("").trim();
    }

    @Override
    protected <P> void readyToProcess(Method method, String URL, P params, ICallbackBase callback) {

    }

    @Override
    protected <B> B successProcess(String URL, String serverBack, Class<B> entityClass, ICallbackBase callback) {
        return null;
    }

    @Override
    protected void faildProcess(Throwable exception, ICallbackBase callback) {

    }
}
