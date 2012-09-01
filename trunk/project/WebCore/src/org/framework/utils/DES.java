package org.framework.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.Key;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.zyweistartframework.config.ConfigConstant;

public class DES {
	
	public final static String ALGORITHM="DES";
	/**
	 * 获取文件密钥
	 */
	public static Key getKey(String keyFileName) throws Exception {
		InputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = new FileInputStream(keyFileName);
			ois = new ObjectInputStream(is);
			return (Key) ois.readObject();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				}finally{
					ois = null;
				}
			}
			if(is != null) {
				try {
					is.close();
				}finally{
					is = null;
				}
			}
		}
	}
	/**
	 * DES加密
	 */
	public static String encrypt(String str, String keyFileName) throws Exception{
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, getKey(keyFileName));
		byte[] buffer = cipher.doFinal(str.getBytes(ConfigConstant.ENCODING));
		return new String(Base64.encodeBase64(buffer));
	}
	/**
	 * DES解密
	 */
	public static String decrypt(String str, String keyFileName) throws Exception{
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, getKey(keyFileName));
		byte[] buffer = cipher.doFinal(Base64.decodeBase64(str.getBytes()));
		return new String(buffer,ConfigConstant.ENCODING).trim();
	}

}