package com.hightern.consult.util;

import java.io.File;
import java.util.HashMap;
//处理视频生成缩略图
public class videoUtil {

	 private static String  BAT_PATH;     //批处理文件地址 
	 private static String INPUT_PATH;    //视频的地址
	 private static String OUTPUT_PATH;   //生成缩略图的地址
	 private static String PROJECT_PATH;  //ffmpeg.exe  处理文件的地址
	 private static Boolean TYPE=false; 
	
	 private static HashMap<String, String> fileType;  
     
	    static  
	    {  
	        fileType = new HashMap<String, String>();  
	        fileType.put("avi", "true");  
	        fileType.put("mpg", "true");  
	        fileType.put("wmv", "true");  
	        fileType.put("3gp", "true");  
	        fileType.put("mov", "true");  
	        fileType.put("mp4", "true");  
	        fileType.put("asf", "true");  
	        fileType.put("asx", "true");  
	        fileType.put("flv", "true");  
	    }  
	
	    public static Boolean convertToFLV(String batPath,String projectPath, String inputFile, String outputFile)  
	    {  
	    	BAT_PATH=batPath;
	        INPUT_PATH = inputFile;  
	        OUTPUT_PATH = outputFile;  
	        PROJECT_PATH = projectPath;  
	        if (checkContentType())  
	        {
	        	return  processFLV();// 直接将文件转为flv文件  
	        }else{
	        	return false;
	        	}
	    }  
	  
	    private static boolean checkContentType()  
	    {  
	        String type = INPUT_PATH.substring(INPUT_PATH.lastIndexOf(".") + 1, INPUT_PATH.length()).toLowerCase();  
	        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）  
	        return "true".equals(fileType.get(type));  
	    }  
	  
	    private static Boolean processFLV()  
	    {  
	        if (new File(INPUT_PATH).isFile())  
	        {  
	            try  
	            {  
	                String cmd = "cmd /c start "+BAT_PATH+"  "+PROJECT_PATH+"  "+INPUT_PATH+"  "+OUTPUT_PATH+" "; 
	                Runtime.getRuntime().exec(cmd);  
	                TYPE=true;
	            }  
	            catch (Exception e)  
	            {  
	            	TYPE=false;
	                e.printStackTrace();  
	            }  
	        }
			return TYPE;  
	    }     
	    
	    
	
	
}
