/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.action;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.consult.model.Cpymag;
import com.hightern.consult.service.CpymagService;
//公司联系方式
@Scope("prototype")
@Controller("cpymagAction")
public class CpymagAction extends BaseAction<Cpymag> {

    /**
     * 此CpymagAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_CPYMAG = "cpymag_manager.htm";
    private static final String ACTION_SHOWADD_CPYMAG = "cpymag_showAdd.htm";
    private static final String FORWARD_MANAGER_CPYMAG = "/consult/manager_cpymag.jsp";
    private static final String FORWARD_LIST_CPYMAG = "/consult/list_cpymag.jsp";
    private static final String FORWARD_SHOWADD_CPYMAG = "/consult/add_cpymag.jsp";
    private static final String FORWARD_SHOWEDIT_CPYMAG = "/consult/edit_cpymag.jsp";
    private static final String FORWARD_DETAIL_CPYMAG = "/consult/detail_cpymag.jsp";
    @Resource(name = "cpymagService")
    private CpymagService cpymagService;
    private List<Cpymag> cpymags;
    private Cpymag cpymag;
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != cpymag) {
            if (cpymag.getId() > 0) {
                this.setParameters("/common/xsuccess.jsp","数据提交成功！", "cpymag_detail.htm");
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_CPYMAG);
            }
            cpymags=cpymagService.findAll();
            if(cpymags.size()<=1 && cpymags!=null){
              cpymagService.save(cpymag);
            }
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
        cpymagService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_CPYMAG);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(cpymag == null){
            cpymag = new Cpymag(request, null);
        }
        cpymags = cpymagService.paginated(cpymag);
        this.setParameters(FORWARD_MANAGER_CPYMAG);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(cpymag == null){
            cpymag = new Cpymag(request, null);
        }
        cpymags = cpymagService.paginated(cpymag);
        this.setParameters(FORWARD_LIST_CPYMAG);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_CPYMAG);
        return SUCCESS;
    }

    /**编辑导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showEdit() throws SystemExceptions {
        if (isNullOrEmptyString(getId())) {
        	cpymags=cpymagService.findAll();
        	if(cpymags==null || cpymags.size()<=0){
        		if(cpymag==null){
            		cpymag = new Cpymag(request, null);
            		cpymagService.save(cpymag);
            	  }
        	}else{
        		cpymag=cpymags.get(0);
        	}
        	setId(cpymag.getId());
        }
        cpymag = cpymagService.findById(getId());
        this.setParameters(FORWARD_SHOWEDIT_CPYMAG);
        return SUCCESS;
    }

    /**
     *显示详细信息
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String detail() throws SystemExceptions {
        if (isNullOrEmptyString(getId())) {
        	cpymags=cpymagService.findAll();
        	if(cpymags==null || cpymags.size()<=0){
        		if(cpymag==null){
            		cpymag = new Cpymag(request, null);
            		cpymagService.save(cpymag);
            	  }
        	}else{
        		cpymag=cpymags.get(0);
        	}
        	setId(cpymag.getId());
        }
        cpymag = cpymagService.findById(getId());
        this.setParameters(FORWARD_DETAIL_CPYMAG);
        return SUCCESS;
    }

    public List<Cpymag> getCpymags() {
        return cpymags;
    }

    public void setCpymags(List<Cpymag> cpymags) {
        this.cpymags = cpymags;
    }

    public Cpymag getCpymag() {
    	if(cpymag == null){
    		cpymag = new Cpymag();
    	}
        return cpymag;
    }

    public void setCpymag(Cpymag cpymag) {
        this.cpymag = cpymag;
    }
}