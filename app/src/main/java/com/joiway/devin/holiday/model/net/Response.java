package com.joiway.devin.holiday.model.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 网络请求响应数据
 *
 * @author 林佳楠
 * @create 2016-06-02
 * @editor 林佳楠
 * @date 2016-06-02
 * @docVersion 适用于代码规范v1.0.0 alpha版本
 */
public class Response {
    private Header header;
    private String chartSet = "utf-8";
    private String strResult;
    private InputStream inputStream;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getChartSet() {
        return chartSet;
    }

    public void setChartSet(String chartSet) {
        this.chartSet = chartSet;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getStrResult() throws Exception {
        if(strResult != null){
            return strResult;
        }
        if(inputStream == null){
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, chartSet));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            strResult = sb.toString();
            return strResult;
        } finally {
            inputStream.close();
        }
    }
}
