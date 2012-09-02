/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.action;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.MultipleFileUploadAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.schman.model.Interactive;
import com.hightern.schman.service.InteractiveService;

@Scope("prototype")
@Controller("interactiveAction")
public class InteractiveAction extends MultipleFileUploadAction<Interactive> {

    /**
     * 此InteractiveAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_INTERACTIVE = "interactive_manager.htm";
    private static final String ACTION_SHOWADD_INTERACTIVE = "interactive_showAdd.htm";
    private static final String FORWARD_MANAGER_INTERACTIVE = "/schman/manager_interactive.jsp";
    private static final String FORWARD_LIST_INTERACTIVE = "/schman/list_interactive.jsp";
    private static final String FORWARD_SHOWADD_INTERACTIVE = "/schman/add_interactive.jsp";
    private static final String FORWARD_SHOWEDIT_INTERACTIVE = "/schman/edit_interactive.jsp";
    private static final String FORWARD_DETAIL_INTERACTIVE = "/schman/detail_interactive.jsp";
    private static final String FORWARD_LIST_HOME_INTERACTIVE = "/interMag.jsp";
    @Resource(name = "interactiveService")
    private InteractiveService interactiveService;
    private List<Interactive> interactives;
    private Interactive interactive;
    
    
    /***
     * 展示所有资源  (前台)
     * @return
     * @throws SystemExceptions
     */
    public String list_home()throws SystemExceptions{
    	if(interactive==null){interactive=new Interactive(request,null);}
    	interactive.setPageSize(10);
    	interactives=interactiveService.queryByPage("select o from interactive o  where 1=1  order by o.id desc",interactive);
    	this.setParameters(FORWARD_LIST_HOME_INTERACTIVE);
    	return SUCCESS;
    }    
    
    
    
    /***
     * 下载资源内容  (前台)
     * @return
     * @throws SystemExceptions
     * @throws IOException 
     */
    public String viewContent()throws SystemExceptions, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
    	   if(getId()!=null && getId()>0L){
    		   interactive=interactiveService.findById(getId());
    		   interactive.setDownqty(String.valueOf(Integer.parseInt(interactive.getDownqty())+1));  //下载量 加1
    		 
    		   if(interactive.getXzStrtus()!=null && interactive.getXzStrtus()==2){
    			   if(!(this.getStuUser()!=null && this.getStuUser().getId()>0L) ){
   		    		response.getWriter().print("<script language=\"javascript\">alert(\"用户尚未登陆，请先...\");window.location.href='"+request.getSession().getServletContext().getContextPath()+"/index.jsp'</script>");
   					response.getWriter().close();
   					return SUCCESS;
   		    	   }    
    		   }
    		   interactiveService.save(interactive);
    		   if(interactive.getFilePath()!=null && interactive.getFilePath().length()>0){
    		   this.setParameters("/common/iasuccess.jsp","",request.getSession().getServletContext().getContextPath()+interactive.getFilePath());
    		   }else{
    		   this.setParameters("/common/qisuccess.jsp","下载该资源出错，请联系管理员",request.getSession().getServletContext().getContextPath()+"/index.jsp");   
    		   }
    	   }else{
    	    response.getWriter().print("<script language=\"javascript\">alert(\"请正常操作下载资源...\");window.location.href='"+request.getSession().getServletContext().getContextPath()+"/index.jsp'</script>");
   			response.getWriter().close(); 
    	   }
    	
    	return SUCCESS;
    }
    
    
    
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != interactive) {
        	File ss = new File(ServletActionContext.getServletContext()
					.getRealPath("/userfiles/interactivefile"));
			if (!ss.exists()) {
				ss.mkdir();
			}
            if (interactive.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_INTERACTIVE);
                if (getUpload() != null) {
                	// 删除存在的文件
					File aa = new File(ServletActionContext.getServletContext()
							.getRealPath(interactive.getFilePath()));
					if (aa.exists()) {
						aa.delete();
					}
					String date=String.valueOf(new Date().getTime());
					this.setUploadFileName(date
							+ getExtention(this.getUploadFileName()));
					File filePath = new File(ServletActionContext
							.getServletContext()
							.getRealPath("/userfiles/interactivefile")
							+ "/" + this.getUploadFileName());
					copy(getUpload(), filePath);
					interactive.setFilePath("/userfiles/interactivefile/"
							+ getUploadFileName());
					interactive.setStartDay(DateHelper.getFullDate());
					interactive.setDownqty("0");
                }
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_INTERACTIVE);
                //文件上传
                if(getUpload()!=null){
                	String date=String.valueOf(new Date().getTime());
                	this.setUploadFileName(date
							+ getExtention(this.getUploadFileName()));
                	File filePath = new File(ServletActionContext
							.getServletContext()
							.getRealPath("/userfiles/interactivefile")
							+ "/" + this.getUploadFileName());
					copy(getUpload(), filePath);      
					interactive.setStartDay(DateHelper.getFullDate());
                	interactive.setFilePath("/userfiles/interactivefile/"
							+ getUploadFileName());
                	interactive.setDownqty("0");
                }
            }
            interactiveService.save(interactive);
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
        //删除上传的文件
        for(Long n:idsStringToList(getSelectedIds())){
        	interactive=interactiveService.findById(n);
        	if(interactive!=null && interactive.getFilePath()!=null &&  interactive.getFilePath().length()>0){
        		File aa = new File(ServletActionContext.getServletContext()
						.getRealPath(interactive.getFilePath()));
				if (aa.exists()) {
					aa.delete();
				}
        	}
        }
        
        interactiveService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_INTERACTIVE);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(interactive == null){
            interactive = new Interactive(request, null);
        }
        interactives = interactiveService.paginated(interactive);
        this.setParameters(FORWARD_MANAGER_INTERACTIVE);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(interactive == null){
            interactive = new Interactive(request, null);
        }
        interactives = interactiveService.paginated(interactive);
        this.setParameters(FORWARD_LIST_INTERACTIVE);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_INTERACTIVE);
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
        interactive = interactiveService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_INTERACTIVE);
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
        interactive = interactiveService.findById(getId());
        this.setParameters(FORWARD_DETAIL_INTERACTIVE);
        return SUCCESS;
    }

    public List<Interactive> getInteractives() {
        return interactives;
    }

    public void setInteractives(List<Interactive> interactives) {
        this.interactives = interactives;
    }

    public Interactive getInteractive() {
    	if(interactive == null){
    		interactive = new Interactive();
    	}
        return interactive;
    }

    public void setInteractive(Interactive interactive) {
        this.interactive = interactive;
    }
}