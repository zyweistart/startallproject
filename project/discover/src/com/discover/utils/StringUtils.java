package com.discover.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import android.util.Base64;

import com.discover.core.Constant;
/**
 * 字符串处理类
 * @author Start
 */
public class StringUtils {
	 /**
	 * HmacSHA1签名
	 */
	public static String signatureHmacSHA1(String data,String key) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalStateException{
		return Base64.encodeToString(HmacSHA1.signature(data, key,Constant.ENCODE),Base64.DEFAULT);
	}
	/**
     * URL编码
     */
	public static String encode(String str) throws UnsupportedEncodingException {
		return encode(str,Constant.ENCODE);
	}
	/**
     * URL编码
     */
	public static String encode(String str, String enc) throws UnsupportedEncodingException {
		String strEncode = "";
		if (str != null){
			strEncode = URLEncoder.encode(str, enc);
		}
		return strEncode;
	}
	/**
	 * 号码转换，例：1 37-3887-3386转换成13738873386把有格式的转成标准号码格式
	 * @param phone
	 * @return
	 */
	public static String convertPhone(String phone){
		//号码转换
		StringBuilder sbphone=new StringBuilder();
		for(int i=0;i<phone.length();i++){
			char c=phone.charAt(i);
			int asnum=(int)c;
			if(asnum>=48&&asnum<=57){
				sbphone.append(c);
			}
		}
		return sbphone.toString();
	}
	
	public static String convertPhonelike(String phone){
		//号码转换
		StringBuilder sbphone=new StringBuilder();
		for(int i=0;i<phone.length();i++){
			char c=phone.charAt(i);
			int asnum=(int)c;
			if(asnum>=48&&asnum<=57){
				sbphone.append(c);
				sbphone.append("%");
			}
		}
		if(sbphone.length()>0){
			return sbphone.substring(0,sbphone.length()-1);
		}
		return sbphone.toString();
	}
}