/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.editor.action;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.editor.model.Kindfile;
import com.hightern.editor.service.KindfileService;

@Scope("prototype")
@Controller("kindfileAction")
public class KindfileAction extends BaseAction<Kindfile> {

    /**
     * 此KindfileAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_KINDFILE = "kindfile_manager.htm";
    private static final String ACTION_SHOWADD_KINDFILE = "kindfile_showAdd.htm";
    private static final String FORWARD_MANAGER_KINDFILE = "/editor/manager_kindfile.jsp";
    private static final String FORWARD_LIST_KINDFILE = "/editor/list_kindfile.jsp";
    private static final String FORWARD_SHOWADD_KINDFILE = "/editor/add_kindfile.jsp";
    private static final String FORWARD_SHOWEDIT_KINDFILE = "/editor/edit_kindfile.jsp";
    private static final String FORWARD_DETAIL_KINDFILE = "/editor/detail_kindfile.jsp";
    @Resource(name = "kindfileService")
    private KindfileService kindfileService;
    private List<Kindfile> kindfiles;
    private Kindfile kindfile;
    
    
    /***
     * 	文件信息(kindEditor上传浏览的信息)
     * @return
     * @throws SystemExceptions
     * @throws IOException 
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String  uploadfile_manager()throws SystemExceptions, IOException{
		try {
			//根目录路径，可以指定绝对路径，比如 /var/www/attached/
			String rootPath = request.getSession().getServletContext().getRealPath("/") + "attached/";
			  File rootFile=new File(rootPath);
			  if(!rootFile.exists()){
				  rootFile.mkdirs();
			  }
			//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
			String rootUrl  = request.getContextPath() + "/attached/";
			//图片扩展名
			String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

			String dirName = request.getParameter("dir");
			if (dirName != null) {
				if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
					response.getWriter().println("Invalid Directory name.");
					return null;
				}
				rootPath += dirName + "/";
				rootUrl += dirName + "/";
				File saveDirFile = new File(rootPath);
				if (!saveDirFile.exists()) {
					saveDirFile.mkdirs();
				}
			}
			//根据path参数，设置各路径和URL
			String path = request.getParameter("path") != null ? request.getParameter("path") : "";
			String currentPath = rootPath + path;
			String currentUrl = rootUrl + path;
			String currentDirPath = path;
			String moveupDirPath = "";
			if (!"".equals(path)) {
				String str = currentDirPath.substring(0, currentDirPath.length() - 1);
				moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
			}

			//排序形式，name or size or type
			String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

			//不允许使用..移动到上一级目录
			if (path.indexOf("..") >= 0) {
				response.getWriter().println("Access is not allowed.");
				return null;
			}
			//最后一个字符不是/
			if (!"".equals(path) && !path.endsWith("/")) {
				response.getWriter().println("Parameter is not valid.");
				return null;
			}
			//目录不存在或不是目录
			File currentPathFile = new File(currentPath);
			if(!currentPathFile.isDirectory()){
				response.getWriter().println("Directory does not exist.");
				return null;
			}

			//遍历目录取的文件信息
			List<Hashtable> fileList = new ArrayList<Hashtable>();
			
			if(currentPathFile.listFiles() != null) {
				for (File file : currentPathFile.listFiles()) {
					Hashtable<String, Object> hash = new Hashtable<String, Object>();
					String fileName = file.getName();
					
					if(file.isDirectory()) {//判断是文件夹
						hash.put("is_dir", true);
						hash.put("has_file", (file.listFiles() != null));
						hash.put("filesize", 0L);
						hash.put("is_photo", false);
						hash.put("filetype", "");
					} else if(file.isFile()){//判断是文件
						String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
						hash.put("is_dir", false);
						hash.put("has_file", false);
						hash.put("filesize", file.length());
						hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
						hash.put("filetype", fileExt);
					}
					hash.put("filename", fileName);
					hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
					if(file.isDirectory()){
						if(this.getCurrentUser()!=null && this.getCurrentUser().getId()>0L){
							//登录用户，判断该用户在该文件夹上是否有上传信息
							if(kindfileService.judgeManager(this.getCurrentUser().getId(), fileName,dirName)){
								fileList.add(hash);
							}
						}else{
							//IP用户(即未登录),判断该用户在该文件夹上是否有上传信息
							String ip = this.getIpAddr();
							if(kindfileService.judgeManager(ip, fileName, dirName)){
								fileList.add(hash);
							}
						}
					}else {
						if(this.getCurrentUser()!=null && this.getCurrentUser().getId()>0L){
							//登录用户，判断该用户在该文件上是否有上传信息
							if(kindfileService.forManager(this.getCurrentUser().getId(), fileName,dirName)){
								fileList.add(hash);
							}
						}else{
							//IP用户(即未登录)，判断该用户在该文件上是否有上传信息
							String ip = this.getIpAddr();
							if(kindfileService.forManager(ip, fileName, dirName)){
								fileList.add(hash);
							}
						}
						
					}
				}
			}
			
			
			if ("size".equals(order)) {
				//按大小排序
				Collections.sort(fileList, new SizeComparator());
			} else if ("type".equals(order)) {
				//按类型排序
				Collections.sort(fileList, new TypeComparator());
			} else {
				//按名称排序(即默认排序)
				Collections.sort(fileList, new NameComparator());
			}
			JSONObject result = new JSONObject();
			result.put("moveup_dir_path", moveupDirPath);
			result.put("current_dir_path", currentDirPath);
			result.put("current_url", currentUrl);
			result.put("total_count", fileList.size());
			result.put("file_list", fileList);

			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().println(result.toJSONString());
	
	
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			response.getWriter().close();
		}
	
	   return null;
	}
	
	

	
	@SuppressWarnings("rawtypes")
	public class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
					return 1;
				} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
			}
		}
	}
	
	
	
	public String getIpAddr() {

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
		return ip;
	}
    
    
    
    
    
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != kindfile) {
            if (kindfile.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_KINDFILE);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_KINDFILE);
            }
            kindfileService.save(kindfile);
        }
        return SUCCESS;
    }

    /**删除操作
     * @return SUCESS
     * @throws SystemExceptions
     */
    public String delete() throws SystemExceptions {
        if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
        for(Long n:idsStringToList(getSelectedIds())){
        	kindfile=kindfileService.findById(n);
        	if(kindfile!=null && kindfile.getFilePath().length()>0 && kindfile.getFileName().length()>0){
//        		String filepath=kindfile.getFilePath();
//        		filepath+=kindfile.getFileName();
        		 File a=new File(ServletActionContext.getServletContext()
							.getRealPath(kindfile.getFilePath()+kindfile.getFileName()));
				  if (a.exists()) {
						a.delete();
					}
        	}
        }
        
        kindfileService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_KINDFILE);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
    	   if(kindfile == null){
               kindfile = new Kindfile(request, null);
           }
            if(this.getCurrentUser()!=null && this.getCurrentUser().getId()>0L){
              String ip=getIpAddr();
              kindfiles = kindfileService.queryByPagetwo("select o from kindfile o where o.userID="+this.getCurrentUser().getId()+" or (o.userID=0 and o.userName='"+ip+"'))",kindfile);
            }else{
           	 kindfiles=kindfileService.queryByPagetwo("select o from kindfile o where o.id=0",kindfile);
            }
           this.setParameters(FORWARD_MANAGER_KINDFILE);
           return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(kindfile == null){
            kindfile = new Kindfile(request, null);
        }
        kindfiles = kindfileService.paginated(kindfile);
        this.setParameters(FORWARD_LIST_KINDFILE);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_KINDFILE);
        return SUCCESS;
    }

    /**编辑导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showEdit() throws SystemExceptions {
        if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
        kindfile = kindfileService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_KINDFILE);
        return SUCCESS;
    }

    /**
     *显示详细信息
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String detail() throws SystemExceptions {
        if (isNullOrEmptyString(getId())) {
            throw new SystemExceptions("编号不能为空！");
        }
        kindfile = kindfileService.findById(getId());
        this.setParameters(FORWARD_DETAIL_KINDFILE);
        return SUCCESS;
    }

    public List<Kindfile> getKindfiles() {
        return kindfiles;
    }

    public void setKindfiles(List<Kindfile> kindfiles) {
        this.kindfiles = kindfiles;
    }

    public Kindfile getKindfile() {
    	if(kindfile == null){
    		kindfile = new Kindfile();
    	}
        return kindfile;
    }

    public void setKindfile(Kindfile kindfile) {
        this.kindfile = kindfile;
    }
}