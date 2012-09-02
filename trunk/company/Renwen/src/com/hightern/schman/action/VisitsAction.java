/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.schman.action;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.schman.model.Users;
import com.hightern.schman.model.Visits;
import com.hightern.schman.model.Visitsmag;
import com.hightern.schman.service.UsersService;
import com.hightern.schman.service.VisitsService;

@Scope("prototype")
@Controller("visitsAction")
public class VisitsAction extends BaseAction<Visits> {

    /**
     * 此VisitsAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_VISITS = "visits_manager.htm";
    private static final String ACTION_SHOWADD_VISITS = "visits_showAdd.htm";
    private static final String FORWARD_MANAGER_VISITS = "/schman/manager_visits.jsp";
    private static final String FORWARD_LIST_VISITS = "/schman/list_visits.jsp";
    private static final String FORWARD_SHOWADD_VISITS = "/schman/add_visits.jsp";
    private static final String FORWARD_SHOWEDIT_VISITS = "/schman/edit_visits.jsp";
    private static final String FORWARD_DETAIL_VISITS = "/schman/detail_visits.jsp";
    @Resource(name = "visitsService")
    private VisitsService visitsService;
	@Resource(name = "usersService")
	private UsersService usersService;
    private List<Visits> visitss;
    private List<Users> userss;
    private Visitsmag  visitsmag;
    private Visits visits;
	private Users users;
    
    private Long userId;
    
    /****
     * 获取用户信息
     * @return
     * @throws SystemExceptions
     */
    public String viewVisits()throws SystemExceptions{
    	if(this.getUserId()!=null && this.getUserId()>0L){
    	   users=usersService.findById(this.getUserId());
    	   visitsmag=new Visitsmag();
    	   visitsmag.setUserName(users.getUname());
           visitsmag.setVisitsMax(visitsService.hqvisitsMax(1, users.getId()));        //总访问时间
    	   visitsmag.setVisitsSize(visitsService.hqsizeMax(1, users.getId()));         //总访问次数
    	   visitsmag.setVisitsExtmMax(visitsService.hqvisitsMax(2, users.getId()));    //总测试库访问时间
    	   visitsmag.setVisitsExtmSize(visitsService.hqsizeMax(2, users.getId()));     //总测试库访问次数
    	     
    	   visitss=visitsService.hqvisitsMag(1, users.getId());
    	   
    	}
    	this.setParameters("/schman/viewVisits.jsp");
    	return SUCCESS;
    }
    
    
    
