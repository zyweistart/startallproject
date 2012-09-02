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
import com.hightern.schman.model.Forum;
import com.hightern.schman.service.ForumService;

@Scope("prototype")
@Controller("forumAction")
public class ForumAction extends BaseAction<Forum> {

    /**
     * 此ForumAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_FORUM = "forum_manager.htm";
    private static final String ACTION_SHOWADD_FORUM = "forum_showAdd.htm";
    private static final String FORWARD_MANAGER_FORUM = "/schman/manager_forum.jsp";
    private static final String FORWARD_LIST_FORUM = "/schman/list_forum.jsp";
    private static final String FORWARD_SHOWADD_FORUM = "/schman/add_forum.jsp";
    private static final String FORWARD_SHOWEDIT_FORUM = "/schman/edit_forum.jsp";
    private static final String FORWARD_DETAIL_FORUM = "/schman/detail_forum.jsp";
    @Resource(name = "forumService")
    private ForumService forumService;
    private List<Forum> forums;
    private Forum forum;
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != forum) {
        	
            if (forum.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_FORUM);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_FORUM);
            }
            forumService.save(forum);
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
        forumService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_FORUM);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(forum == null){
            forum = new Forum(request, null);
        }
        forums = forumService.paginated(forum);
        this.setParameters(FORWARD_MANAGER_FORUM);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(forum == null){
            forum = new Forum(request, null);
        }
        forums = forumService.paginated(forum);
        this.setParameters(FORWARD_LIST_FORUM);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_FORUM);
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
        forum = forumService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_FORUM);
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
        forum = forumService.findById(getId());
        this.setParameters(FORWARD_DETAIL_FORUM);
        return SUCCESS;
    }

    
    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public Forum getForum() {
    	if(forum == null){
    		forum = new Forum();
    	}
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}