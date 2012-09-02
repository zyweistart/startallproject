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
import com.hightern.schman.service.FbpotsService;
import com.hightern.schman.service.FbreplyService;

@Scope("prototype")
@Controller("fbreplyAction")
public class FbreplyAction extends BaseAction<Fbreply> {

    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_FBREPLY = "fbreply_manager.htm";
    private static final String ACTION_SHOWADD_FBREPLY = "fbreply_showAdd.htm";
    private static final String FORWARD_MANAGER_FBREPLY = "/schman/manager_fbreply.jsp";
    private static final String FORWARD_LIST_FBREPLY = "/schman/list_fbreply.jsp";
    private static final String FORWARD_SHOWADD_FBREPLY = "/schman/add_fbreply.jsp";
    private static final String FORWARD_SHOWEDIT_FBREPLY = "/schman/edit_fbreply.jsp";
    private static final String FORWARD_DETAIL_FBREPLY = "/schman/detail_fbreply.jsp";
    private static final String FORWARD_VIEWCONTENT_FBREPLY = "/home/bbscontent.jsp";
    @Resource(name = "fbreplyService")
    private FbreplyService fbreplyService;
    private List<Fbreply> fbreplys;
    private Fbreply fbreply;
    @Resource(name="fbpotsService")
    private FbpotsService fbpotsService;
    private List<Fbpots> fbpotss;
    private Fbpots fbpots;
    
    private Long fbpostsId;
	private Integer f;//判断是否第一次点击这个帖子
    
	private String rcontent;
	
    
    /***
     * 展示帖子内容
     * @return
     * @throws SystemExceptions
     */
    public String viewContent()throws SystemExceptions{
    	if(getFbpostsId()!=null && getFbpostsId()>0){
			fbpots=fbpotsService.findById(getFbpostsId()); //选择的帖子
			 if(null!=f && f==1){
			  fbpots.setPviews(String.valueOf((Integer.parseInt(fbpots.getPviews())+1)));
		      fbpotsService.save(fbpots);
			 }
			if(fbreply==null){
			fbreply=new Fbreply(request,null);		
             }
			fbreply.setPageSize(5);
			fbreplys=fbreplyService.queryByPage("select o from fbreply o where 1=1 and o.potsId="+fbpots.getId(), fbreply);
		}
    	
    	this.setParameters(FORWARD_VIEWCONTENT_FBREPLY);
    	return SUCCESS;
    }
    
    
    /***
     * 保存回复信息 (前台)
     * @return
     * @throws SystemExceptions
     */
    public String viewSave()throws SystemExceptions{
    	 if(getFbpostsId()!=null && getFbpostsId()>0){
        	 Fbreply rs=new Fbreply(request,null);
        	 fbpots=fbpotsService.findById(getFbpostsId());
        	 rs.setPotsId(fbpots.getId());
        	 rs.setPotsTitle(fbpots.getFpTitle());
        	 rs.setStartDay(DateHelper.getFullDate());
        	 if(this.getStuUser()!=null){
        	 rs.setUserName(this.getStuUser().getUserName());
        	 }else{
        		 rs.setUserName("访客");
        	 }
        	 if(this.getStuUser()!=null && this.getStuUser().getAvatar()!=null && this.getStuUser().getAvatar().length()>0){
        		 rs.setAvatar(this.getStuUser().getAvatar());
   			 }else{
   				rs.setAvatar("/userfiles/touxiang/cstx.jpg");//填写固定的头像地址
   			 }
//        	 rs.setRavatar(request.getParameter("ravatar").toString());
        	 if(null!=getRcontent()){
        	 rs.setRcontent(getRcontent());
        	 }
        	 fbreplyService.save(rs);
    }	
    String url="fbreply_viewContent.htm?fbpostsId="+getFbpostsId();
    if(fbreply!=null){
    	url+="&fbreply.pageNo="+fbreply.getPageNo(); //回复后，位置为当前位置
    }
    this.setParameters("/common/qisuccess.jsp","已成功,回复信息!",url);
    	return SUCCESS;
    }
    
    
    
    
    
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != fbreply) {
            if (fbreply.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_FBREPLY);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_FBREPLY);
            }
            fbreplyService.save(fbreply);
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
        fbreplyService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_FBREPLY);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(fbreply == null){
            fbreply = new Fbreply(request, null);
        }
        fbreply.setPotsTitle(request.getParameter("potsTitle"));
        fbreply.setUserName(request.getParameter("userName"));
        
        fbreplys = fbreplyService.paginated(fbreply);
        
        this.setParameters(FORWARD_MANAGER_FBREPLY);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(fbreply == null){
            fbreply = new Fbreply(request, null);
        }
        fbreplys = fbreplyService.paginated(fbreply);
        this.setParameters(FORWARD_LIST_FBREPLY);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_FBREPLY);
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
        fbreply = fbreplyService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_FBREPLY);
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
        fbreply = fbreplyService.findById(getId());
        this.setParameters(FORWARD_DETAIL_FBREPLY);
        return SUCCESS;
    }

    public List<Fbreply> getFbreplys() {
        return fbreplys;
    }

    public void setFbreplys(List<Fbreply> fbreplys) {
        this.fbreplys = fbreplys;
    }

    public Fbreply getFbreply() {
    	if(fbreply == null){
    		fbreply = new Fbreply();
    	}
        return fbreply;
    }

    public void setFbreply(Fbreply fbreply) {
        this.fbreply = fbreply;
    }

	public List<Fbpots> getFbpotss() {
		return fbpotss;
	}

	public void setFbpotss(List<Fbpots> fbpotss) {
		this.fbpotss = fbpotss;
	}


	public Fbpots getFbpots() {
		return fbpots;
	}


	public void setFbpots(Fbpots fbpots) {
		this.fbpots = fbpots;
	}

	public Long getFbpostsId() {
		return fbpostsId;
	}

	public void setFbpostsId(Long fbpostsId) {
		this.fbpostsId = fbpostsId;
	}

	public Integer getF() {
		return f;
	}

	public void setF(Integer f) {
		this.f = f;
	}


	public String getRcontent() {
		return rcontent;
	}


	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}
    
    
    
}