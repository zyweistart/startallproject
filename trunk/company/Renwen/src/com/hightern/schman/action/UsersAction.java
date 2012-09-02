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
import com.hightern.kernel.util.MD5;
import com.hightern.schman.model.Users;
import com.hightern.schman.model.Visits;
import com.hightern.schman.service.UsersService;
import com.hightern.schman.service.VisitsService;

@Scope("prototype")
@Controller("usersAction")
public class UsersAction extends BaseAction<Users> {

	/**
	 * 此UsersAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */

	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_USERS = "users_manager.htm";
	private static final String ACTION_SHOWADD_USERS = "users_showAdd.htm";
	private static final String FORWARD_MANAGER_USERS = "/schman/manager_users.jsp";
	private static final String FORWARD_LIST_USERS = "/schman/list_users.jsp";
	private static final String FORWARD_SHOWADD_USERS = "/schman/add_users.jsp";
	private static final String FORWARD_SHOWEDIT_USERS = "/schman/edit_users.jsp";
	private static final String FORWARD_DETAIL_USERS = "/schman/detail_users.jsp";
	@Resource(name = "usersService")
	private UsersService usersService;
	@Resource(name = "visitsService")
	private VisitsService visitsService;
	private List<Users> userss;
	private List<Visits> visitss;
	private Users users;
	private Visits visits;