    /***
     *  进入测试库花费的时间
     * @return
     * @throws SystemExceptions
     * @throws IOException
     */
    public String extractin_ajax()throws SystemExceptions, IOException{
    	response.setContentType("text/html;charset=utf-8");
    	if(this.getStuUser()!=null && this.getStuUser().getId()>0L  && this.getStuUser().getExtractionStartDay()!=null && this.getStuUser().getExtractionStartDay().length()>0L){
    		visits=new Visits(request,null);
    		visits.setType(2);
    		visits.setStartDay(this.getStuUser().getExtractionStartDay());
    		visits.setUserID(this.getStuUser().getId());
    		visits.setUserName(this.getStuUser().getUserName());
    		SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    		Date endDate;
    		Date startDate;
    		try {
				  endDate=df.parse(DateHelper.getFullDate());
					
				 startDate = df.parse(this.getStuUser().getExtractionStartDay());
				 long   seconds   =   (endDate.getTime()-   startDate.getTime())/1000; 

				 long   date   =   seconds/(24*60*60);     //相差的天数 
				 long   hour   =   (seconds-date*24*60*60)/(60*60);//相差的小时数 
				 
				 long   minut   =   (seconds-date*24*60*60-hour*60*60)/(60);//相差的分钟数 
				
				 long   second   =   (seconds-date*24*60*60-hour*60*60-minut*60);//相差的秒数 
				 
				 if(!(minut>0L) && second>30L){
					 minut=1;
				 }
				 
				 if(minut>0L){
					  DecimalFormat   ss   =   new   DecimalFormat( "#0.00 ");   
					  float max=new Float(date)*new Float(24)+new Float(hour)+(new Float(minut)/new Float(60));
					  visits.setRecordSize(String.valueOf(ss.format(max))); 
				  }else{
				     visits.setRecordSize(String.valueOf(date*24+hour));   
				  }
				
				 visitsService.save(visits);
				  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    	response.getWriter().print("1");
    	response.getWriter().close();
    	return null;
    }
    
    
    
    /***
     * 保存时间
     * @return
     * @throws SystemExceptions
     * @throws IOException 
     */
    public String ajax() throws SystemExceptions, IOException{
    	response.setContentType("text/html;charset=utf-8");
    	if(this.getStuUser()!=null && this.getStuUser().getId()>0L  && this.getStuUser().getVisitsId()>0L){
    		visits=visitsService.findById(this.getStuUser().getVisitsId());
    		SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    		Date endDate;
    		Date startDate;
			try {
				endDate=df.parse(DateHelper.getFullDate());
				if(visits.getStartDay()!=null && visits.getStartDay().length()>0L){
					
				 startDate = df.parse(visits.getStartDay());
				 long   seconds   =   (endDate.getTime()-   startDate.getTime())/1000; 

				 long   date   =   seconds/(24*60*60);     //相差的天数 
				 long   hour   =   (seconds-date*24*60*60)/(60*60);//相差的小时数 
				 
				 long   minut   =   (seconds-date*24*60*60-hour*60*60)/(60);//相差的分钟数 
				 
				 long   second   =   (seconds-date*24*60*60-hour*60*60-minut*60);//相差的秒数 
				 
				 if(!(minut>0L) && second>30L){
					 minut=1;
				 }
				 
				  if(minut>0L){
					  DecimalFormat   ss   =   new   DecimalFormat( "#0.00 ");   
					  float max=new Float(date)*new Float(24)+new Float(hour)+(new Float(minut)/new Float(60));
					  visits.setRecordSize(String.valueOf(ss.format(max))); 
				  }else{
					  visits.setRecordSize(String.valueOf(date*24+hour));   
				  }
				 visitsService.save(visits);
				  
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    	response.getWriter().print("1");
    	response.getWriter().close();
    	return null;
    }
    
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != visits) {
            if (visits.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_VISITS);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_VISITS);
            }
            visitsService.save(visits);
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
        visitsService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_VISITS);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(visits == null){
            visits = new Visits(request, null);
        }
        visitss = visitsService.paginated(visits);
        this.setParameters(FORWARD_MANAGER_VISITS);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(visits == null){
            visits = new Visits(request, null);
        }
        visitss = visitsService.paginated(visits);
        this.setParameters(FORWARD_LIST_VISITS);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_VISITS);
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
        visits = visitsService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_VISITS);
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
        visits = visitsService.findById(getId());
        this.setParameters(FORWARD_DETAIL_VISITS);
        return SUCCESS;
    }

    public List<Visits> getVisitss() {
        return visitss;
    }

    public void setVisitss(List<Visits> visitss) {
        this.visitss = visitss;
    }

    public Visits getVisits() {
    	if(visits == null){
    		visits = new Visits();
    	}
        return visits;
    }

    public void setVisits(Visits visits) {
        this.visits = visits;
    }



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Visitsmag getVisitsmag() {
		return visitsmag;
	}



	public void setVisitsmag(Visitsmag visitsmag) {
		this.visitsmag = visitsmag;
	}



	public List<Users> getUserss() {
		return userss;
	}



	public void setUserss(List<Users> userss) {
		this.userss = userss;
	}



	public Users getUsers() {
		return users;
	}



	public void setUsers(Users users) {
		this.users = users;
	}
    
    
}