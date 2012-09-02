package com.hightern.editor.servlet;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hightern.editor.model.Kindfile;
import com.hightern.editor.util.KindfileInfo;
import com.hightern.kernel.util.DateHelper;
import com.hightern.system.model.Operator;
/**
 * Servlet implementation class fileEditor
 */

//上传文件(kindEditor在线编辑器)
public class fileEditor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private KindfileInfo kindfileInfo;
	private Kindfile kindfile;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fileEditor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		out = response.getWriter();
		Operator operator=(Operator) request.getSession().getAttribute("currentUser"); 
		kindfile=new Kindfile(request,null);
		kindfileInfo=KindfileInfo.getFromApplicationContext(WebApplicationContextUtils.getWebApplicationContext(getServletContext()));
		try {
		
		//文件保存目录路径
		String savePath = request.getSession().getServletContext().getRealPath("/") + "attached/";
		  File rootFile=new File(savePath);
		  if(!rootFile.exists()){
			  rootFile.mkdirs();
		  }
		//文件保存目录URL
		String saveUrl  = request.getContextPath() + "/attached/";

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		//最大文件大小
		long maxSize = 1000000000;

		response.setContentType("text/html; charset=UTF-8");

		if(!ServletFileUpload.isMultipartContent(request)){
			out.println(getError("请选择文件。"));
			return;
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			out.println(getError("上传目录不存在。"));
			return;
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			out.println(getError("上传目录没有写权限。"));
			return;
		}

		String dirName = request.getParameter("dir");
		kindfile.setFileType(dirName);//文件类型(保存到表中)
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			out.println(getError("目录名不正确。"));
			return;
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		kindfile.setFileDate(ymd);//文件上传日期(即保存到那个目录，保存到表中)
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		
		kindfile.setFilePath("/attached/"+dirName+"/"+ymd + "/");   //文件路径(保存到表中)
		
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items=new ArrayList();
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			BufferedInputStream bis=null;
			BufferedReader br=null;
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				//检查文件大小
				if(item.getSize() > maxSize){
					out.println(getError(item.getSize()+"上传文件大小超过限制。"));
					return;
				}
				kindfile.setFileSize(String.valueOf(item.getSize()));//文件大小(保存到表中)
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
					out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
					return;
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try{
					File uploadedFile = new File(savePath, newFileName);
					 bis= new BufferedInputStream(item.getInputStream());
						BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(uploadedFile));
						byte[] buffer = new byte[2048]; 
						int len = 0; 
						while ((len = bis.read(buffer)) > 0) 
						{ 
							bos.write(buffer , 0 , len); 
							bos.flush();
						} 
						bos.close();
						bis.close();
					kindfile.setFileName(newFileName);//文件名(保存到表中)
					
					kindfile.setFileTime(DateHelper.getTime());//文件上传时间(保存到表中)
					 if(operator!=null && operator.getId()>0L){
						 kindfile.setUserID(operator.getId()); //上传者ID(保存到表中)
						 kindfile.setUserName(operator.getLoginName());//上传者名称(保存到表中)
					 }else{
							String ip = request.getHeader("x-forwarded-for");
							if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
								ip = request.getHeader("Proxy-Client-IP");
							}
							if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
								ip = request.getHeader("WL-Proxy-Client-IP");
							}
							if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
								ip = request.getRemoteAddr();
							}
						 kindfile.setUserID(new Long(0));//上传者未登录ID(保存到表中)
						 kindfile.setUserName(ip);//上传者未登录(保存到表中)
					 }
					 kindfileInfo.save(kindfile);
//					item.write(uploadedFile);
					
				}catch(Exception e){
					out.println(getError("上传文件失败。"));
					return;
				}

				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				out.println(obj.toJSONString());
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			out.close();
		}
	}

	@SuppressWarnings("unchecked")
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

	public Kindfile getKindfile() {
		return kindfile;
	}

	public void setKindfile(Kindfile kindfile) {
		this.kindfile = kindfile;
	}


}