	private String username;
	private String password;

	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != users) {
			if (users.getId() > 0) {
				this.setParameters("数据编辑成功！", ACTION_MANAGER_USERS);
			} else {
				this.setParameters("数据保存成功！", ACTION_SHOWADD_USERS);
			}
			usersService.save(users);
		}
		return SUCCESS;
	}

	/**
	 * 删除操作
	 * 
	 * @return SUCESS
	 * @throws SystemExceptions
	 */
	public String delete() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) {
			throw new SystemExceptions("编号不能为空！");
		}
		usersService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", ACTION_MANAGER_USERS);
		return SUCCESS;
	}

	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (users == null) {
			users = new Users(request, null);
		}
		userss = usersService.paginated(users);
		this.setParameters(FORWARD_MANAGER_USERS);
		return SUCCESS;
	}

	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (users == null) {
			users = new Users(request, null);
		}
		userss = usersService.paginated(users);
		this.setParameters(FORWARD_LIST_USERS);
		return SUCCESS;
	}

	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		this.setParameters(FORWARD_SHOWADD_USERS);
		return SUCCESS;
	}

	/**
	 * 编辑导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showEdit() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) {
			throw new SystemExceptions("编号不能为空！");
		}
		users = usersService.findById(idsStringToList(getSelectedIds()).get(0));
		this.setParameters(FORWARD_SHOWEDIT_USERS);
		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String detail() throws SystemExceptions {
		if (isNullOrEmptyString(getId())) {
			throw new SystemExceptions("编号不能为空！");
		}
		users = usersService.findById(getId());
		this.setParameters(FORWARD_DETAIL_USERS);
		return SUCCESS;
	}

	/***
	 * 判断用户是否存在
	 * 
	 * @return
	 */
	public String checkname() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		String cname = request.getParameter("username").toString();
		StringBuffer sb = new StringBuffer();
		if (usersService.judgeName(cname)) {
			sb.append("1");
		} else {
			sb.append("2");
		}

		response.getWriter().print(sb.toString());
		response.getWriter().close();
		return null;
	}

	/***
	 * 注册
	 * 
	 * @return
	 * @throws IOException
	 */
	public String zcUsers() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		String zwname="";
		String userCls="";
		String userTeacther="";
		String userYear="";
		Integer userXq=0;
		String identity="";
		if(request.getParameter("zwname")!=null && request.getParameter("zwname").length()>0L){
			zwname=request.getParameter("zwname").toString();
		}
		
		if(request.getParameter("userCls")!=null && request.getParameter("userCls").length()>0L){
			userCls=request.getParameter("userCls").toString();
		}
		if(request.getParameter("userTeacther")!=null && request.getParameter("userTeacther").length()>0L){
			userTeacther=request.getParameter("userTeacther").toString();
		}
		if(request.getParameter("userYear")!=null && request.getParameter("userYear").length()>0L){
			userYear=request.getParameter("userYear").toString();
		}
		if(request.getParameter("userXq")!=null && request.getParameter("userXq").length()>0L){
			userXq=Integer.parseInt(request.getParameter("userXq").toString());
		}
		
		if(request.getParameter("identity")!=null && request.getParameter("identity").length()>0L){
			identity=request.getParameter("identity").toString();
		}
		String uname = request.getParameter("username");
		String pwd = request.getParameter("password");
		StringBuffer sb = new StringBuffer();
		if (users == null) {
			users = new Users(request, null);
		}

		if (usersService.judgeName(uname)) {
			sb.append("1");
		} else {
			users.setUname(zwname);
			users.setUserCls(userCls);
			users.setUserTeacther(userTeacther);
			users.setUserYear(userYear);
			users.setUserXq(userXq);
			users.setIdentity(identity);
			users.setStatus(1);// 未审核
			users.setStartDay(DateHelper.getFullDate());
			if (uname != null && uname.length() > 0) {
				users.setUserName(uname);
			}
			if (pwd != null && pwd.length() > 0) {
				users.setPassword(MD5.calcMD5(pwd));
				usersService.save(users);
			}

			sb.append(request.getSession().getServletContext()
					.getContextPath() +"/users_login.htm?username=" + uname + "&password=" + pwd
					+ "");
		}
		response.getWriter().print(sb.toString());
		response.getWriter().close();
		return null;
	}

	/**
	 * 用户退出
	 * 
	 * @return
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String exit() throws SystemExceptions, IOException {
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
		
		
		if (session.get("stuUser") != null) {
			session.clear();
			session = null;
		}
		// response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/home.jsp");
		response.getWriter().print(
				"<script language=\"javascript\">window.location.href='"
						+ request.getSession().getServletContext()
								.getContextPath() + "/home.jsp'</script>");
		response.getWriter().close();
		return null;
	}

	/**
	 * 登录
	 * 
	 * @return
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	@SuppressWarnings({  "unchecked" })
	public String login() throws SystemExceptions, IOException {
		if (request.getParameter("username") != null
				&& request.getParameter("username").toString().length() > 0) {
			this.setUsername(request.getParameter("username").toString());
		} else {
			this.setUsername("");
			setMessage("用户名不能为空！");
		}
		if (request.getParameter("password") != null
				&& request.getParameter("password").toString().length() > 0) {
			this.setPassword(request.getParameter("password").toString());
		} else {
			this.setPassword("");
			setMessage("密码不能为空！");
			
		}
		if (isNullOrEmptyString(this.getUsername())
				|| isNullOrEmptyString(this.getPassword())) {

			response.getWriter().print("");
			response.getWriter().close();
			// this.setParameters("/common/qisuccess.jsp","用户或密码输入有误,请重试!","users_kc.htm");
			setMessage("用户或密码输入有误,请重试!");
		} else {
			final List<Users> us = usersService.hquserMag(this.getUsername(), MD5.calcMD5(this.getPassword()));
			if (us != null && us.size() > 0) {
				for (final Users user : us) {
					session.put("stuUser", user);
					break;
				}
				// response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/fbpots_list_home.htm");
				
				visits=new Visits(request,null);
				visits.setType(1);
				visits.setStartDay(DateHelper.getFullDate());
				visits.setUserID(this.getStuUser().getId());
				visits.setUserName(this.getStuUser().getUserName());
				visitsService.save(visits);
				this.getStuUser().setVisitsId(visits.getId());
				this.getStuUser().setVisitsMax(visitsService.hqvisitsMax(1, this.getStuUser().getId()));
				this.getStuUser().setVisitsSize(visitsService.hqsizeMax(1, this.getStuUser().getId()));
				this.getStuUser().setVisitsExtmMax(visitsService.hqvisitsMax(2, this.getStuUser().getId()));
				this.getStuUser().setVisitsExtmSize(visitsService.hqsizeMax(2, this.getStuUser().getId()));
//				response.getWriter().print(
//						"<script language=\"javascript\">window.location.href='"
//								+ request.getSession().getServletContext()
//										.getContextPath()
//								+ "/home.jsp'</script>");
//				response.getWriter().close();
				this.setParameters("/home.jsp");
				return SUCCESS;
			} else {
				setMessage("用户名或密码有误！");
//				response.getWriter().print(
//						"<script language=\"javascript\">window.location.href='"
//								+ request.getSession().getServletContext()
//										.getContextPath()
//								+ "/home.jsp'</script>");
//				response.getWriter().close();
//				setMessage("用户或密码输入有误,请重试!");
				// this.setParameters("/common/qisuccess.jsp","用户或密码输入有误,请重试!","users_kc.htm");
//				return SUCCESS;
			}

		}
		this.setParameters("/home/login.jsp");
		return SUCCESS;
	}

	
	
	
	
	public List<Users> getUserss() {
		return userss;
	}

	public void setUserss(List<Users> userss) {
		this.userss = userss;
	}

	public Users getUsers() {
		if (users == null) {
			users = new Users();
		}
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Visits> getVisitss() {
		return visitss;
	}

	public void setVisitss(List<Visits> visitss) {
		this.visitss = visitss;
	}

	public Visits getVisits() {
		return visits;
	}

	public void setVisits(Visits visits) {
		this.visits = visits;
	}


	

}