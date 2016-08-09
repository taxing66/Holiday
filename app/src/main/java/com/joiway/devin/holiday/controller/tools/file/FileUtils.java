package com.joiway.devin.holiday.controller.tools.file;

import android.text.TextUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 用途描述
 * 文件工具
 *
 * @author 潘阳君
 * @create 2016/6/27
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class FileUtils {
    public static final int TYPE_INT_FIND_FLAG_FILE = 1;
    public static final int TYPE_INT_FIND_FLAG_DIR = 1<<8;

    /**
     * 删除 文件或文件夹
     * 同步方法
     * @param fileOrDir 文件或文件夹
     */
    public static void delete(File fileOrDir) {
        if (fileOrDir != null && fileOrDir.exists()) {
            if (fileOrDir.isFile() && fileOrDir.canWrite()) {
                fileOrDir.delete();
            }
            if (fileOrDir.isDirectory() && fileOrDir.canRead()){
                File[] files = fileOrDir.listFiles();
                for (File file : files) {
                    delete(file);
                }
            }
        }
    }

    /**
     * 拷贝
     * 同步方法
     * @param from 需要拷贝的文件或文件夹
     * @param to   拷贝到目的的文件夹
     */
    public static boolean copy(File from, File to) {
        if (from != null && from.canRead() && to != null) {
            try{
                if(from.isFile()){

                    if(!to.exists()){
                        to.mkdirs();
                    }

                    File copy;
                    String fromParent = from.getParent();
                    copy = new File(to,from.getName());
                    if(!TextUtils.isEmpty(fromParent) && fromParent.contentEquals(to.getPath())){
                        copy = new File(to,from.getName()+".copy");
                    }


                    copy.createNewFile();
                    FileOutputStream fout = new FileOutputStream(copy);

                    byte[] buffer = new byte[4096];
                    int readed = -1;
                    FileInputStream fin = new FileInputStream(from);
                    while((readed = fin.read(buffer)) != -1){
                        fout.write(buffer,0,readed);
                    }
                    fout.flush();
                    fin.close();
                    fout.close();
                    return true;
                }

                if(from.isDirectory()){
                    File copyDir = new File(to,from.getName());
                    copyDir.mkdirs();

                    File[] childFile = from.listFiles();
                    for (File file : childFile) {
                        copy(file,copyDir);
                    }
                    return true;
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean fileCopyFile(File srcFile,File toFile){
        if(srcFile != null && toFile != null){
            if(srcFile.isFile() && srcFile.canRead()){
                try {
                    File saveFile;
                    if(toFile.isDirectory()){
                        saveFile = new File(toFile,srcFile.getName());
                    }else if(toFile.isFile() || !toFile.exists()){
                        saveFile = toFile;
                    }else{
                        saveFile = new File(srcFile.getAbsoluteFile().getParentFile(),srcFile.getName()+".copy");
                    }

                    saveFile.createNewFile();

                    FileInputStream fin = new FileInputStream(srcFile);
                    FileOutputStream fout = new FileOutputStream(saveFile);
                    byte[] buffer = new byte[4096];

                    int read = -1;
                    while((read = fin.read(buffer)) != -1){
                        fout.write(buffer,0,read);
                    }
                    fout.flush();
                    fin.close();
                    fout.close();
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 在文件夹中查找文件
     * 精准查找
     * @param srcDir 进行查找的位置文件夹
     * @param fileName 需要查找的文件、文件夹名
     *
     * @return 如果找到返回File，如果找不到则null
     */
    public static File find(File srcDir,String fileName){
        return find(srcDir, fileName,TYPE_INT_FIND_FLAG_FILE);
    }
    /**
     * 在文件夹中查找文件
     * 精准查找
     * @param srcDir 进行查找的位置文件夹
     * @param fileName 需要查找的文件、文件夹名
     *
     * @return 如果找到返回File，如果找不到则null
     */
    public static File find(File srcDir, final String fileName, final int flag){
        if (srcDir != null && srcDir.isDirectory() && !TextUtils.isEmpty(fileName)) {
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if((flag & TYPE_INT_FIND_FLAG_FILE) == TYPE_INT_FIND_FLAG_FILE && pathname.isFile()){
                        return pathname.getName().contentEquals(fileName);
                    }
                    if((flag & TYPE_INT_FIND_FLAG_DIR) == TYPE_INT_FIND_FLAG_DIR && pathname.isDirectory()){
                        return pathname.getName().contentEquals(fileName);
                    }
                    return false;
                }
            };

            File[] list = srcDir.listFiles(fileFilter);
            if (list.length > 0){
                return list[0];
            }
        }
        return null;
    }

    /**
     * 获取文件的所有字节
     * @param f 目标文件
     * @return
     * @throws Exception
     */
    public static byte[] getBytes(File f) throws Exception {
        if (f != null && f.exists() && f.isFile() && f.canRead()) {
            byte[] buff = new byte[(int) f.length()];
            FileInputStream fin = new FileInputStream(f);
            fin.read(buff);
            fin.close();
            return buff;
        }
        return null;
    }
}
