/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.MultipleFileUploadtwoAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.kernel.util.FileUtil;
import com.hightern.kernel.util.GenerateCode;
import com.hightern.kernel.util.JsonUtil;
import com.hightern.kernel.util.ScaleImage;
import com.hightern.website.model.Information;
import com.hightern.website.model.Menu;
import com.hightern.website.service.InformationService;
import com.hightern.website.service.MenuService;

@Scope("prototype")
@Controller("informationAction")
public class InformationAction extends MultipleFileUploadtwoAction {
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_INFORMATION = "information_manager.htm";
    private static final String ACTION_RELEASE_INFORMATION="information_release.htm";
    private static final String ACTION_SHOWADD_INFORMATION = "information_showAdd.htm";
    private static final String FORWARD_MANAGER_INFORMATION = "/website/manager_information.jsp";
    private static final String FORWARD_RELEASE_INFORMATION="/website/release_information.jsp";
    private static final String FORWARD_SHOWADD_INFORMATION = "/website/add_information.jsp";
    private static final String FORWARD_SHOWEDIT_INFORMATION = "/website/edit_information.jsp";
    private static final String FORWARD_DETAIL_INFORMATION = "/website/detail_information.jsp";
    private static final String FORWARD_TREE_INFORMATION="/website/tree_information_menu.jsp";
    private static final String FORWARD_TREE_INFORMATIONS="/website/tree_information_menus.jsp";
    @Resource(name = "informationService")
    private InformationService informationService;
    @Resource(name = "menuService")
    private MenuService menuService;
    private List<Menu> menus;
    private List<Information> informations;
    private Information information;
    
    private File uploadScalePic;
	private String uploadScalePicFileName;
	private String uploadScalePicContentType;
    
	private String conte;
	
