/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.system.action;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.system.model.Logs;
import com.hightern.system.service.LogsService;
import com.opensymphony.xwork2.Action;

@Scope("prototype")
@Controller("logsAction")
public class LogsAction extends BaseAction<Logs> {
	
	/**
	 * 日志管理
	 */
	
	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_LOGS = "logs_manager.htm";
	private static final String ACTION_SHOWADD_LOGS = "logs_showAdd.htm";
	private static final String FORWARD_MANAGER_LOGS = "/system/manager_logs.jsp";
	private static final String FORWARD_LIST_LOGS = "/system/list_logs.jsp";
	private static final String FORWARD_SHOWADD_LOGS = "/system/add_logs.jsp";
	private static final String FORWARD_SHOWEDIT_LOGS = "/system/edit_logs.jsp";
	private static final String FORWARD_DETAIL_LOGS = "/system/detail_logs.jsp";
	@Resource(name = "logsService")
	private LogsService logsService;
	private List<Logs> logss;
	private Logs logs;
	
	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != logs) {
			if (logs.getId() > 0) {
				this.setParameters("数据编辑成功！", LogsAction.ACTION_MANAGER_LOGS);
			} else {
				this.setParameters("数据保存成功！", LogsAction.ACTION_SHOWADD_LOGS);
			}
			logsService.save(logs);
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除操作
	 * 
	 * @return SUCESS
	 * @throws SystemExceptions
	 */
	public String delete() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) { throw new SystemExceptions("编号不能为空！"); }
		logsService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", LogsAction.ACTION_MANAGER_LOGS);
		return Action.SUCCESS;
	}
	
	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 * @throws ParseException
	 */
	public String manager() throws SystemExceptions, ParseException {
		if (logs == null) {
			logs = new Logs(request, null);
		}
		logs.setLoginName(request.getParameter("loginName"));
		logs.setLoginIp(request.getParameter("loginIp"));
		logs.setLoginDate(request.getParameter("loginDate"));
		logss = logsService.paginated(logs);
		this.setParameters(LogsAction.FORWARD_MANAGER_LOGS);
		return Action.SUCCESS;
	}
	
	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (logs == null) {
			logs = new Logs(request, null);
		}
		logss = logsService.paginated(logs);
		this.setParameters(LogsAction.FORWARD_LIST_LOGS);
		return Action.SUCCESS;
	}
	
	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		this.setParameters(LogsAction.FORWARD_SHOWADD_LOGS);
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showEdit() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) { throw new SystemExceptions("编号不能为空！"); }
		logs = logsService.findById(idsStringToList(getSelectedIds()).get(0));
		this.setParameters(LogsAction.FORWARD_SHOWEDIT_LOGS);
		return Action.SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String detail() throws SystemExceptions {
		if (isNullOrEmptyString(getId())) { throw new SystemExceptions("编号不能为空！"); }
		logs = logsService.findById(getId());
		this.setParameters(LogsAction.FORWARD_DETAIL_LOGS);
		return Action.SUCCESS;
	}
	
	public List<Logs> getLogss() {
		return logss;
	}
	
	public void setLogss(List<Logs> logss) {
		this.logss = logss;
	}
	
	public Logs getLogs() {
		if (logs == null) {
			logs = new Logs();
		}
		return logs;
	}
	
	public void setLogs(Logs logs) {
		this.logs = logs;
	}
}