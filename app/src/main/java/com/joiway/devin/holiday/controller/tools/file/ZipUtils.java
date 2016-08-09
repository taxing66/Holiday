package com.joiway.devin.holiday.controller.tools.file;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by 潘阳君 on 2016/1/7.
 * 【豆浆框架】-【工具集】
 * 【Zip压缩工具】
 */
public class ZipUtils {

    public static final String TAG = "ZipUtils";

    public interface ZipListener {

        void onStart();

        void onZipping(String entryName, int total, int value);

        void onFinish(File zipFile);

        void onError(Exception e);
    }

    private boolean isRunning = false;
    private File beZipFile;
    private int totalCount;
    private int currentValue;

    private File saveTarget;

    private ZipListener mListener;

    public void setListener(ZipListener mListener) {
        this.mListener = mListener;
    }

    private File zipRun() {
        isRunning = true;

        try {
            currentValue = 0;
            File zip;
            if (saveTarget == null) {
                zip = new File(beZipFile.getParentFile().getAbsoluteFile() + "/" + beZipFile.getName() + ".zip");
            } else {
                zip = saveTarget;
            }
            zip.createNewFile();

            ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(zip));
            zipout.setLevel(9);

            if (mListener != null) {
                totalCount = getDirFileCount(beZipFile);
                mListener.onStart();
            }

            if (beZipFile.isDirectory()) {
                zipDir(zipout, "", beZipFile);
            } else {
                zipFile(zipout, beZipFile.getName(), beZipFile);
            }

            zipout.flush();
            zipout.closeEntry();
            zipout.finish();
            zipout.close();

            if (mListener != null) {
                mListener.onFinish(zip);
            }
            return zip;
        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onError(e);
            }
        }
        isRunning = false;
        return null;
    }

    private int getDirFileCount(File dir) {
        int count = 0;
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    count += getDirFileCount(file);
                } else {
                    count++;
                }
            }
        } else {
            count++;
        }
        return count;
    }

    private void zipDir(ZipOutputStream zipout, String parent, File dir) throws Exception {
        File[] files = dir.listFiles();

        String path;
        for (File file : files) {
            if (parent.isEmpty()) {
                path = file.getName();
            } else {
                path = parent + File.separator + file.getName();
            }

            if (file.isDirectory()) {
                zipDir(zipout, path, file);
            } else {
                zipFile(zipout, path, file);
            }
        }
    }

    private void zipFile(ZipOutputStream zipout, String name, File file) throws Exception {

        System.out.println("zip:" + name);

        ZipEntry entry = new ZipEntry(name);
        zipout.putNextEntry(entry);
        byte buffer[] = new byte[4096];

        FileInputStream fin = new FileInputStream(file);

        for (int read; (read = fin.read(buffer)) != -1;) {
            zipout.write(buffer, 0, read);
        }

        fin.close();
        zipout.flush();

        if (mListener != null) {
            currentValue++;
            mListener.onZipping(name, totalCount, currentValue);
        }
    }

    private File unzipRun(InputStream inputStream) {
        isRunning = true;
        try {
            if (mListener != null) {
                mListener.onStart();
            }
            File saveDir;
            if (saveTarget == null) {
                saveDir = new File(beZipFile.getAbsolutePath());
            } else {
                saveDir = saveTarget;
            }
            saveDir.mkdirs();

            ZipInputStream zipin = new ZipInputStream(inputStream);

            ZipEntry zipEntry = null;
            int count = 0;
            while ((zipEntry = zipin.getNextEntry()) != null) {
                File outFile = new File(saveDir, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    outFile.mkdirs();
                } else {
                    File parent = outFile.getParentFile();
                    if(parent != null){
                        parent.mkdirs();
                    }
                    outFile.createNewFile();
                    FileOutputStream fout = new FileOutputStream(outFile);

                    byte[] buffer = new byte[2048];
                    int read = 0;
                    while ((read = zipin.read(buffer)) != -1) {
                        fout.write(buffer, 0, read);
                    }
                    fout.flush();
                    fout.close();
                    if (mListener != null) {
                        mListener.onZipping(zipEntry.getName(), -1, ++count);
                    }
                }
            }
            zipin.closeEntry();
            zipin.close();
            if (mListener != null) {
                mListener.onFinish(saveDir);
            }
            return saveDir;
        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onError(e);
            }
        }
        isRunning = false;
        return null;
    }

    /**
     * 同步压缩
     *
     * @param beZipFile 被压缩的File
     * @return 已压缩好的File
     */
    public File startZip(File beZipFile) {
        return startZip(beZipFile, null);
    }

    /**
     * 同步压缩
     *
     * @param beZipFile 被压缩的File
     * @param saveTarget 自定义保存的文件
     * @return 已压缩好的File
     */
    public File startZip(File beZipFile, File saveTarget) {
        if (!isRunning) {
            this.beZipFile = beZipFile;
            this.saveTarget = saveTarget;
            return zipRun();
        }
        return null;
    }

    /**
     * 同步解压
     *
     * @param beZipFile 被压缩的File
     * @return 已压缩好的File
     */
    public File startUnzip(File beZipFile) {
        return startUnzip(beZipFile, null);
    }

    /**
     * 同步解压
     *
     * @param beZipFile 被压缩的File
     * @param saveTarget 自定义解压的位置
     * @return 已压缩好的File
     */
    public File startUnzip(File beZipFile, File saveTarget) {
        if (!isRunning) {
            this.beZipFile = beZipFile;
            this.saveTarget = saveTarget;
            try {
                return unzipRun(new FileInputStream(beZipFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                if (mListener != null) {
                    mListener.onError(e);
                }
            }
        }
        return null;
    }

    /**
     * 同步解压
     *
     * @param zipFileInputStream 被压缩的输入流
     * @param saveTarget 自定义解压的位置
     * @return 已压缩好的File
     */
    public File startUnzip(InputStream zipFileInputStream, File saveTarget) {
        if (!isRunning && saveTarget != null) {
            this.saveTarget = saveTarget;
            return unzipRun(zipFileInputStream);
        }
        return null;
    }

    /**
     * 进行同步压缩文件
     *
     * @param toZip
     * @param listener
     */
    public static final File toZip(File toZip, ZipListener listener) {
        return toZip(toZip, null, listener);
    }
    /**
     * 进行同步压缩文件
     *
     * @param toZip
     * @param listener
     */
    public static final File toZip(File toZip, File saveFile, ZipListener listener) {
        ZipUtils zip = new ZipUtils();
        zip.setListener(listener);
        return zip.startZip(toZip,saveFile);
    }
    /**
     * 进行同步解压文件
     *
     * @param unzip
     * @param listener
     * @return
     */
    public static final File toUnzip(File unzip, ZipListener listener) {
        ZipUtils zip = new ZipUtils();
        zip.setListener(listener);
        return zip.startUnzip(unzip);
    }

    /**
     * 进行同步解压文件
     *
     * @param unzip zip文件
     * @param toSaveDir 解压到的路径
     * @param listener
     * @return
     */
    public static final File toUnzip(File unzip, File toSaveDir, ZipListener listener) {
        ZipUtils zip = new ZipUtils();
        zip.setListener(listener);
        return zip.startUnzip(unzip, toSaveDir);
    }

    /**
     * 进行同步解压文件
     *
     * @param zipFileInputStream zip文件的输入流
     * @param toSaveDir 解压到的路径
     * @param listener
     * @return
     */
    public static final File toUnzip(InputStream zipFileInputStream, File toSaveDir, ZipListener listener) {
        ZipUtils zip = new ZipUtils();
        zip.setListener(listener);
        return zip.startUnzip(zipFileInputStream, toSaveDir);
    }
    /**
     * 从文件格式上判读是否是zip文件
     * @param zipFile
     * @return
     */
    public static final boolean isZipFile(File zipFile){
        //zip文件，文件头java是字节反转
        int ZIP_HEADER = 0x50ab0304;//0x04034b50;

        if(zipFile.isFile() && zipFile.canRead()){
            try {
                DataInputStream fin = new DataInputStream(new FileInputStream(zipFile));
                int zipHeader = fin.readInt();
                fin.close();
                return zipHeader == ZIP_HEADER;
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
