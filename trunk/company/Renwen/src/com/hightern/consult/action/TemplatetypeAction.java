/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.MultipleFileUploadAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.consult.model.Templatetype;
import com.hightern.consult.service.TemplatetypeService;
//控制前台页面的模板页面
@Scope("prototype")
@Controller("templatetypeAction")
public class TemplatetypeAction extends MultipleFileUploadAction<Templatetype> {

	/**
	 * 此TemplatetypeAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */

	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_TEMPLATETYPE = "templatetype_manager.htm";
	private static final String ACTION_SHOWADD_TEMPLATETYPE = "templatetype_showAdd.htm";
	private static final String FORWARD_MANAGER_TEMPLATETYPE = "/consult/manager_templatetype.jsp";
	private static final String FORWARD_LIST_TEMPLATETYPE = "/consult/list_templatetype.jsp";
	private static final String FORWARD_SHOWADD_TEMPLATETYPE = "/consult/add_templatetype.jsp";
	private static final String FORWARD_SHOWEDIT_TEMPLATETYPE = "/consult/edit_templatetype.jsp";
	private static final String FORWARD_DETAIL_TEMPLATETYPE = "/consult/detail_templatetype.jsp";
	@Resource(name = "templatetypeService")
	private TemplatetypeService templatetypeService;
	private List<Templatetype> templatetypes;
	private Templatetype templatetype;

	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != templatetype) {
			File aa = new File(ServletActionContext.getServletContext()
					.getRealPath("/userfiles/mbimages"));
			if (!aa.exists()) {
				aa.mkdir();
			}
			if (templatetype.getId() > 0) {
				if (request.getParameter("pdxc") != null
						&& request.getParameter("pdxc").toString().length() > 0) {
					if (Integer.parseInt(request.getParameter("pdxc")
							.toString()) == 2) {
						File sss = new File(ServletActionContext
								.getServletContext().getRealPath(
										templatetype.getFilePath()));
						if (sss.exists()) {
							sss.delete();
						}
						templatetype.setFilePath("");
					}
				}

				if (getUpload() != null) {
					// 先删除文件，后保存
					File sss = new File(ServletActionContext
							.getServletContext().getRealPath(
									templatetype.getFilePath()));
					if (sss.exists()) {
						sss.delete();
					}
					this.setUploadFileName(new Date().getTime()
							+ getExtention(this.getUploadFileName()));
					File filePath = new File(ServletActionContext
							.getServletContext().getRealPath(
									"/userfiles/mbimages")
							+ "/" + this.getUploadFileName());
					copy(getUpload(), filePath);
					templatetype.setFilePath("/userfiles/mbimages/"
							+ this.getUploadFileName());
				}
				this.setParameters("数据编辑成功！", ACTION_MANAGER_TEMPLATETYPE);
			} else {
				// 上传文件
				if (getUpload() != null) {
					this.setUploadFileName(new Date().getTime()
							+ getExtention(this.getUploadFileName()));
					File filePath = new File(ServletActionContext
							.getServletContext().getRealPath(
									"/userfiles/mbimages")
							+ "/" + this.getUploadFileName());
					copy(getUpload(), filePath);
					templatetype.setFilePath("/userfiles/mbimages/"
							+ this.getUploadFileName());

				}

				this.setParameters("数据保存成功！", ACTION_SHOWADD_TEMPLATETYPE);
			}
			templatetypeService.save(templatetype);
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
		templatetypeService.remove(idsStringToList(getSelectedIds()));
		setParameters("删除成功！", ACTION_MANAGER_TEMPLATETYPE);
		return SUCCESS;
	}

	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (templatetype == null) {
			templatetype = new Templatetype(request, null);
		}
		templatetypes = templatetypeService.paginated(templatetype);
		this.setParameters(FORWARD_MANAGER_TEMPLATETYPE);
		return SUCCESS;
	}

	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (templatetype == null) {
			templatetype = new Templatetype(request, null);
		}
		templatetypes = templatetypeService.paginated(templatetype);
		this.setParameters(FORWARD_LIST_TEMPLATETYPE);
		return SUCCESS;
	}

	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		this.setParameters(FORWARD_SHOWADD_TEMPLATETYPE);
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
		templatetype = templatetypeService.findById(idsStringToList(
				getSelectedIds()).get(0));
		this.setParameters(FORWARD_SHOWEDIT_TEMPLATETYPE);
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
		templatetype = templatetypeService.findById(getId());
		this.setParameters(FORWARD_DETAIL_TEMPLATETYPE);
		return SUCCESS;
	}

	/***
	 * 判断模板文件是否存在
	 * 
	 * @return
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String judgePath() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		String sb = "";
		if (request.getParameter("mbPath") != null
				&& request.getParameter("mbPath").toString().length() > 0) {
			File fm = new File(ServletActionContext.getServletContext()
					.getRealPath(request.getParameter("mbPath").toString()));
			if (fm.exists()) {
				sb = "1"; // 文件存在
			} else {
				sb = "2";// 不存在
			}

		} else {
			sb = "2";
		}
		response.getWriter().print(sb);
		response.getWriter().close();
		return null;
	}

	/***
	 * 获取当前模板的效果图片
	 * @return
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String showEffect() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		StringBuffer sb = new StringBuffer();
        if(request.getParameter("category")!=null && request.getParameter("category").toString().length()>0){
        	templatetype=templatetypeService.findById(Long.parseLong(request.getParameter("category").toString()));
        	  if(templatetype.getFilePath()!=null && templatetype.getFilePath().length()>0){
        		  sb.append("<a href=\""+request.getSession().getServletContext().getContextPath()+templatetype.getFilePath()+"\" target=\"_blank\" ><img src=\""+request.getSession().getServletContext().getContextPath()+templatetype.getFilePath()+"\" width=\"200px\" height=\"200px\" ></a>");
        	  }else {
				 sb.append("无该模板的效果图");
			  }
        }else{
        	sb.append("模板效果图出错");
        }
		
		response.getWriter().print(sb);
		response.getWriter().close();
		return null;
	}

	public List<Templatetype> getTemplatetypes() {
		return templatetypes;
	}

	public void setTemplatetypes(List<Templatetype> templatetypes) {
		this.templatetypes = templatetypes;
	}

	public Templatetype getTemplatetype() {
		if (templatetype == null) {
			templatetype = new Templatetype();
		}
		return templatetype;
	}

	public void setTemplatetype(Templatetype templatetype) {
		this.templatetype = templatetype;
	}
}