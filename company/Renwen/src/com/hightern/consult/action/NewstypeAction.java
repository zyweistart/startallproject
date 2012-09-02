/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.MultipleFileUploadAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.JsonUtil;
import com.hightern.consult.model.Newstype;
import com.hightern.consult.model.Templatetype;
import com.hightern.consult.service.NewstypeService;
import com.hightern.consult.service.TemplatetypeService;
import com.hightern.consult.util.ObjectToXml;

@Scope("prototype")
@Controller("newstypeAction")
public class NewstypeAction extends MultipleFileUploadAction<Newstype> {

	/**
	 * 此NewstypeAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */

	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_NEWSTYPE = "newstype_manager.htm";
	private static final String ACTION_SHOWADD_NEWSTYPE = "newstype_showAdd.htm";
	private static final String FORWARD_MANAGER_NEWSTYPE = "/consult/manager_newstype.jsp";
	private static final String FORWARD_LIST_NEWSTYPE = "/consult/list_newstype.jsp";
	private static final String FORWARD_SHOWADD_NEWSTYPE = "/consult/add_newstype.jsp";
	private static final String FORWARD_INDIVIDUALADD_NEWSTYPE = "/consult/individualAdd_newstype.jsp";
	private static final String FORWARD_SHOWEDIT_NEWSTYPE = "/consult/edit_newstype.jsp";
	private static final String FORWARD_DETAIL_NEWSTYPE = "/consult/detail_newstype.jsp";
	private static final String FORWARD_TREE_NEWS = "/consult/tree_newstype.jsp";
	private static final String FORWARD_XMTREE_NEWS = "/consult/xmtree_newstype.jsp";
	private static final String FORWARD_VIEWFRAME_NEWS = "/consult/newstype_frame.jsp";
	@Resource(name = "newstypeService")
	private NewstypeService newstypeService;
	private List<Newstype> newstypes;
	private Newstype newstype;
    @Resource(name="templatetypeService")
	private TemplatetypeService templatetypeService;
	private List<Templatetype> templatetypes;
	private Templatetype templatetype;

	private Long pdId;
	private String pdName;
	
