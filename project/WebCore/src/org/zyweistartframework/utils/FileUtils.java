package org.zyweistartframework.utils;

import java.io.File;

public class FileUtils {
	/**
     * 移出文件或文件夹
     */
    public static void remove(File directory) {
    	if(directory.exists()){
    		if (!directory.delete()) {
                File[] files = directory.listFiles();
                for (int i = 0, n = files.length; i < n; i++) {
                    if (files[i].isDirectory()) {
                        remove(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
            directory.delete();
    	}
    }
    /**
	 * 目录不存在则创建目录
	 */
    public static File createDirectory(String path) {
        final File dir = new File(path);
        if (!dir.exists()) {
        	dir.mkdirs();
        }
        return dir;
    }
    /**
     * 获取文件名
     */
    public static String getFileName(String input){
        int fIndex = input.lastIndexOf("\\");
        if (fIndex == -1) {
            fIndex = input.lastIndexOf("/");
            if (fIndex == -1) {
                return input;
            }
        } 
        return input.substring(fIndex + 1);
    }
    /**
     * 获取文件名除开扩展名
     */
    public static String getFileNameNotExtension(String input){
    	String fileName=getFileName(input);
        Integer exint=fileName.indexOf(".");
        if(exint!=-1){
        	return fileName.substring(0,exint);
        }else{
        	return fileName;
        }
    }
    /**
     * 获取扩展名
     */
    public static String getExtension(String fileName){
    	int index=fileName.lastIndexOf(".");
    	if(index!=-1){
    		return fileName.substring(index);
    	}
    	return "";
    }
}
