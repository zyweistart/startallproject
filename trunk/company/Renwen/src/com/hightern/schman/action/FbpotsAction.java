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
import com.hightern.schman.model.Fbpots;
import com.hightern.schman.model.Fbreply;
import com.hightern.schman.model.Forum;
import com.hightern.schman.service.FbpotsService;
import com.hightern.schman.service.FbreplyService;
import com.hightern.schman.service.ForumService;

@Scope("prototype")
@Controller("fbpotsAction")
public class FbpotsAction extends BaseAction<Fbpots> {

    /**
     * 此FbpotsAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_FBPOTS = "fbpots_manager.htm";
    private static final String ACTION_SHOWADD_FBPOTS = "fbpots_showAdd.htm";
    private static final String FORWARD_MANAGER_FBPOTS = "/schman/manager_fbpots.jsp";
    private static final String FORWARD_LIST_FBPOTS = "/schman/list_fbpots.jsp";
    private static final String FORWARD_SHOWADD_FBPOTS = "/schman/add_fbpots.jsp";
    private static final String FORWARD_SHOWEDIT_FBPOTS = "/schman/edit_fbpots.jsp";
    private static final String FORWARD_DETAIL_FBPOTS = "/schman/detail_fbpots.jsp";
    private static final String FORWARD_VIEW_FBPOTS = "/home/bbsports.jsp";
    private static final String FORWARD_LIST_HOME_FBPOTS = "/home/bbslist.jsp";
    @Resource(name = "fbpotsService")
    private FbpotsService fbpotsService;
    private List<Fbpots> fbpotss;
    private Fbpots fbpots;
    @Resource(name="forumService")
    private ForumService forumService;
    private List<Forum> forums;
    private Forum forum;
    @Resource(name="fbreplyService")
    private FbreplyService fbreplyService;
    private List<Fbreply> fbreplies;
    private Fbreply fbreply;
    
    
    /***
     * 帖子展示页面 (前台)
     * @return
     * @throws SystemExceptions
     */
    public String list_home()throws SystemExceptions{
    	if(fbpots==null){fbpots=new Fbpots(request,null);}
    	fbpots.setPageSize(15);
    	
    	fbpotss=fbpotsService.queryByPage("select o from fbpots o where 1=1  order by o.id desc", fbpots);
    	this.setParameters(FORWARD_LIST_HOME_FBPOTS);
    	return SUCCESS;
    }
    
    
    /***
     * 保存帖子 (前台)
     * @return
     * @throws SystemExceptions
     */
    public String viewSave() throws SystemExceptions{
    	if(this.getStuUser()!=null && this.getStuUser().getId()>0L){
    		if(fbpots!=null){
    			 forum=forumService.findById(fbpots.getForumId());
    			 fbpots.setForumName(forum.getForumName());
    			 fbpots.setUsersId(this.getStuUser().getId());
                 fbpots.setUsersName(this.getStuUser().getUserName()); 			
    			 if(this.getStuUser().getAvatar()!=null && this.getStuUser().getAvatar().length()>0){
                  fbpots.setAvatar(this.getStuUser().getAvatar());
    			 }else{
    			  fbpots.setAvatar("/userfiles/touxiang/cstx.jpg");//填写固定的头像地址
    			 }
    			 fbpots.setStartDay(DateHelper.getFullDate());
    			 fbpots.setPviews("1");
    			 fbpotsService.save(fbpots);
    		}
    	
    		this.setParameters("/common/qisuccess.jsp","已发布，新帖子!","fbpots_list_home.htm");
    	}else{
    		this.setParameters("/common/qisuccess.jsp","用户尚未登陆，请先登陆！",request.getSession().getServletContext().getContextPath()+"/registration.jsp");
    	}
    	return SUCCESS;
    }
    
    
    /***
     * 发布帖子页面 (前台)
     * @return
     * @throws SystemExceptions
     */
    public String view() throws SystemExceptions{
       if(this.getStuUser()!=null && this.getStuUser().getId()>0L){    	
    	if(fbpots==null){fbpots=new Fbpots(request,null);}
    	forums=forumService.findAll();
    	this.setParameters(FORWARD_VIEW_FBPOTS);
       }else{
    	   this.setParameters("/common/qisuccess.jsp","用户尚未登陆，请先登陆！","/home/login.jsp");
       }
    	return SUCCESS;
    }
    
    
    
    
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != fbpots) {
            if (fbpots.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_FBPOTS);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_FBPOTS);
            }
            
            
            fbpotsService.save(fbpots);
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
        //先删除改帖子的回复信息
        for(Long n:idsStringToList(getSelectedIds())){
    	   fbreplies=fbreplyService.forMag(n);
    	   if(fbreplies!=null && fbreplies.size()>0){
    	      for(Fbreply ss:fbreplies){
    		   fbreplyService.remove(ss);
    		   }
    	   }
        }
        
        fbpotsService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_FBPOTS);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(fbpots == null){
            fbpots = new Fbpots(request, null);
        }
        String setFpTitle=(request.getParameter("fpTitle"));
        if(setFpTitle!=null){
        	 fbpots.setFpTitle(setFpTitle);
        }
        fbpots.setUsersName(request.getParameter("usersName"));

        fbpotss = fbpotsService.paginated(fbpots);
        this.setParameters(FORWARD_MANAGER_FBPOTS);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(fbpots == null){
            fbpots = new Fbpots(request, null);
        }
        fbpotss = fbpotsService.paginated(fbpots);
        this.setParameters(FORWARD_LIST_FBPOTS);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_FBPOTS);
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
        fbpots = fbpotsService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_FBPOTS);
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
        fbpots = fbpotsService.findById(getId());
        this.setParameters(FORWARD_DETAIL_FBPOTS);
        return SUCCESS;
    }

    public List<Fbpots> getFbpotss() {
        return fbpotss;
    }

    public void setFbpotss(List<Fbpots> fbpotss) {
        this.fbpotss = fbpotss;
    }

    public Fbpots getFbpots() {
    	if(fbpots == null){
    		fbpots = new Fbpots();
    	}
        return fbpots;
    }

    public void setFbpots(Fbpots fbpots) {
        this.fbpots = fbpots;
    }

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}


	public List<Fbreply> getFbreplies() {
		return fbreplies;
	}


	public void setFbreplies(List<Fbreply> fbreplies) {
		this.fbreplies = fbreplies;
	}


	public Fbreply getFbreply() {
		return fbreply;
	}


	public void setFbreply(Fbreply fbreply) {
		this.fbreply = fbreply;
	}
    
    
    
}