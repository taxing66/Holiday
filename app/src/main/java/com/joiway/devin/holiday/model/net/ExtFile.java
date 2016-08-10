package com.joiway.devin.holiday.model.net;

import java.io.File;

/**
 * 文件扩展类
 *
 * @author 林佳楠
 * @create 2016-06-02
 * @editor 林佳楠
 * @date 2016-06-02
 * @docVersion 适用于代码规范v1.0.0 alpha版本
 */
public class ExtFile{

    public static final String TYPE_STRING_FILE_MEDIA_ALL = "*/*";
    public static final String TYPE_STRING_FILE_MEDIA_IMAGE = "image/*";
    public static final String TYPE_STRING_FILE_MEDIA_PNG = "image/png";
    public static final String TYPE_STRING_FILE_MEDIA_JPG = "image/jpeg";

    private String fileType;
    private File file;

    public ExtFile(File file, String fileType){
        this.file = file;
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