	/**
	 * 根据菜单编号取得菜单名称
	 * 
	 * @return null
	 * @throws SystemExceptions
	 */
	public String ajax() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		if (isNullOrEmptyString(this.getId())) {
			throw new SystemExceptions("菜单编号不可为空！");
		}
		newstype = newstypeService.findById(this.getId());
		response.getWriter().print(JsonUtil.getJsonStringJavaPOJO(newstype));
		response.getWriter().close();
		return null;
	}

	
	
	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String save() throws SystemExceptions {
		if (null != newstype) {
			if (newstype.getId() > 0) {
				if(request.getParameter("pdxc")!=null && !isNullOrEmptyString(request.getParameter("pdxc"))){
					File aa=new File(ServletActionContext.getServletContext().getRealPath(newstype.getXcImg()));
					if(aa.exists()){
						aa.delete();
					}
					newstype.setXcImg("");
				}
				if (getUpload() != null) {
					//删除存在的文件
					File aa=new File(ServletActionContext.getServletContext().getRealPath(newstype.getXcImg()));
					if(aa.exists()){
						aa.delete();
					}
					
					this.setUploadFileName(new Date().getTime()
							+ getExtention(this.getUploadFileName()));
					File ss=new File(ServletActionContext.getServletContext().getRealPath("/userfiles/xcImg"));
					if(!ss.exists()){
						ss.mkdirs();
					}
					File filePath = new File(ServletActionContext
							.getServletContext().getRealPath("/userfiles/xcImg")
							+ "/" + this.getUploadFileName());
					copy(getUpload(), filePath);
					newstype.setXcImg("/userfiles/xcImg/"+this.getUploadFileName());
				}
				if (newstype.getId() == newstype.getPid()) {
					// this.setMessage("请不要选择自己做上级类型！");
					this.setParameters(
							"<span style='color: red'>请不要选择自己做上级类型！</span>",
							ACTION_MANAGER_NEWSTYPE);
				} else {
					this.setParameters("数据编辑成功！", ACTION_MANAGER_NEWSTYPE);
					Newstype pn = newstypeService.findById(newstype.getPid());
					if (pn != null && pn.getLength() != null
							&& pn.getLength() > 0) {
						newstype.setLength(pn.getLength() + 1);
					} else {
						newstype.setLength(1);
					}
					newstypeService.updateName(newstype.getId(),newstype.getPid(),newstype.getCategory(), newstype.getName());
					templatetype=templatetypeService.findById(newstype.getCategory());
					newstype.setTemplateTitle(templatetype.getTitle());
					newstype.setUpStatus(templatetype.getUpStatus());
					newstypeService.save(newstype);
				}
			} else {
				if(this.getPdId()!=null && this.getPdId()>0L){
					newstype.setPid(this.getPdId());
					this.setParameters("/common/individualSuccess.jsp","目录创建成功！", "integration_individualMag.htm?id="+this.getPdId());
				}else {
					this.setParameters("数据保存成功！", ACTION_SHOWADD_NEWSTYPE);
				}
				Newstype pn = newstypeService.findById(newstype.getPid());
				if (pn != null && pn.getLength() != null && pn.getLength() > 0) {
					newstype.setLength(pn.getLength() + 1);
				} else {
					newstype.setLength(1);
				}
				if (getUpload() != null) {
					this.setUploadFileName(new Date().getTime()
							+ getExtention(this.getUploadFileName()));
					File ss=new File(ServletActionContext.getServletContext().getRealPath("/userfiles/xcImg"));
					if(!ss.exists()){
						ss.mkdirs();
					}
					File filePath = new File(ServletActionContext
							.getServletContext().getRealPath("/userfiles/xcImg")
							+ "/" + this.getUploadFileName());
					copy(getUpload(), filePath);
					newstype.setXcImg("/userfiles/xcImg/"+this.getUploadFileName());
				}
				templatetype=templatetypeService.findById(newstype.getCategory());
				newstype.setTemplateTitle(templatetype.getTitle());
				newstype.setUpStatus(templatetype.getUpStatus());//作用:控制添加信息时候的上传按钮
				newstypeService.save(newstype);
			}
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
		List<Long> ids = idsStringToList(getSelectedIds());
		for (Long id : ids) {
			newstypes = newstypeService.getchilren(id);
			if (newstypes != null && newstypes.size() > 0) {
				List<Long> idss = new ArrayList<Long>();
				for (Newstype nt : newstypes) {
					idss.add(nt.getId());
				}
				newstypeService.remove(idss);
			}
			newstypeService.remove(id);
		}
		setParameters("删除成功！", ACTION_MANAGER_NEWSTYPE);

		return SUCCESS;
	}

	/**
	 * 用户单独  删除操作
	 * 
	 * @return SUCESS
	 * @throws SystemExceptions
	 */
	public String individualDelete() throws SystemExceptions {
		if (isNullOrEmptyString(this.getPdId())) {
			throw new SystemExceptions("编号不能为空！");
		}
			newstypes = newstypeService.getchilren(this.getPdId());
			if (newstypes != null && newstypes.size() > 0) {
				List<Long> idss = new ArrayList<Long>();
				for (Newstype nt : newstypes) {
					idss.add(nt.getId());
				}
				newstypeService.remove(idss);
			}
			newstypeService.remove(this.getPdId());
		setParameters("/common/individualSuccess.jsp","目录删除成功,包括子级目录也删除！", request.getSession().getServletContext().getContextPath()+"/blank.jsp");
		return SUCCESS;
	}

	
	
	
	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (newstype == null) {
			newstype = new Newstype(request, null);
		}
		String name = request.getParameter("name");
		if (name != null) {
			newstype.setName(name);
		}
		String pname = request.getParameter("pname");
		if (pname != null) {
			newstype.setPname(pname);
		}
		String length = request.getParameter("length");
		if (length != null && !"".equals(length)) {
			newstype.setLength(Integer.parseInt(length));
		}
		newstypes = newstypeService.paginated(newstype);
		this.setParameters(FORWARD_MANAGER_NEWSTYPE);
		return SUCCESS;
	}

	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list() throws SystemExceptions {
		if (newstype == null) {
			newstype = new Newstype(request, null);
		}
		newstypes = newstypeService.paginated(newstype);
		this.setParameters(FORWARD_LIST_NEWSTYPE);
		return SUCCESS;
	}

	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		
		if(this.getPdId()!=null && this.getPdId()>0L){
			if(newstype==null){newstype=new Newstype(request,null);}
			 newstype.setRemoveStatus(2);//用户自行创建，可以删除
			 Newstype ss=new Newstype(request,null);
			 ss=newstypeService.findById(this.getPdId());
			 this.setPdName(ss.getName());
			 templatetypes=templatetypeService.obtainMag(1);//用户可选择的模板
			 this.setParameters(FORWARD_INDIVIDUALADD_NEWSTYPE);
		}else{
        if(newstype==null){newstype=new Newstype(request,null);}
        newstype.setRemoveStatus(1);//用户  不可以删除
		templatetypes=templatetypeService.findAll();
		this.setParameters(FORWARD_SHOWADD_NEWSTYPE);
		}
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
		templatetypes=templatetypeService.findAll();
		newstype = newstypeService.findById(idsStringToList(getSelectedIds())
				.get(0));
		this.setParameters(FORWARD_SHOWEDIT_NEWSTYPE);
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
		newstype = newstypeService.findById(getId());
		this.setParameters(FORWARD_DETAIL_NEWSTYPE);
		return SUCCESS;
	}

	
	
	/***
	 * 显示导航菜单(ajax)
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 * @throws IOException
	 */
	public String ajax_menu() throws SystemExceptions, IOException {
		response.setContentType("text/html;charset=utf-8");
		StringBuilder strBuilder = new StringBuilder();
	     Long bh=new Long(0);
		  if(request.getParameter("id")!=null){
			bh=Long.parseLong(request.getParameter("id"));
		  }
		newstypes=newstypeService.getMapName(bh);
		strBuilder.append("<ul>");
		if(bh==2){
		   for(int i=0;i<newstypes.size();i++){
		     if(i==2){
		    	 strBuilder.append("<li><a href=\"examination_Self.htm\">自我测试</a></li>");
		    	 strBuilder.append("<li><a href=\"postmsg_list_home.htm\">互动交流</a></li>");
		     }
		     strBuilder.append("<li><a href=\"integration_list_home.htm?id="+newstypes.get(i).getPid()+"&typeId="+newstypes.get(i).getId()+"&typeA="+newstypes.get(i).getCategory()+"&typeB=1\">"+newstypes.get(i).getName()+"</a></li>");
	     	}
		}else if(bh==1){
		   for(int i=0;i<newstypes.size();i++){
				  
			strBuilder.append("<li><a href=\"integration_list_sb.htm?id="+newstypes.get(i).getId()+"&typeId="+newstypes.get(i).getPid()+"&typeC=1\">"+newstypes.get(i).getName()+"</a></li>");		
		    }
		}
		strBuilder.append("</ul>");
		
		response.getWriter().print(strBuilder.toString());
		response.getWriter().close();
	    return SUCCESS;
	}
	
	
	
	/**
	 * 树形新闻类别
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String tree() throws SystemExceptions {
		newstypes = newstypeService.findAll();
		setParameters(FORWARD_TREE_NEWS);
		return SUCCESS;
	}

	/***
	 * 菜单展示页面
	 * @return 
	 */
	public String viewFrame()throws SystemExceptions{
		setParameters(FORWARD_VIEWFRAME_NEWS);
		return SUCCESS;
	}
	
	
	
	/***
	 * 树形菜单
	 * @return
	 * @throws SystemExceptions
	 */
	public String xmtree()throws SystemExceptions{
		//将值设置为 无模板的编号
		newstypes=newstypeService.obtainNewstype(new Long(1));
		setParameters(FORWARD_XMTREE_NEWS);
		return  SUCCESS;
	}
	
	
	/***
	 * 根据项目编号打开下级项目
	 * @return SUCCESS
	 * @throws SystemExceptions
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public String getChild()throws SystemExceptions, IOException{
		this.response.setContentType("text/html;charset=utf-8");
		if(!isNullOrEmptyString(this.getId())){
			this.newstype=new Newstype();
			newstype.setPid(this.getId());
			Map map=this.newstypeService.buildReflectJpql("select o from newstype o where o.category!=1", this.newstype);
			newstypes=newstypeService.queryForObject(this.getJpql(map), this.getParams(map));
			this.response.getWriter().print(ObjectToXml.NewstypeXML(this.newstypes));
			this.response.getWriter().close();
		}
		
		return null;
	}
	
	
	public List<Newstype> getNewstypes() {
		return newstypes;
	}

	public void setNewstypes(List<Newstype> newstypes) {
		this.newstypes = newstypes;
	}

	public Newstype getNewstype() {
		if (newstype == null) {
			newstype = new Newstype();
		}
		return newstype;
	}

	public void setNewstype(Newstype newstype) {
		this.newstype = newstype;
	}

	public List<Templatetype> getTemplatetypes() {
		return templatetypes;
	}

	public void setTemplatetypes(List<Templatetype> templatetypes) {
		this.templatetypes = templatetypes;
	}

	public Templatetype getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(Templatetype templatetype) {
		this.templatetype = templatetype;
	}


	public Long getPdId() {
		return pdId;
	}

	public void setPdId(Long pdId) {
		this.pdId = pdId;
	}

	public String getPdName() {
		return pdName;
	}


	public void setPdName(String pdName) {
		this.pdName = pdName;
	}


}