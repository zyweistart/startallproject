package com.hightern.kernel.util;

/**
 * 比对两个文件夹下的同名文件，如果相同则删除
 */
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ContrastFile {
	
	public static void main(String[] args) {
		ContrastFile.check("D:/Eclipse/Myeclipse8.0GA/eclipse/plugins", "D:/Eclipse/eclipse/plugins");
		ContrastFile.check("D:/Eclipse/Myeclipse8.0GA/eclipse/features", "D:/Eclipse/eclipse/features");
	}
	
	/**
	 * 比对文件
	 * 
	 * @param myeclipsePath
	 *            --myeclipse文件夹
	 * @param eclipsePath
	 *            --eclipse文件夹
	 */
	static void check(String myeclipsePath, String eclipsePath) {
		final File myeclipse = new File(myeclipsePath);
		final File eclipse = new File(eclipsePath);
		
		final List<String> files1 = Arrays.asList(myeclipse.list());
		final List<String> files2 = Arrays.asList(eclipse.list());
		
		Collections.sort(files1);
		Collections.sort(files2);
		
		int i = 0, j = 0;
		while (true) {
			final String name1 = files1.get(i);
			final String name2 = files2.get(j);
			
			final int a = name1.compareTo(name2);
			if (a == 0) {
				ContrastFile.remove(new File(myeclipsePath + "/" + name2));
				i++;
				j++;
			} else if (a < 0) {
				i++;
			} else {
				j++;
			}
			
			if (j == files2.size()) {
				break;
			}
		}
	}
	
	/**
	 * 移出文件或文件夹
	 * 
	 * @param directory
	 */
	static void remove(File directory) {
		if (!directory.delete()) {
			System.out.println(directory.getName());
			final File[] files = directory.listFiles();
			for (final File element : files) {
				if (element.isDirectory()) {
					ContrastFile.remove(element);
				} else {
					element.delete();
				}
			}
		}
		directory.delete();
	}
}
