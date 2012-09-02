package com.hightern.test.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//Mysql数据库备份 处理

public class bfDB {

	private  String username;
	private String password;
	private String host;
	private String PORT;
	private String dbname;
	private String lj;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		bfDB aa=new bfDB();
		 aa.config();
		backup(aa.getUsername(),aa.getPassword(),aa.getHost(),aa.getPORT(),aa.getDbname(),aa.getLj()); // 数据库备份
//		load(); // 数据库还原
	}

	/***
	 * 
	 * @param username  用户
	 * @param password  密码
	 * @param host     IP地址  (值为null 即为本地)
	 * @param post     (占时无用)
	 * @param dbname   数据源名
	 * @param lj     保存地址
	 */
	//备份
	public static void backup(String username,String password,String host,String post,String dbname,String lj) {
		try {
			Runtime rt = Runtime.getRuntime();
			// 调用 mysql 的 cmd:
			Process child;
			  if(host==null){
				  child = rt.exec("mysqldump -u"+username+" -p"+password+" "+ dbname);
			  }else{
				  child = rt.exec("mysqldump -u"+username+" -p"+password+" "+ " -h "+host+" "+dbname);
			  }
			
			// 设置导出编码为utf8。这里必须是utf8
			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份件。
			
			InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
			InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			// 要用来做导入用的sql目标文件E:\\test5.sql FileOutputStream fout=new
			// FileOutputStream( "E:\\test5.sql");
			 FileOutputStream fout=new FileOutputStream(lj); 
			OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
			writer.write(outStr);
			writer.flush();
			// 关闭输入输出流
			in.close();
			xx.close();
			br.close();
			writer.close();
			fout.close();
			System.out.println("/* Output OK! */");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 导入 ，需要先建立好目标数据库。
	 * @param username  用户
	 * @param password  密码
	 * @param host      IP地址 (值为null 即为本地)
	 * @param dbname    数据源名
	 * @param lj       文件路径
	 */
	public static void load(String username,String password,String host,String dbname,String lj) {
		try {

			String fPath = lj;
			Runtime rt = Runtime.getRuntime();
			// 调用 mysql 的 cmd:
			Process child ;
			  if(host==null){
				  child = rt.exec("mysql -u"+username+" -p"+password+" "+ dbname);
			  }else{
				  child = rt.exec("mysql -u"+username+" -p"+password+" "+ " -h "+host+" "+dbname);
			  }
			OutputStream out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fPath), "utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			writer.flush();
			// 别忘记关闭输入输出流
			out.close();
			br.close();
			writer.close();
			System.out.println("/* Load OK! */");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public  void config() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ComboPooledDataSource ba = (ComboPooledDataSource) context
				.getBean("dataSource");
		setUsername(ba.getUser());
		setPassword(ba.getPassword());
		
		String url = ba.getJdbcUrl();
		url = url.substring(13, url.length());

		String temp=url.substring(0,url.indexOf("/"));
		System.out.println("temp=" + temp);
		setHost(temp.substring(0,temp.indexOf(":")));
		setPORT(temp.substring(temp.indexOf(":")+1));
		url = url.substring(url.indexOf("/") + 1);
		
		setDbname(url.substring(0, url.indexOf("?")));

		String backPath = System.getProperty("user.dir")
				+ "\\WebRoot\\db\\mysql\\" + System.currentTimeMillis() + ".sql";
		setLj(backPath);
		System.out.println("username="+getUsername());
		System.out.println("password="+getPassword());
		System.out.println("host="+getHost());
		System.out.println("PORT="+getPORT());
		System.out.println("dbname="+getDbname());
		System.out.println("lj="+getLj());
	}


	
	

	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	public String getPORT() {
		return PORT;
	}



	public void setPORT(String pORT) {
		PORT = pORT;
	}



	public String getDbname() {
		return dbname;
	}



	public void setDbname(String dbname) {
		this.dbname = dbname;
	}



	public String getLj() {
		return lj;
	}



	public void setLj(String lj) {
		this.lj = lj;
	}


	
	
}
