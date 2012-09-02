/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.hightern.kernel.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * 根据模型生成JAVA文件
 * 
 * @author Stone
 */
public class GenerateModel {
	
	Logger logger = Logger.getLogger(this.getClass());
	/** ***************生成JAVA文件的地址******************** */
	private static final String PACKURL = "src/com/hightern/";
	private static final String FLODERURL = "WebRoot/";
	/** ***************各模板的存放地址********************** */
	private static final String JAVA_ACTION_TEMPLE_PATH = "/com/hightern/kernel/temple/Action.txt";
	private static final String JAVA_SERVICE_TEMPLE_PATH = "/com/hightern/kernel/temple/Service.txt";
	private static final String JAVA_SERVICEIMPL_TEMPLE_PATH = "/com/hightern/kernel/temple/ServiceImpl.txt";
	private static final String JAVA_DAO_TEMPLE_PATH = "/com/hightern/kernel/temple/Dao.txt";
	private static final String JAVA_DAOIMPL_TEMPLE_PATH = "/com/hightern/kernel/temple/DaoImpl.txt";
	private static final String JAVA_MODEL_TEMPLE_PATH = "/com/hightern/kernel/temple/Model.txt";
	private static final String PAGE_MANAGER_TEMPLE_PATH = "/com/hightern/kernel/temple/Manager.txt";
	private static final String PAGE_LIST_TEMPLE_PATH = "/com/hightern/kernel/temple/List.txt";
	private static final String PAGE_ADD_TEMPLE_PATH = "/com/hightern/kernel/temple/Add.txt";
	private static final String PAGE_EDIT_TEMPLE_PATH = "/com/hightern/kernel/temple/Edit.txt";
	private static final String PAGE_DETAIL_TEMPLE_PATH = "/com/hightern/kernel/temple/Detail.txt";
	public File tempFile; // 临时文件
	
	/**
	 * 根据包名和对象名生成统一的相关属性名称
	 * 
	 * @param packageName
	 * @param entityName
	 * @throws IOException
	 */
	public void Generation(String packageName, String entityName) {
		try {
			final String upperName = StringUtils.changeFirstLetterUpperCase(entityName);
			// 将首字母最大化
			final String lowerCase = entityName.toLowerCase();
			// 将字符串最小化
			final String upperCase = entityName.toUpperCase();
			// 将字符串最大化
			this.readFile(packageName, upperName, lowerCase, upperCase);
		} catch (final IOException ex) {
			logger.error(ex);
		}
	}
	
	/**
	 * 读到模板并替换相应的字符串
	 * 
	 * @param packageName
	 * @param upperName
	 * @param lowerCase
	 * @param upperCase
	 */
	public void readFile(String packageName, String upperName, String lowerCase, String upperCase) throws IOException {
		// 生成控制对象
		tempFile = new File(this.getResource(GenerateModel.JAVA_ACTION_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(1, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件Action.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件Action.txt未获取到！");
		}
		
		// 生成服务层接口
		tempFile = new File(this.getResource(GenerateModel.JAVA_SERVICE_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(2, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件Service.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件Service.txt未获取到！");
		}
		
		// 生成服务层实现方法
		tempFile = new File(this.getResource(GenerateModel.JAVA_SERVICEIMPL_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(3, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件ServiceImpl.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件ServiceImpl.txt未获取到！");
		}
		
		// 生成持久层接口
		tempFile = new File(this.getResource(GenerateModel.JAVA_DAO_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(4, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件Dao.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件Dao.txt未获取到！");
		}
		
		// 生成持久层实现方法
		tempFile = new File(this.getResource(GenerateModel.JAVA_DAOIMPL_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(5, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件DaoImpl.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件DaoImpl.txt未获取到！");
		}
		
		// 生成实习对象
		tempFile = new File(this.getResource(GenerateModel.JAVA_MODEL_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(6, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件Model.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件Model.txt未获取到！");
		}
		
		// 生成添加页面
		tempFile = new File(this.getResource(GenerateModel.PAGE_ADD_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(7, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件Add.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件Add.txt未获取到！");
		}
		// 生成编辑页面
		tempFile = new File(this.getResource(GenerateModel.PAGE_EDIT_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(8, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件Edit.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件Edit.txt未获取到！");
		}
		// 生成管理页面
		tempFile = new File(this.getResource(GenerateModel.PAGE_MANAGER_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(9, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件Manager.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件Manager.txt未获取到！");
		}
		
		// 生成列表页面
		tempFile = new File(this.getResource(GenerateModel.PAGE_LIST_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(10, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件List.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件List.txt未获取到！");
		}
		
		// 生成详细页面
		tempFile = new File(this.getResource(GenerateModel.PAGE_DETAIL_TEMPLE_PATH));
		if (tempFile.exists()) {
			final String tempString = replace(tempFile, packageName, upperName, lowerCase, upperCase);
			if (tempString != null) {
				writeFile(11, packageName, upperName, lowerCase, tempString);
			} else {
				logger.error("模型文件Detail.txt中的内容未获取到！");
			}
		} else {
			logger.error("模型文件Detail.txt未获取到！");
		}
	}
	
	/**
	 * 生成到具体的文件夹及文件
	 * 
	 * @param type
	 * @param packageName
	 * @param upperName
	 * @param lowerCase
	 * @param content
	 */
	public void writeFile(int type, String packageName, String upperName, String lowerCase, String content) throws IOException {
		if (type == 1) { // 生成控制对象
			final String path = GenerateModel.PACKURL + packageName + "/action/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + upperName + "Action.java");
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + upperName + "Action.java"), content, "UTF-8");
				System.out.println(upperName + "Action.java  生成成功！");
			} else {
				logger.error(upperName + "Action.java 已经存在！");
			}
		}
		if (type == 2) {// 生成服务层接口
			final String path = GenerateModel.PACKURL + packageName + "/service/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + upperName + "Service.java");
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + upperName + "Service.java"), content, "UTF-8");
				System.out.println(upperName + "Service.java  生成成功！");
			} else {
				logger.error(upperName + "Service.java 已经存在！");
			}
		}
		if (type == 3) {// 生成服务层实现方法
			final String path = GenerateModel.PACKURL + packageName + "/service/impl/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + upperName + "ServiceImpl.java");
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + upperName + "ServiceImpl.java"), content, "UTF-8");
				System.out.println(upperName + "ServiceImpl.java  生成成功！");
			} else {
				logger.error(upperName + "ServiceImpl.java 已经存在！");
			}
		}
		if (type == 4) {// 生成持久层接口
			final String path = GenerateModel.PACKURL + packageName + "/dao/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + upperName + "Dao.java");
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + upperName + "Dao.java"), content, "UTF-8");
				System.out.println(upperName + "Dao.java  生成成功！");
			} else {
				logger.error(upperName + "Dao.java 已经存在！");
			}
		}
		if (type == 5) {// 生成持久层实现方法
			final String path = GenerateModel.PACKURL + packageName + "/dao/impl/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + upperName + "DaoImpl.java");
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + upperName + "DaoImpl.java"), content, "UTF-8");
				System.out.println(upperName + "DaoImpl.java  生成成功！");
			} else {
				logger.error(upperName + "DaoImpl.java 已经存在！");
			}
		}
		if (type == 6) {// 生成对象模型
			final String path = GenerateModel.PACKURL + packageName + "/model/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + upperName + ".java");
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + upperName + ".java"), content, "UTF-8");
				System.out.println(upperName + ".java  生成成功！");
			} else {
				logger.error(upperName + ".java 已经存在！");
			}
		}
		
		if (type == 7) {// 生成添加模型
			final String path = GenerateModel.FLODERURL + packageName + "/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + "add_" + lowerCase + ".jsp");
			
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + "add_" + lowerCase + ".jsp"), content, "UTF-8");
				System.out.println("add_" + lowerCase + ".jsp 生成成功！");
			} else {
				logger.error("add_" + lowerCase + ".jsp 已经存在！");
			}
		}
		
