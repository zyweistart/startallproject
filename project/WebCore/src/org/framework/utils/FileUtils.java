package org.framework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.zip.GZIPInputStream;

import org.framework.config.Variable;
import org.zyweistartframework.utils.StackTraceInfo;


public class FileUtils {
	/**
	 * 判断目录是否存在
	 */
	public static boolean isDirExists(String filename) {
		File file = new File(filename);
		return (file.isDirectory() && file.exists());
	}
	/**
	 * 判断文件是否存在
	 */
	public static boolean isFileExists(String filename) {
		File file = new File(filename);
		return (file.isFile() && file.exists());
	}
	/**
	 * 获取文件长度
	 */
	public static long getFileLength(String filename) {
		File file = new File(filename);
		if (file.isFile() && file.exists()) {
			return file.length();
		}
		return 0L;
	}
	/**
	 * 级联创建目录
	 */
	public static boolean mkdirs(String filepath) {
		return mkdirs(new File(filepath));
	}
	/**
	 * 级联创建目录
	 */
	public static boolean mkdirs(File file) {
		if(!file.exists()) {
			return file.mkdirs();
		}
		return true;
	}
	/**
	 * 删除文件
	 */
	public static boolean deleteFile(String filename) {
		return deleteFile(new File(filename));
	}
	/**
	 * 删除文件，如果为目录则删除失败
	 */
	public static boolean deleteFile(File file) {
		if(file.exists()) {
			if(file.isFile()) {
				return file.delete();
			} else if(file.isDirectory()) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 删除目录
	 */
	public static boolean deleteDir(String dir) {
		return deleteDir(new File(dir));
	}
	/**
	 * 删除目录,如果为文件删除失败
	 */
	public static boolean deleteDir(File file) {
		if(file.exists()) {
			if(file.isFile()) {
				return false;
			} else if(file.isDirectory()) {
				if(!deleteAllFile(file.getPath())) {
					return false;
				}
				return file.delete();
			}
		}
		return true;
	}
	/**
	 * 删除目录，删除当前目录下的所有文件
	 */
	public static boolean deleteAllFile(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			return true;
		}
		if (!file.isDirectory()) {
			return false;
		}
		String[] tempList = file.list();
		File temp = null;
		boolean flag = false;
		for (int i = 0; i < tempList.length; i++) {
			if (dir.endsWith(File.separator)) {
				temp = new File(dir + tempList[i]);
			} else {
				temp = new File(dir + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				flag = temp.delete();
			} else if (temp.isDirectory()) {
				flag = deleteAllFile(dir + File.separator + tempList[i]);
				if(!flag) {
					return false;
				}
				flag = deleteDir(dir + File.separator + tempList[i]);
			}
			if(!flag) {
				return false;
			}
		}
		return true;
	}

	public static boolean renameTo(String filenamefrom, String fileto) {
		return renameTo(new File(filenamefrom), new File(fileto));
	}

	public static boolean renameTo(File filefrom, String fileto) {
		return renameTo(filefrom, new File(fileto));
	}
	
	public static boolean renameTo(String filenamefrom, String filetopath, String filetoname) {
		File file = new File(filetopath);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				return false;
			}	
		}
		return renameTo(new File(filenamefrom), new File(filetopath + filetoname));
	}

