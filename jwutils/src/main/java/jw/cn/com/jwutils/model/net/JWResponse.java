package jw.cn.com.jwutils.model.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *网络请求响应数据
 * @author 陈德华
 * @create 2016-09-01
 * @editer 陈德华
 * @date 2016-09-01
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class JWResponse {
    private JWHeader header;
    private String chartSet = "utf-8";
    private String strResult;
    private InputStream inputStream;

    public JWHeader getHeader() {
        return header;
    }

    public void setHeader(JWHeader header) {
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

    public String getStrResult() throws Exception{
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
