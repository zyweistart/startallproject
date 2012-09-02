/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.action;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.schman.model.Fdbmag;
import com.hightern.schman.service.FdbmagService;

@Scope("prototype")
@Controller("fdbmagAction")
public class FdbmagAction extends BaseAction<Fdbmag> {

    /**
     * 此FdbmagAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_FDBMAG = "fdbmag_manager.htm";
    private static final String ACTION_SHOWADD_FDBMAG = "fdbmag_showAdd.htm";
    private static final String FORWARD_MANAGER_FDBMAG = "/schman/manager_fdbmag.jsp";
    private static final String FORWARD_LIST_FDBMAG = "/schman/list_fdbmag.jsp";
    private static final String FORWARD_SHOWADD_FDBMAG = "/schman/add_fdbmag.jsp";
    private static final String FORWARD_SHOWEDIT_FDBMAG = "/schman/edit_fdbmag.jsp";
    private static final String FORWARD_DETAIL_FDBMAG = "/schman/detail_fdbmag.jsp";
    @Resource(name = "fdbmagService")
    private FdbmagService fdbmagService;
    private List<Fdbmag> fdbmags;
    private Fdbmag fdbmag;
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != fdbmag) {
            if (fdbmag.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_FDBMAG);
            } else {
            	fdbmag.setSrartDay(DateHelper.getFullDate());
            	if(this.getStuUser().getUname()!=null && this.getStuUser().getUname().length()>0){
            		fdbmag.setFauthor(this.getStuUser().getUname());
            		}else{
            		fdbmag.setFauthor("访客");
            			}
                this.setParameters("数据保存成功！", ACTION_SHOWADD_FDBMAG);
            }
            fdbmagService.save(fdbmag);
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
        fdbmagService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_FDBMAG);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(fdbmag == null){
            fdbmag = new Fdbmag(request, null);
        }
        fdbmags = fdbmagService.paginated(fdbmag);
        this.setParameters(FORWARD_MANAGER_FDBMAG);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(fdbmag == null){
            fdbmag = new Fdbmag(request, null);
        }
        fdbmags = fdbmagService.paginated(fdbmag);
        this.setParameters(FORWARD_LIST_FDBMAG);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_FDBMAG);
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
        fdbmag = fdbmagService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_FDBMAG);
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
        fdbmag = fdbmagService.findById(getId());
        this.setParameters(FORWARD_DETAIL_FDBMAG);
        return SUCCESS;
    }

    /***
     *  前台添加页面 (用户留言)
     * @return  SUCCESS
     * @throws SystemExceptions
     */
    public String view() throws  SystemExceptions{
    	if(fdbmag==null){fdbmag=new Fdbmag(request,null);}
    	fdbmag.setReplyStatus(2);
    	this.setParameters("/fkMessage.jsp");
    	return SUCCESS;
    }
    
    
    
    
    public List<Fdbmag> getFdbmags() {
        return fdbmags;
    }

    public void setFdbmags(List<Fdbmag> fdbmags) {
        this.fdbmags = fdbmags;
    }

    public Fdbmag getFdbmag() {
    	if(fdbmag == null){
    		fdbmag = new Fdbmag();
    	}
        return fdbmag;
    }

    public void setFdbmag(Fdbmag fdbmag) {
        this.fdbmag = fdbmag;
    }
}