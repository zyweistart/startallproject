package com.discover.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.TelephonyManager;

public class CommonFn {
	
	public static ProgressDialog progressDialog(Context context,String message){
		ProgressDialog dialog = new ProgressDialog(context);
   		dialog.setMessage(message);
   		dialog.setIndeterminate(true);
   		dialog.setCancelable(true);
   		return dialog;
	}
	/**
	 * 获取本机号码
	 */
	public static String getPhoneNumber(Context context){ 
		//创建电话管理器
		TelephonyManager mTelephonyMgr;    
		//获取系统固定号码
		mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		//返回手机号码
		return mTelephonyMgr.getLine1Number();  
	}
	/**
	 * 流转字符串方法
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	/**
	 * 获取权限
	 * @param permission
	 *            权限
	 * @param path
	 *            路径
	 */
	public static void chmod(String permission, String path) throws IOException {
		String command = "chmod " + permission + " " + path;
		Runtime runtime = Runtime.getRuntime();
		runtime.exec(command);
	}
}
