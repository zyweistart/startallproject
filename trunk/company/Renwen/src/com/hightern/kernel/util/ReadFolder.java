package com.hightern.kernel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Trail 读取文件夹及文件的名称
 */
public class ReadFolder {
	
	/**
	 * 读取某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public boolean readfile(String filepath) throws FileNotFoundException, IOException {
		boolean bo = false;
		try {
			final File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("文件:" + file.getName());
				System.out.println("地址:" + file.getPath());
			} else if (file.isDirectory()) {
				System.out.println("文件夹名称:" + file.getName());
				System.out.println("文件夹地址:" + file.getPath());
				final String[] filelist = file.list();
				for (final String element : filelist) {
					final File readfile = new File(filepath + "\\" + element);
					if (!readfile.isDirectory()) {
						System.out.println("文件:" + readfile.getName());
						System.out.println("地址:" + readfile.getPath());
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + element);
					}
				}
				
			}
			
		} catch (final FileNotFoundException e) {
			bo = false;
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return bo;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		final String filePath = "E://struts-2.1.6//docs//apidocs";
		final ReadFolder rf = new ReadFolder();
		if (rf.readfile(filePath)) {
			System.out.println("读取成功！");
		}
	}
}