	public static boolean renameTo(File filefrom, File fileto) {
		if(!filefrom.exists() || fileto.exists()) {
			return false;
		}
		if (!fileto.getParentFile().exists()) {
			if(!fileto.getParentFile().mkdirs()) {
				return false;
			}
		}
		return filefrom.renameTo(fileto);
	}
	/**
	 * 创建文件
	 */
	public static boolean createFile(String filepath, String filename, long filesize) {
		if(!mkdirs(filepath)){
			return false;
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(filepath + "/" + filename, "rw");
			raf.setLength(filesize);
			return true;
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return false;
		} finally {
			if(raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return false;
				}finally{
					raf = null;
				}
			}
		}
	}
	/**
	 * 根据正则表达式过滤出当前目录下满足条件的文件名
	 */
	public static File[] getFilesByFilterRegex(String dirpath,final String regex) {
		File dir = new File(dirpath);
		if (dir.exists() && dir.isDirectory()) {
			return dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.matches(regex);
				}
			});
		} else {
			return null;
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
	/**
	 * 获取文件字节数组
	 */
	public static byte[] getBytes(File file) {
		if (file == null) {
			return null;
		}
		FileInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = new ByteArrayOutputStream(8196);
			int len=-1;
			byte[] b = new byte[Variable.BUFFER];
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			b=null;
			return out.toByteArray();
		} catch (IOException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return null;
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return null;
				}finally{
					out=null;
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return null;
				}finally{
					in=null;
				}
			}
		}
	}
	/**
	 * 根据文件完整路径获取文件的字符内容
	 */
	public static String getFileStringContent(String fileName) {
		FileInputStream fileInputStream =null;
		try{
			fileInputStream =new FileInputStream(fileName);
			int len=-1;
			byte[] buffer = new byte[Variable.BUFFER];
			StringBuffer sbContent = new StringBuffer();
			while ((len = fileInputStream.read(buffer)) != -1) {
				sbContent.append(new String(buffer, 0, len));
			}
			buffer=null;
			return sbContent.toString();
		}catch(IOException e){
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return null;
		}finally{
			if(fileInputStream!=null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return null;
				}finally{
					fileInputStream=null;
				}
			}
		}
	}
	/**
	 * 把输入流存储到本地
	 * @param directory
	 * 保存目录路径
	 * @param fileName
	 * 文件名
	 * @param inputStream
	 * 文件的输入流
	 */
	public static boolean generate(String directory, String fileName, InputStream inputStream) {
		if(!mkdirs(directory)){
			return false;
		}
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			if (inputStream != null) {
				int len = -1;
				byte[] buffer = new byte[Variable.BUFFER];
				bufferedInputStream = new BufferedInputStream(inputStream);
				bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(directory + fileName)));
				while ((len = bufferedInputStream.read(buffer)) != -1) {
					bufferedOutputStream.write(buffer, 0, len);
				}
				buffer = null;
				bufferedOutputStream.flush();
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			return false;
		} finally {
			if(bufferedOutputStream!=null){
				try {
					bufferedOutputStream.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return false;
				}finally{
					try {
						bufferedOutputStream.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
						return false;
					}finally{
						bufferedOutputStream=null;
					}
				}
			}
			if(bufferedInputStream!=null){
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return false;
				}finally{
					bufferedInputStream=null;
				}
			}
		}
	}
	/**
	 * 把输入流存储到本地并返回输入流文件的MD5
	 * @param directory
	 * 保存目录路径
	 * @param filename
	 * 文件名
	 * @param inputStream
	 * 文件的输入流
	 */
	public static String generateMD5(String directory, String fileName, InputStream inputStream) {
		if(inputStream==null){
			return null;
		}
		if(!mkdirs(directory)){
			return null;
		}
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			int len=-1;
			bufferedInputStream = new BufferedInputStream(inputStream);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(directory + fileName)));
			MessageDigest md = MessageDigest.getInstance(MD5.ALGORITHM);
			byte[] buffer = new byte[Variable.BUFFER];
			while ((len = bufferedInputStream.read(buffer)) != -1) {
				bufferedOutputStream.write(buffer, 0, len);
				md.update(buffer, 0, len);
			}
			buffer=null;
			return MD5.byte2hex(md.digest());
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			return null;
		} finally {
			if(bufferedOutputStream!=null){
				try {
					bufferedOutputStream.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
					return null;
				}finally{
					try {
						bufferedOutputStream.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
						return null;
					}finally{
						bufferedOutputStream=null;
					}
				}
			}
			if(bufferedInputStream!=null){
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
					return null;
				}finally{
					bufferedInputStream=null;
				}
			}
		}
	}
	/**
	 * 解压缩GZIP文件返回原始文件的MD5值和大小
	 */
	public static String[] decompressGZIP(String filePath) {
		BufferedInputStream bufferedInputStream = null;
		try {
			int len = -1;
			long fileLength = 0L;
			byte[] buffer = new byte[Variable.BUFFER];
			MessageDigest md = MessageDigest.getInstance(MD5.ALGORITHM);
			bufferedInputStream = new BufferedInputStream(new GZIPInputStream(new FileInputStream(filePath)));
			while ((len = bufferedInputStream.read(buffer)) != -1) {
				md.update(buffer, 0, len);
				fileLength = fileLength + len;
			}
			buffer=null;
			return new String[] {MD5.byte2hex(md.digest()),String.valueOf(fileLength)};
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			return null;
		} finally {
			if(bufferedInputStream!=null){
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
					return null;
				}finally{
					bufferedInputStream=null;
				}
			}
		}
	}
	/**
	 * 通过给定的URL有本地文件名下载保存文件
	 */
	public static boolean downLoadFile(File destFile, String url) {
		URLConnection connection = null;
		try {
			URL u = new URL(url);
			connection = u.openConnection();
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			return false;
		}
		connection.setReadTimeout(100000);
		BufferedInputStream bis=null;
		BufferedOutputStream bos=null;
		try {
			destFile.createNewFile();
			bis=new BufferedInputStream(connection.getInputStream());
			bos=new BufferedOutputStream(new FileOutputStream(destFile));
			int l = -1;
			byte[] buffer = new byte[Variable.BUFFER];
			while ((l = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, l);
			}
			buffer=null;
			return true;
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			deleteFile(destFile);
			return false;
		} finally {
			if (bos != null) {
				try {
					bos.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
					deleteFile(destFile);
					return false;
				}finally{
					try {
						bos.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
						deleteFile(destFile);
						return false;
					}finally{
						bos=null;
					}
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
					deleteFile(destFile);
					return false;
				}finally{
					bis=null;
				}
			}
		}
		//System.gc();
	}
}