    public String tree(){
    	menus=menuService.findAll();
		setParameters(FORWARD_TREE_INFORMATION);
		return SUCCESS;
    }
    public String trees(){
		menus=menuService.findAll();
		setParameters(FORWARD_TREE_INFORMATIONS);
		return SUCCESS;
	}
    /**
	 * 根据菜单编号取得菜单名称
	 */
	public String ajax() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		if (isNullOrEmptyString(this.getId())) {
			throw new SystemExceptions("菜单编号不可为空！");
		}
		Menu menu = menuService.findById(this.getId());
		if(getConte()==null){
			//列表页面、图文页面不用判断
			if(menu.getPagetype()==1){//父级类型
				response.getWriter().print("1");
			}else if(menu.getPagetype()==2){//单页面
				if(informationService.findInformationByMenu(menu.getId())){
					response.getWriter().print(JsonUtil.getJsonStringJavaPOJO(menu));
				}else{
					response.getWriter().print("2");
				}
			}else{
				response.getWriter().print(JsonUtil.getJsonStringJavaPOJO(menu));
			}
		}else{
			response.getWriter().print(JsonUtil.getJsonStringJavaPOJO(menu));
		}
		response.getWriter().close();
		return null;
	}
    
    /**
     * 保存数据（添加或修改对象）
     */
    public String save() throws SystemExceptions {
        if (null != information) {
            if (information.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_INFORMATION);
            } else {
            	information.setHits(0);
                this.setParameters("数据保存成功！", ACTION_SHOWADD_INFORMATION);
            }
            information.setReleases(1);
            if(getUploadScalePic()!=null){
            	if(information.getPagetype()==4){
            		String path=ServletActionContext.getServletContext().getRealPath("/imgs");
                	File ss = new File(path);
                    if(!ss.exists()){
                    	ss.mkdirs();
                    }
                    String ext=getUploadScalePicFileName().substring(getUploadScalePicFileName().lastIndexOf("."));
                    information.setScalepic("/"+GenerateCode.random()+ext);
                    copy(getUploadScalePic(),new File(path+information.getScalepic()));
            	}else{
            		this.setParameters("图文类型无法上传引导图！", ACTION_SHOWADD_INFORMATION);
            		return SUCCESS;
            	}
            }else{
            	if("".equals(information.getScalepic())){
            		if(information.getPagetype()==4){
                		this.setParameters("当前为图文类型未上传引导图！", ACTION_SHOWADD_INFORMATION);
                		return SUCCESS;
                	}
            	}
            }
            if (getUpload() != null) {
            	String path=ServletActionContext.getServletContext().getRealPath("/annex");
            	File ss = new File(path);
                if(!ss.exists()){
                	ss.mkdirs();
                }
                String fileName=GenerateCode.random();
                copy(getUpload(),new File(path+"/"+fileName));
                information.setAnnexpath("annex/"+fileName);
                information.setFileName(getUploadFileName());
                information.setContentType(getUploadContentType());
            }
            information.setPhoto(1);
            //生成缩略图
            if(information.getContent()!=null){
            	StringBuilder thumbnailpath=new StringBuilder(request.getSession().getServletContext().getRealPath("/"));
        		File thumbnaildir=new File(thumbnailpath.toString()+"/resourcefiles/thumbnail/");
        		if(!thumbnaildir.exists()){
        			thumbnaildir.mkdirs();
        		}
				//下载并生成缩略图
				try{
					//只处理有图片的文章
					int index=information.getContent().indexOf("<img");
					if(index!=-1){
						 information.setPhoto(2);
						String imghtml=information.getContent().substring(index);
						String thumbnail=imghtml.substring(imghtml.indexOf("src=\"")+5);
						//获取图片的路径 
						String imgurl=thumbnail.substring(0,thumbnail.indexOf("\""));
						
						information.setThumbnail("/resourcefiles/thumbnail/"+GenerateCode.random()+imgurl.substring(imgurl.lastIndexOf(".")));
						
						ScaleImage scaleImage=new ScaleImage();
						//表示为本地服务器上的图片
						if(imgurl.startsWith("/")){
							scaleImage.saveImageAsJpg(thumbnailpath+imgurl, thumbnailpath+getInformation().getThumbnail(), 250,250);
						}else{
							//随机生成一个临时文件名称
							File downFile=new File(GenerateCode.random());
							//下载该图片
							FileUtil.downLoadFile(downFile,imgurl);
							scaleImage.saveImageAsJpg(downFile.getAbsolutePath(), thumbnailpath+getInformation().getThumbnail(), 250,250);
							//删除临时文件
							downFile.delete();
						}
					}
				}catch(Exception ex){
					 information.setPhoto(1);
					//一般如果报异常则可能的原因是下载不到图片，服务器上图片不存在
					getInformation().setThumbnail(null);
				}
            }
            informationService.save(information);
        }
        return SUCCESS;
    }
    
    public String download() throws FileNotFoundException{
    	Information information=informationService.findById(getId());
    	String path=ServletActionContext.getServletContext().getRealPath("/");
    	setFileName(information.getFileName());
    	setContentType(information.getContentType());
    	setDownloadFile(new FileInputStream(new File(path+"/"+information.getAnnexpath())));
    	return DOWNLOAD;
    }

    /**
     * 删除操作
     */
    public String delete() throws SystemExceptions {
        if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
        informationService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_INFORMATION);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     */
    public String manager() throws SystemExceptions {
        if(information == null){
            information = new Information(request, null);
        }
        information.setTitle(request.getParameter("title"));
        information.setCreateUserName(request.getParameter("createUserName"));
        information.setCreateUserCode(getCurrentUser().getLoginName());
        String menuname=request.getParameter("menuname");
        if(menuname!=null){
        	information.setMenuname(menuname);
        }
        String menuid=request.getParameter("menuId");
        if(menuid!=null&&!"".equals(menuid)){
        	information.setMenuId(Long.parseLong(menuid));
        }
        String releases=request.getParameter("releases");
        if(releases!=null&&releases.length()>0){
        	information.setReleases(Integer.parseInt(releases));
        }
        informations = informationService.paginated(information);
        this.setParameters(FORWARD_MANAGER_INFORMATION);
        return SUCCESS;
    }
    
    public String release() throws SystemExceptions {
        if(information == null){
            information = new Information(request, null);
        }
        information.setTitle(request.getParameter("title"));
        information.setCreateUserName(request.getParameter("createUserName"));
        String releases=request.getParameter("releases");
        if(releases!=null&&releases.length()>0){
        	information.setReleases(Integer.parseInt(releases));
        }
        String menuname=request.getParameter("menuname");
        if(menuname!=null){
        	information.setMenuname(menuname);
        }
        String menuid=request.getParameter("menuId");
        if(menuid!=null&&!"".equals(menuid)){
        	information.setMenuId(Long.parseLong(menuid));
        }
        informations = informationService.paginated(information);
        this.setParameters(FORWARD_RELEASE_INFORMATION);
        return SUCCESS;
    }
    /**
     * 发布
     */
    public String rerouted(){
    	if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
    	for(Long id:idsStringToList(getSelectedIds())){
    		information=informationService.findById(id);
    		if(information.getReleases()!=2){
    			information.setReleases(2);
    			information.setReleaseTime(DateHelper.getFullDate());
    			informationService.save(information);
    		}
    	}
    	setParameters("发布成功！",ACTION_RELEASE_INFORMATION);
    	return SUCCESS;
    }
    /**
     * 撤消发布
     */
    public String undo(){
    	if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
    	for(Long id:idsStringToList(getSelectedIds())){
    		information=informationService.findById(id);
    		information.setReleases(1);
    		informationService.save(information);
    	}
    	setParameters("撤消成功！",ACTION_RELEASE_INFORMATION);
    	return SUCCESS;
    }
    /**添加导航
     */
    public String showAdd() throws SystemExceptions {
    	getInformation().setCreateTime(DateHelper.getDate());
    	getInformation().setCreateUserCode(getCurrentUser().getLoginName());
    	getInformation().setCreateUserName(getCurrentUser().getCreateName());
        this.setParameters(FORWARD_SHOWADD_INFORMATION);
        return SUCCESS;
    }

    /**编辑导航
     */
    public String showEdit() throws SystemExceptions {
        if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
        information = informationService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_INFORMATION);
        return SUCCESS;
    }

    /**
     *显示详细信息
     */
    public String detail() throws SystemExceptions {
        if (isNullOrEmptyString(getId())) {
            throw new SystemExceptions("编号不能为空！");
        }
        information = informationService.findById(getId());
        this.setParameters(FORWARD_DETAIL_INFORMATION);
        return SUCCESS;
    }
    
    public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public List<Information> getInformations() {
        return informations;
    }

    public void setInformations(List<Information> informations) {
        this.informations = informations;
    }

    public Information getInformation() {
    	if(information == null){
    		information = new Information();
    	}
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }
	public File getUploadScalePic() {
		return uploadScalePic;
	}
	public void setUploadScalePic(File uploadScalePic) {
		this.uploadScalePic = uploadScalePic;
	}
	public String getUploadScalePicFileName() {
		return uploadScalePicFileName;
	}
	public void setUploadScalePicFileName(String uploadScalePicFileName) {
		this.uploadScalePicFileName = uploadScalePicFileName;
	}
	public String getUploadScalePicContentType() {
		return uploadScalePicContentType;
	}
	public void setUploadScalePicContentType(String uploadScalePicContentType) {
		this.uploadScalePicContentType = uploadScalePicContentType;
	}
	public void setConte(String conte) {
		this.conte = conte;
	}
	public String getConte() {
		return conte;
	}
}