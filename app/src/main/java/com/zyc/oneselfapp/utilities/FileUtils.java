package com.zyc.oneselfapp.utilities;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by zyc on 2016/12/16.
 */

public class FileUtils {
    private static int BUFFER_SIZE = 1024 * 8;
    /**
     * 获取程序根路径
     * @return
     */
    public static String getRootFile(){
        String rootPath = "";
        //判断SD卡是否挂载并且有写的权限
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && Environment.getExternalStorageDirectory().canWrite()) {
            rootPath = getSdcardRootPath()+"/Zyc";
        } else {
            rootPath = getDataRootPath()+"/Zyc";
        }
        return rootPath;
    }

    /**
     * 获取SD卡根路径
     * @return
     */
    public static String getSdcardRootPath(){
        return android.os.Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取机身路径
     * @return
     */
    public static String getDataRootPath(){
        return android.os.Environment.getDataDirectory()+"/data/"+CommUtils.getPackageName()+"/files/";
    }

    /**
     * 写文件
     * @param inputStream 输入流
     * @param fileString 保存路径
     * @return
     */
    public static  boolean writeToDisk(InputStream inputStream,String fileString){
        File file=new File(fileString);
        OutputStream outputStream=null;
        byte[] bytes=new byte[BUFFER_SIZE];
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            outputStream=new FileOutputStream(file);
            /*for (int read=0;(read=inputStream.read(bytes,0,bytes.length))!=-1;){
                outputStream.write(bytes,0,read);
            }*/
            while (true){
               int read= inputStream.read(bytes);
                if (read==-1){
                    break;
                }
                outputStream.write(bytes,0,read);
            }
            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解压文件
     * @param srcFileFullName 完整的文件路径
     * @param targetPath 需要解压到的路径
     * @return 是否成功
     */
    public static boolean unzip(String srcFileFullName, String targetPath){
        BufferedOutputStream bos=null;
        BufferedInputStream bufferedInputStream=null;
        try {
            ZipFile zipFile=new ZipFile(srcFileFullName);
            Enumeration<? extends ZipEntry> zipEntrys=zipFile.entries();
            while (zipEntrys.hasMoreElements()){
                ZipEntry zipEntry=zipEntrys.nextElement();
                if(zipEntry.isDirectory()){
                    new File(targetPath+zipEntry.getName()).mkdirs();
                    continue;
                }
                //得到输入流
                bufferedInputStream=new BufferedInputStream(zipFile.getInputStream(zipEntry));
                File file = new File(targetPath + zipEntry.getName());
                // 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                // 而这个文件所在的目录还没有出现过，所以要建出目录来。
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos,
                        BUFFER_SIZE);
                int read;
                byte[] data=new byte[BUFFER_SIZE];
                while ((read=bufferedInputStream.read(data,0,BUFFER_SIZE))!=-1){
                    bos.write(data,0,read);
                }
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bos!=null){
                    bos.close();
                }
                if (bufferedInputStream!=null){
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public static boolean writeToDiskAndUnzip(InputStream inputStream,String fileString){
        writeToDisk(inputStream,fileString);
        File file=new File(fileString);
        unzip(fileString,file.getParent()+File.separator);
        return true;
    }

    /**
     * 输入流转为String
     * @param inputStream 输入流
     * @return 对应的String
     */
    public static String readInputStream2String(InputStream inputStream){
        String returnSting=null;
        ByteArrayOutputStream byteArrayOutputStream=null;
        try {
            byteArrayOutputStream=new ByteArrayOutputStream();
            byte[] data=new byte[1024];
            for (int read=0;(read=inputStream.read(data,0,data.length))!=-1;){
                byteArrayOutputStream.write(data,0,read);
            }
            returnSting=byteArrayOutputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
                if(byteArrayOutputStream!=null){
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnSting;
    }

    /**
     * 通过文件得到Bean类型
     * @param file 文件路径
     * @param t 对应Bean类型
     * @param <T> 返回类型
     * @return 返回Bean
     */
    public static <T> T getBeanByFile(File file,Class<T> t){
        T bean=null;
        try {
            InputStream fileInputStream=new FileInputStream(file);
            String beanString=readInputStream2String(fileInputStream);
            Gson gson=new Gson();
            bean= gson.fromJson(beanString, t);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
