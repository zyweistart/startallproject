/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.ckman.action;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.ckman.model.Gjz;
import com.hightern.ckman.service.GjzService;

@Scope("prototype")
@Controller("gjzAction")
public class GjzAction extends BaseAction<Gjz> {

    /**
     * 此GjzAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_GJZ = "gjz_manager.htm";
    private static final String ACTION_SHOWADD_GJZ = "gjz_showAdd.htm";
    private static final String FORWARD_MANAGER_GJZ = "/ckman/manager_gjz.jsp";
    private static final String FORWARD_LIST_GJZ = "/ckman/list_gjz.jsp";
    private static final String FORWARD_SHOWADD_GJZ = "/ckman/add_gjz.jsp";
    private static final String FORWARD_SHOWEDIT_GJZ = "/ckman/edit_gjz.jsp";
    private static final String FORWARD_DETAIL_GJZ = "/ckman/detail_gjz.jsp";
    @Resource(name = "gjzService")
    private GjzService gjzService;
    private List<Gjz> gjzs;
    private Gjz gjz;
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != gjz) {
            if (gjz.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_GJZ);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_GJZ);
            }
            gjzService.save(gjz);
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
        gjzService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_GJZ);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(gjz == null){
            gjz = new Gjz(request, null);
        }
        gjzs = gjzService.paginated(gjz);
        this.setParameters(FORWARD_MANAGER_GJZ);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(gjz == null){
            gjz = new Gjz(request, null);
        }
        gjzs = gjzService.paginated(gjz);
        this.setParameters(FORWARD_LIST_GJZ);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_GJZ);
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
        gjz = gjzService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_GJZ);
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
        gjz = gjzService.findById(getId());
        this.setParameters(FORWARD_DETAIL_GJZ);
        return SUCCESS;
    }

    public List<Gjz> getGjzs() {
        return gjzs;
    }

    public void setGjzs(List<Gjz> gjzs) {
        this.gjzs = gjzs;
    }

    public Gjz getGjz() {
    	if(gjz == null){
    		gjz = new Gjz();
    	}
        return gjz;
    }

    public void setGjz(Gjz gjz) {
        this.gjz = gjz;
    }
}