package com.joiway.devin.holiday.controller.tools.net;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * 用途描述
 * 数据处理的通用工具
 *
 * @author 潘阳君
 * @create 2016/4/12
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class DataUtils {
    public static final String TAG = "DataUtils";
    /**
     * 拷贝一份可序列化对象
     * @param data
     * @return
     */
    public static final Object copyObject(Serializable data) throws NotSerializableException,ClassNotFoundException,InvalidClassException,StreamCorruptedException,OptionalDataException {
        try{
            byte[] buffer = objectToBytes(data);
            if (buffer != null && buffer.length > 0) {
                return bytesToObject(buffer);
            }
            return null;
        }catch(IOException e){
            e.printStackTrace();
            Log.e(TAG, "copyObject:" + e.toString(),e);
            return null;
        }
    }

    /**
     * 将 可序列化对象 导出字节
     * @param data
     * @return
     * @throws NotSerializableException
     */
    public static final byte[] objectToBytes(Serializable data) throws NotSerializableException {
        try{
            ByteArrayOutputStream objectBuffer = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(objectBuffer);
            oout.writeObject(data);
            oout.close();
            return objectBuffer.toByteArray();
        }catch(Exception e){

            e.printStackTrace();
            Log.e(TAG, "objectToBytes:" + e.toString(),e);

            if(e instanceof NotSerializableException){
                throw (NotSerializableException)e;
            }
            return null;
        }
    }

    public static final Object bytesToObject(byte[] data) throws ClassNotFoundException,InvalidClassException,StreamCorruptedException,OptionalDataException {
        try{
            ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
            Object copy = oin.readObject();
            oin.close();
            return copy;
        }catch(IOException e){
            e.printStackTrace();
            Log.e(TAG, "bytesToObject:" + e.toString(),e);
            return null;
        }
    }
}
