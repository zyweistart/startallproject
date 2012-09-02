package com.hightern.kernel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
	
	/**
	 * 移出文件或文件夹
	 * 
	 * @param directory
	 */
	public static void remove(File directory) {
		if (!directory.delete()) {
			final File[] files = directory.listFiles();
			for (final File element : files) {
				if (element.isDirectory()) {
					FileUtil.remove(element);
				} else {
					element.delete();
				}
			}
		}
		directory.delete();
	}
	
	/**
	 * 创建文件夹
	 * 
	 * @param path
	 */
	public static void createDirectory(String path) {
		final File theFile = new File(path);
		if (!theFile.exists()) {
			theFile.mkdirs();
		}
	}
	
	/**
	 * 文件下载
	 */
	public static void downLoadFile(File destFile, String url) {
		byte[] buffer = new byte[8 * 1024];
		URL u;
		URLConnection connection = null;
		try {
			u = new URL(url);
			connection = u.openConnection();
		} catch (Exception e) {
			System.out.println("ERR:" + url);
			return;
		}
		connection.setReadTimeout(100000);
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			destFile.createNewFile();
			is = connection.getInputStream();
			fos = new FileOutputStream(destFile);
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

		} catch (Exception e) {
			destFile.delete();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		buffer = null;
		//System.gc();
	}
}
