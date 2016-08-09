/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joiway.devin.holiday.controller.tools.file;
import com.joiway.devin.holiday.tools.LogManager;
import com.joiway.lib.base.cryptolib.CryptoNativeLib;

import java.io.File;

/**
 * 用途描述
 * 文件加密打包器
 *
 * @author 潘阳君
 * @create 2016/6/28
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class PickUpper implements Runnable, ZipUtils.ZipListener {

    public static final String TAG = "PickUpper";

    private boolean isRunning;
    private File pickTarget;
    private File saveTarget;
    private String header;
    private int version;

    public PickUpper() {
        isRunning = false;
    }

    public File startPickSync() {
        return readyPick();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onZipping(String entryName, int total, int value) {
    }

    @Override
    public void onFinish(File zipFile) {
    }

    @Override
    public void onError(Exception e) {
        LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "PickUpper", "onError", "onError: zip file fail" + e);
    }

    public void setPickTarget(File pickTarget) {
        this.pickTarget = pickTarget;
    }

    public void setSaveTarget(File saveTarget) {
        this.saveTarget = saveTarget;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void run() {

        if (isRunning) {
            return;
        }

        isRunning = true;

        readyPick();

        isRunning = false;
    }

    private File readyPick() {

        File saveParent = saveTarget.getParentFile();
        //建立 保存目标 的父文件夹
        if (saveParent != null) {
            saveParent.mkdirs();
        }
        return pick();
    }

    private File pick() {
        if (ZipUtils.isZipFile(pickTarget)) {
            //不需要zip压缩
            if (FileUtils.fileCopyFile(pickTarget, saveTarget)) {
                //直接加密
                return cryptFile(saveTarget);
            } else {
                LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "PickUpper", "pick", "pick: copy file fail");
            }
        } else {
            //需要zip压缩
            return cryptFile(ZipUtils.toZip(pickTarget, saveTarget, this));
        }
        return null;
    }

    private File cryptFile(File target) {
        if (target != null && CryptoNativeLib.fileAesEncrypt(target.getAbsolutePath(), version, header.getBytes())) {
            return target;
        } else {
            LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "PickUpper", "cryptFile", "pick: crypt zip file fail"
            );
        }
        return null;
    }
}