		if (type == 8) {// 生成修改模型
			final String path = GenerateModel.FLODERURL + packageName + "/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + "edit_" + lowerCase + ".jsp");
			
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + "edit_" + lowerCase + ".jsp"), content, "UTF-8");
				System.out.println("edit_" + lowerCase + ".jsp 生成成功！");
			} else {
				logger.error("edit_" + lowerCase + ".jsp 已经存在！");
			}
		}
		
		if (type == 9) {// 生成管理模型
			final String path = GenerateModel.FLODERURL + packageName + "/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + "manager_" + lowerCase + ".jsp");
			
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + "manager_" + lowerCase + ".jsp"), content, "UTF-8");
				System.out.println("manager_" + lowerCase + ".jsp 生成成功！");
			} else {
				logger.error("manager_" + lowerCase + ".jsp 已经存在！");
			}
		}
		
		if (type == 10) {// 生成列表模型
			final String path = GenerateModel.FLODERURL + packageName + "/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + "list_" + lowerCase + ".jsp");
			
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + "list_" + lowerCase + ".jsp"), content, "UTF-8");
				System.out.println("list_" + lowerCase + ".jsp 生成成功！");
			} else {
				logger.error("list_" + lowerCase + ".jsp 已经存在！");
			}
		}
		
		if (type == 11) {// 生成详细模型
			final String path = GenerateModel.FLODERURL + packageName + "/";
			final File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			final File ff = new File(path + "detail_" + lowerCase + ".jsp");
			
			if (!ff.exists()) {
				FileUtils.writeStringToFile(new File(path + "detail_" + lowerCase + ".jsp"), content, "UTF-8");
				System.out.println("detail_" + lowerCase + ".jsp 生成成功！");
			} else {
				logger.error("detail_" + lowerCase + ".jsp 已经存在！");
			}
		}
	}
	
	/**
	 * 替换字符串
	 * 
	 * @param file
	 * @param packageName
	 * @param upperName
	 * @param lowerName
	 * @param upperCase
	 * @return String
	 */
	public String replace(File file, String packageName, String upperName, String lowerName, String upperCase) throws IOException {
		String content = null;
		content = FileUtils.readFileToString(file, "UTF-8");
		content = content.replaceAll("\\#\\{packageName\\}", packageName);
		content = content.replaceAll("\\#\\{upperName\\}", upperName);
		content = content.replaceAll("\\#\\{lowerName\\}", lowerName);
		content = content.replaceAll("\\#\\{upperCase\\}", upperCase);
		return content;
	}
	
	/**
	 * 取得当前存放模板的路径
	 * 
	 * @param file
	 * @return String
	 */
	public String getResource(String file) {
		return this.getClass().getResource(file).getFile();
	}
}
