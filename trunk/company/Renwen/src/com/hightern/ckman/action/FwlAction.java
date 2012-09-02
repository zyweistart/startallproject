/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.ckman.action;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.ckman.model.Fwl;
import com.hightern.ckman.service.FwlService;

@Scope("prototype")
@Controller("fwlAction")
public class FwlAction extends BaseAction<Fwl> {

    /**
     * 此FwlAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_FWL = "fwl_manager.htm";
    private static final String ACTION_SHOWADD_FWL = "fwl_showAdd.htm";
    private static final String FORWARD_MANAGER_FWL = "/ckman/manager_fwl.jsp";
    private static final String FORWARD_LIST_FWL = "/ckman/list_fwl.jsp";
    private static final String FORWARD_SHOWADD_FWL = "/ckman/add_fwl.jsp";
    private static final String FORWARD_SHOWEDIT_FWL = "/ckman/edit_fwl.jsp";
    private static final String FORWARD_DETAIL_FWL = "/ckman/detail_fwl.jsp";
    private static final String FF = "/ckman/ii.jsp";
    @Resource(name = "fwlService")
    private FwlService fwlService;
    private List<Fwl> fwls;
    private Fwl fwl;
    
    /***
     * 统计流量 (财智交易网站)
     * @return SUCCESS
     * @throws SystemExceptions
     * @throws IOException 
     */
	public String ajax() throws SystemExceptions, IOException{
		      fwl=fwlService.findById(1L);
		      fwl.setLs(fwl.getLs()+1);
		      fwl = fwlService.save(fwl);
		      this.setParameters(FF);
//        wzll=wzllService.findById(new Long(1));
//    	response.getWriter().print(wzll.getSl());
//		response.getWriter().close();
    	return SUCCESS;
    	
    }
    
    
	
	   /***
     * 统计流量
     * @return SUCCESS
     * @throws SystemExceptions
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
	public String zajax() throws SystemExceptions, IOException{
    	
     if(session.get("fwsl")==null){
			  
			  session.put("fwsl", "visits");
		     fwl=fwlService.findById(new Long(2));
		      if(fwl!=null)
		      {
		    	 if(fwl.getLs()==null){fwl.setLs(new Long(1));}
		    	 fwl.setLs( fwl.getLs()+1);
		    	 fwlService.save(fwl);
		    	 
		      }
		      else
		      {
		    	  fwl=new Fwl(request,null);
		    	  fwl.setLs(new Long(1));
		    	  fwlService.save(fwl);
		      }
		  }
//        wzll=wzllService.findById(new Long(1));
//    	response.getWriter().print(wzll.getSl());
//		response.getWriter().close();
    	return null;
    	
    }
	
	
	
	
	
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != fwl) {
            if (fwl.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_FWL);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_FWL);
            }
            fwlService.save(fwl);
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
        fwlService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_FWL);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(fwl == null){
            fwl = new Fwl(request, null);
        }
        fwls = fwlService.paginated(fwl);
        this.setParameters(FORWARD_MANAGER_FWL);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
    	 if(fwl == null){
              fwl = new Fwl(request, null);
         }
     	 fwl.setLs(Long.parseLong(request.getParameter("ls")));
        System.out.println(request.getParameter("ss"));
        fwlService.save(fwl);
    	
    	if(fwl == null){
            fwl = new Fwl(request, null);
        }
        fwls = fwlService.paginated(fwl);
        this.setParameters(FORWARD_LIST_FWL);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_FWL);
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
        fwl = fwlService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_FWL);
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
        fwl = fwlService.findById(getId());
        this.setParameters(FORWARD_DETAIL_FWL);
        return SUCCESS;
    }

    
    
    
    
    
    public List<Fwl> getFwls() {
        return fwls;
    }

    public void setFwls(List<Fwl> fwls) {
        this.fwls = fwls;
    }

    public Fwl getFwl() {
    	if(fwl == null){
    		fwl = new Fwl();
    	}
        return fwl;
    }

    public void setFwl(Fwl fwl) {
        this.fwl = fwl;
    }
}