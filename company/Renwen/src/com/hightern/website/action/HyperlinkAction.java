/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.website.action;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.website.model.Hyperlink;
import com.hightern.website.service.HyperlinkService;

@Scope("prototype")
@Controller("hyperlinkAction")
public class HyperlinkAction extends BaseAction<Hyperlink> {

    /**
     * 此HyperlinkAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_HYPERLINK = "hyperlink_manager.htm";
    private static final String ACTION_SHOWADD_HYPERLINK = "hyperlink_showAdd.htm";
    private static final String FORWARD_MANAGER_HYPERLINK = "/website/manager_hyperlink.jsp";
    private static final String FORWARD_SHOWADD_HYPERLINK = "/website/add_hyperlink.jsp";
    private static final String FORWARD_SHOWEDIT_HYPERLINK = "/website/edit_hyperlink.jsp";
    @Resource(name = "hyperlinkService")
    private HyperlinkService hyperlinkService;
    private List<Hyperlink> hyperlinks;
    private Hyperlink hyperlink;
    
    /**保存数据（添加或修改对象）
     */
    public String save() throws SystemExceptions {
        if (null != hyperlink) {
            if (hyperlink.getId() > 0) {
            	if(hyperlink.getName()==null||"".equals(hyperlink.getName().trim())){
            		this.setParameters("请输入链接名称！", ACTION_MANAGER_HYPERLINK);
            		return SUCCESS;
            	}
            	if(hyperlink.getUrl()==null||"".equals(hyperlink.getUrl().trim())){
            		this.setParameters("请输入链接地址！", ACTION_MANAGER_HYPERLINK);
            		return SUCCESS;
            	}
            	if(!hyperlink.getUrl().toLowerCase().startsWith("http://")){
            		this.setParameters("链接地址必须以http://开头请核实！", ACTION_MANAGER_HYPERLINK);
            		return SUCCESS;
            	}
                this.setParameters("数据编辑成功！", ACTION_MANAGER_HYPERLINK);
            } else {
            	if(hyperlink.getName()==null||"".equals(hyperlink.getName().trim())){
            		this.setParameters("请输入链接名称！", ACTION_SHOWADD_HYPERLINK);
            		return SUCCESS;
            	}
            	if(hyperlink.getUrl()==null||"".equals(hyperlink.getUrl().trim())){
            		this.setParameters("请输入链接地址！", ACTION_SHOWADD_HYPERLINK);
            		return SUCCESS;
            	}
            	if(!hyperlink.getUrl().toLowerCase().startsWith("http://")){
            		this.setParameters("链接地址必须以http://开头请核实！", ACTION_SHOWADD_HYPERLINK);
            		return SUCCESS;
            	}
                this.setParameters("数据保存成功！", ACTION_SHOWADD_HYPERLINK);
            }
            hyperlinkService.save(hyperlink);
        }
        return SUCCESS;
    }

    /**删除操作
     */
    public String delete() throws SystemExceptions {
        if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
        hyperlinkService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_HYPERLINK);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     */
    public String manager() throws SystemExceptions {
        if(hyperlink == null){
            hyperlink = new Hyperlink(request, null);
        }
        hyperlinks = hyperlinkService.paginated(hyperlink);
        this.setParameters(FORWARD_MANAGER_HYPERLINK);
        return SUCCESS;
    }
    
    /**添加导航
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_HYPERLINK);
        return SUCCESS;
    }

    /**编辑导航
     */
    public String showEdit() throws SystemExceptions {
        if (isNullOrEmptyString(getSelectedIds())) {
            throw new SystemExceptions("编号不能为空！");
        }
        hyperlink = hyperlinkService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_HYPERLINK);
        return SUCCESS;
    }

    public List<Hyperlink> getHyperlinks() {
        return hyperlinks;
    }

    public void setHyperlinks(List<Hyperlink> hyperlinks) {
        this.hyperlinks = hyperlinks;
    }

    public Hyperlink getHyperlink() {
    	if(hyperlink == null){
    		hyperlink = new Hyperlink();
    	}
        return hyperlink;
    }

    public void setHyperlink(Hyperlink hyperlink) {
        this.hyperlink = hyperlink;
    }
}