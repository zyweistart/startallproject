/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.action;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.hightern.kernel.action.MultipleFileUploadAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.consult.model.Integration;
import com.hightern.consult.model.Newstype;
import com.hightern.consult.model.Templatetype;
import com.hightern.consult.service.IntegrationService;
import com.hightern.consult.service.NewstypeService;
import com.hightern.consult.service.TemplatetypeService;
import com.hightern.consult.util.videoUtil;

@Scope("prototype")
@Controller("integrationAction")
public class IntegrationAction extends MultipleFileUploadAction<Integration> {

	/**
	 * 此IntegrationAction由系统自动生成，但仅实现了基本的CRUD 功能 请根据业务的需求补全其它功能及编码
	 */

	private static final long serialVersionUID = 1L;
	private static final String ACTION_MANAGER_INTEGRATION = "integration_manager.htm";
	private static final String ACTION_SHOWADD_INTEGRATION = "integration_showAdd.htm";
	private static final String FORWARD_MANAGER_INTEGRATION = "/consult/manager_integration.jsp";
	private static final String FORWARD_INDIVIDUALMAG_INTEGRATION = "/consult/individualMag_integration.jsp";
	
	private static final String FORWARD_LIST_INTEGRATION = "/consult/list_integration.jsp";
	private static final String FORWARD_SHOWADD_INTEGRATION = "/consult/add_integration.jsp";
	private static final String FORWARD_INDIVIDUALADD_INTEGRATION = "/consult/individualAdd_integration.jsp";
	private static final String FORWARD_SHOWEDIT_INTEGRATION = "/consult/edit_integration.jsp";
	private static final String FORWARD_INDIVIDUALEDIT_INTEGRATION = "/consult/individualEdit_integration.jsp";
	private static final String FORWARD_DETAIL_INTEGRATION = "/consult/detail_integration.jsp";
	
	
	
	private static final String FORWARD_ISSUED_INTEGRATION = "/consult/isSued_integration.jsp";
//	private static final String FORWARD_PRO_SHOWEDIT_INTEGRATION = "/consult/edit_integration_pro.jsp";
//	private static final String FORWARD_ABOUT_LIST_INTEGRATION = "/news_content.jsp"; // 内容形式
//	private static final String FORWARD_LIST1_INTEGRATION_HOME = "/news_list.jsp";//列表1形式
//	private static final String FORWARD_LIST2_INTEGRATION_HOME = "/news_list.jsp";//列表2形式  
//	private static final String FORWARD_SHIPIN_LIST = "/news_shipinlist.jsp";//视频形式
//	private static final String FORWARD_XIAZAI_LIST = "/news_xiazailist.jsp";//下载形式
//	private static final String FORWARD_TUPIAN_LIST = "/default/tupian_list.jsp";//图片形式
	private static final String FORWARD_SPCONTENT_INTEGRATION = "/news_shipincontent.jsp"; //视频内容展示
	private static final String FORWARD_CONTENT_INTEGRATION = "/newContent.jsp";   //图文内容展示 
	@Resource(name = "integrationService")
	private IntegrationService integrationService;
	@Resource(name = "newstypeService")
	private NewstypeService newstypeService;
    @Resource(name="templatetypeService")
    private TemplatetypeService templatetypeService;
	
	
	private List<Integration> integrations;
	private List<Newstype> newstypes;
    private List<Templatetype> templatetypes;
	
	private Integration integration;
	private Newstype newstype;
	private Templatetype templatetype;

	private Long typeA;   //展示类型
	private Long typeId;   

	private Long pdid;  //控制页面跳转
	
	private Integer currentPage;// 当前页
	private Integer pagerSize;// 总页数
	private Integer nextPager;// 上一页
	private Integer prePager;// 下一页
	private Integer count;// 总数

	/**
	 * 保存数据（添加或修改对象）
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (null != integration) {
			File ss = new File(ServletActionContext.getServletContext()
					.getRealPath("/userfiles/files"));
			if (!ss.exists()) {
				ss.mkdir();
			}
			if (integration.getId() > 0) {
				if(this.getPdid()!=null && this.getPdid()>0L){
				this.setParameters("数据编辑成功！", "integration_individualMag.htm?id="+this.getPdid());	
				}else{
				this.setParameters("数据编辑成功！", ACTION_MANAGER_INTEGRATION);
				}
				// 上传文件
				if (getUpload() != null) {
					// 删除存在的文件
					File aa = new File(ServletActionContext.getServletContext()
							.getRealPath(integration.getFilePath()));
					if (aa.exists()) {
						aa.delete();
					}
					File bb = new File(ServletActionContext.getServletContext()
							.getRealPath(integration.getVideoPath()));
					if (bb.exists()) {
						bb.delete();
					}
					String date=String.valueOf(new Date().getTime());
					this.setUploadFileName(date
							+ getExtention(this.getUploadFileName()));
					File filePath = new File(ServletActionContext
							.getServletContext()
							.getRealPath("/userfiles/files")
							+ "/" + this.getUploadFileName());
					copy(getUpload(), filePath);
					integration.setFilePath("/userfiles/files/"
							+ getUploadFileName());
					//视频生成缩略图
					if(videoUtil.convertToFLV(request.getSession().getServletContext().getRealPath("userfiles/zsfiles/zs.bat"),request.getSession().getServletContext().getRealPath("userfiles/ffmpeg.exe"), request.getSession().getServletContext().getRealPath(integration.getFilePath()) , request.getSession().getServletContext().getRealPath("userfiles/filesimage/"+date+".jpg"))){
						integration.setVideoPath("/userfiles/filesimage/"+date+".jpg");
					}
				}
			} else {
				   if(this.getPdid()!=null && this.getPdid()>0L){
					this.setParameters("数据保存成功！", "integration_individualMag.htm?id="+this.getPdid());	
					}else{
				    this.setParameters("数据保存成功！", ACTION_SHOWADD_INTEGRATION);
					}
				// 上传文件
				if (getUpload() != null) {
					String date=String.valueOf(new Date().getTime());
					this.setUploadFileName(date
							+ getExtention(this.getUploadFileName()));
					File filePath = new File(ServletActionContext
							.getServletContext()
							.getRealPath("/userfiles/files")
							+ "/" + this.getUploadFileName());
					copy(getUpload(), filePath);
					integration.setFilePath("/userfiles/files/"
							+ getUploadFileName());
					if(videoUtil.convertToFLV(request.getSession().getServletContext().getRealPath("userfiles/zsfiles/zs.bat"),request.getSession().getServletContext().getRealPath("userfiles/ffmpeg.exe"), request.getSession().getServletContext().getRealPath(integration.getFilePath()) , request.getSession().getServletContext().getRealPath("userfiles/filesimage/"+date+".jpg"))){
						integration.setVideoPath("/userfiles/filesimage/"+date+".jpg");
					}
				}
				integration.setHits(new Long(1));
			}

			// 替换首页宣传
			integrationService.updatexcrj(integration.getXcrj());

			// 信息的状态 1：代表为未发布
			integration.setIsSued(1);

			// 获取FCK里面第一张图片
			String img = integrationService
					.setNewsImg(integration.getContent());
			if (img == null || img == "") {
				img = integrationService.setNewsImg1(integration.getContent());
			}
			if (img != null && !"".equals(img)) {
				integration.setImg(img);
			}

			// 获取对应数据的展示形式
			newstype = newstypeService.findById(integration.getNewsTypeId());
			integration.setCategory(newstype.getCategory());

			// 内容来源 未填写 自动填写为原文
			if (integration.getNewsfrom() == null
					|| isNullOrEmptyString(integration.getNewsfrom())) {
				integration.setNewsfrom("原文");
			}

			integrationService.save(integration);
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
		
		for(Long n:idsStringToList(getSelectedIds())){
			integration=integrationService.findById(n);
			if(integration.getFilePath()!=null && integration.getFilePath().length()>0){
				  File a=new File(ServletActionContext.getServletContext()
							.getRealPath(integration.getFilePath()));
				  if (a.exists()) {
						a.delete();
					}
			}
			if(integration.getVideoPath()!=null && integration.getVideoPath().length()>0){
				  File a=new File(ServletActionContext.getServletContext()
							.getRealPath(integration.getVideoPath()));
				  if (a.exists()) {
						a.delete();
					}
			}
		}
		
		integrationService.remove(idsStringToList(getSelectedIds()));

		if(this.getPdid()!=null && this.getPdid()>0L){
		setParameters("删除成功！", "integration_individualMag.htm?id="+this.getPdid());
		}else{
		setParameters("删除成功！", ACTION_MANAGER_INTEGRATION);
		}
		return SUCCESS;
	}

	/**
	 * 管理所有
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String manager() throws SystemExceptions {
		if (integration == null) {
			integration = new Integration(request, null);
		}

		integration.setName(request.getParameter("name"));
		String isSued = request.getParameter("isSued");
		if (isSued != null && isSued.length() > 0) {
			integration.setIsSued(Integer.parseInt(isSued));
		}
		integration.setNewsTypeName(request.getParameter("newsTypeName"));

		integrations = integrationService.paginated(integration);
		this.setParameters(FORWARD_MANAGER_INTEGRATION);

		return SUCCESS;
	}

	
	/**
	 * 管理所有 按菜单分别管理
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String individualMag() throws SystemExceptions {
		if (integration == null) {
			integration = new Integration(request, null);
		}
		if(this.getId()!=null && this.getId()>0){
			integration.setNewsTypeId(this.getId());
	        newstype=newstypeService.findById(this.getId());
		}else{
			integration.setNewsTypeId(new Long(0));	
		}
	
		integrations = integrationService.paginated(integration);
		this.setParameters(FORWARD_INDIVIDUALMAG_INTEGRATION);

		return SUCCESS;
	}
	
	
	
	/**
	 * 查看所有信息
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */

	public String list() throws SystemExceptions {
		if (integration == null) {
			integration = new Integration(request, null);
		}
		integrations = integrationService.paginated(integration);
		this.setParameters(FORWARD_LIST_INTEGRATION);
		return SUCCESS;
	}

	/**
	 * 添加导航
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String showAdd() throws SystemExceptions {
		if(this.getPdid()!=null && this.getPdid()>0L){
			if(integration==null){integration=new Integration(request,null);}
			newstype=newstypeService.findById(this.getPdid());
			integration.setNewsTypeId(newstype.getId());
			integration.setNewsTypeName(newstype.getName());
			integration.setStartDay(DateHelper.getFullDate());
			integration.setParentNewsTypeId(newstype.getPid());
			this.setParameters(FORWARD_INDIVIDUALADD_INTEGRATION);
		}else{
			if(integration==null){integration=new Integration(request,null);}
			integration.setStartDay(DateHelper.getFullDate());
		this.setParameters(FORWARD_SHOWADD_INTEGRATION);
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
		integration = integrationService.findById(idsStringToList(
				getSelectedIds()).get(0));

		if(this.getPdid()!=null && this.getPdid()>0L){
			this.setParameters(FORWARD_INDIVIDUALEDIT_INTEGRATION);
			newstype=newstypeService.findById(this.getPdid());
		}else{
		
//		if (integration.getParentNewsTypeId() == 2) {
//			this.setParameters(FORWARD_PRO_SHOWEDIT_INTEGRATION);
//		} else {
			this.setParameters(FORWARD_SHOWEDIT_INTEGRATION);
//		}
		}
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
		integration = integrationService.findById(getId());
		this.setParameters(FORWARD_DETAIL_INTEGRATION);
		return SUCCESS;
	}
	
	
	/***
	 * 红帮文化节
	 * @return
	 * @throws SystemExceptions
	 */
	public String list_whj()throws SystemExceptions{
		if(isNullOrEmptyString(getTypeId()) && isNullOrEmptyString(getId())){
			throw new SystemExceptions("编号不能为空!");
		}
		int pageNoss = 0;
		if(!isNullOrEmptyString(this.getCurrentPage()) && !isNullOrEmptyString(this.getPagerSize())  && this.getPagerSize()>=this.getCurrentPage()){
			pageNoss=this.getCurrentPage();
		}else if(!isNullOrEmptyString(this.getCurrentPage()) && !isNullOrEmptyString(this.getPagerSize())  && this.getPagerSize()<this.getCurrentPage()){
			pageNoss=this.getPagerSize();
		}
		if(newstype==null){
		  newstype=new Newstype(request,null);
		}
		if(pageNoss>0){
		newstype.setPageNo(getCurrentPage());
		}
		newstype.setPageSize(4);
		newstypes=newstypeService.queryByPage("select o from newstype o where 1=1 and o.pid="+getId() , newstype);
		
		this.setCurrentPage(newstype.getPageNo());
		
		this.setPagerSize(newstype.getPageCount());
		setParameters("/cswhj.jsp");
		return SUCCESS;
	}
	
	
	
	

	/***
	 * 信息展示(点击菜单的信息)
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String list_home() throws SystemExceptions {
		if (isNullOrEmptyString(getId())) {
			throw new SystemExceptions("编号不能为空！");
		}
		if (integration == null) {
			integration = new Integration(request, null);
		}

		// 二级目录和三级目录
		if (getTypeId() != null && !isNullOrEmptyString(getTypeId())) {
			newstypes = newstypeService.findByPid(getTypeId());
        //	newstypes=newstypeService.getchilren(getTypeId());//获取子级的子级  保存在一个临时字段中
		}
		//本级目录菜单
        newstype=newstypeService.findById(getId());
	
        
    	if(newstype!=null){
			templatetype=templatetypeService.findById(newstype.getCategory());
		    if(templatetype!=null){
		    	integration.setPageSize(templatetype.getDataSize());
		    	//templatetype表中mbPath参数为空，确定newstype表中ljUrl不为空，两表中该两字段只能一个为空，否侧无跳转页面
		    	// newstype 表的ljUrl  可用于访问其他  action
		    	//templatetype 表的mbPath  可以访问其他模板 页面  本action 该方法
		    	this.setParameters(templatetype.getMbPath());  
//		    	if(templatetype.getMbPath()!=null && templatetype.getMbPath().indexOf("htm")>0){
//			    	this.setParameters("/common/iasuccess.jsp","",templatetype.getMbPath()+"?id="+getId()+"&typeId="+getTypeId());  
//			    	}else{
//			    	this.setParameters(templatetype.getMbPath());  	
//			    	}
		    }
		}
    
    	int pageno=1;   //用于控制分页  (即主菜单 显示子菜单的内容)
		int pageCount=0;
	
			if(integration.getPageNo()>0){
				pageno=integration.getPageNo();
			}
		
    	String jpql = "select o from integration o where o.isSued=99 and o.newsTypeId="
				+ getId() + " and o.category="+newstype.getCategory()+" order by o.startDay desc";
		integrations=integrationService.queryByPage(jpql, integration);
		if (integrations != null && integrations.size() > 0) {
			if(integration.getPageCount()>0){
				pageCount=integration.getPageCount();
				
				if(integration.getPageNo()>0){
					pageno=integration.getPageNo();
				}
			}
			integration = integrations.get(0);
			integration.setPageNo(pageno);
			integration.setPageCount(pageCount);
		}
		//
		//控制父级菜单无内容，展示子级菜单的信息， (内容为 同父级一样类型的内容)
		//父级菜单不想展示所有子级信息，可通过页面直接设置连接地址
		//
		if(!(integrations!=null && integrations.size()>0)){
		  if(getTypeId()!=null && getId()!=null && getTypeId().equals(getId())){
				    if(templatetype!=null){
				    	integration.setPageSize(templatetype.getDataSize());
				    }
			  
				    if(pageno>1){
				    	integration.setPageNo(pageno);
				    }
				    
			  jpql="select o from integration o where o.isSued=99 and o.parentNewsTypeId="
				+ getId() + " and o.category="+newstype.getCategory()+" order by o.startDay desc";
			  integrations=integrationService.queryByPage(jpql, integration);
			  
				if (integrations != null && integrations.size() > 0) {
					if(integration.getPageCount()>0){
						pageCount=integration.getPageCount();
						if(integration.getPageNo()>0){
							pageno=integration.getPageNo();
						}
					}
					integration = integrations.get(0);
					integration.setPageNo(pageno);
					integration.setPageCount(pageCount);
				}
		  }
		}
		
	
		return SUCCESS;
	}

	/**
	 * 显示详细信息 首页
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String content() throws SystemExceptions {
		if (isNullOrEmptyString(getId())) {
			throw new SystemExceptions("编号不能为空！");
		}

		newstype = newstypeService.findById(getTypeId());
		newstypes = newstypeService.findByPid(newstype.getId());

		integration = integrationService.findById(getId());
		integration.setHits(integration.getHits()+1);
		integrationService.save(integration);
		this.setParameters(FORWARD_CONTENT_INTEGRATION);
		return SUCCESS;

	}

	/**
	 * 显示详细信息 视频(flash)
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String spcontent() throws SystemExceptions {
		if (isNullOrEmptyString(getId())) {
			throw new SystemExceptions("编号不能为空！");
		}

		newstype = newstypeService.findById(getTypeId());
		newstypes = newstypeService.findByPid(newstype.getId());

		integration = integrationService.findById(getId());
		integration.setHits(integration.getHits()+1);
		integrationService.save(integration);
		this.setParameters(FORWARD_SPCONTENT_INTEGRATION);
		return SUCCESS;
	}

	/**
	 * 缩放处理
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public void zoom(File imageFile) throws Exception {
		try {

			// 缩略图存放路径
			File todir = new File(ServletActionContext.getServletContext()
					.getRealPath("/userfiles/integration") + "/small");
			if (!todir.exists()) {
				todir.mkdir();
			}

			if (!imageFile.isFile())
				throw new Exception(imageFile
						+ " is not image file error in CreateThumbnail!");

			File sImg = new File(todir, this.getUploadFileName());

			BufferedImage Bi = ImageIO.read(imageFile);
			// 假设图片宽 高 最大为300 360,使用默认缩略算法
			Image Itemp = Bi.getScaledInstance(300, 360, Bi.SCALE_DEFAULT);

			double Ratio = 0.0;
			if ((Bi.getHeight() > 300) || (Bi.getWidth() > 360)) {
				if (Bi.getHeight() > Bi.getWidth())
					Ratio = 360.0 / Bi.getHeight();
				else
					Ratio = 300.0 / Bi.getWidth();

				AffineTransformOp op = new AffineTransformOp(
						AffineTransform.getScaleInstance(Ratio, Ratio), null);
				Itemp = op.filter(Bi, null);
			}

			ImageIO.write((BufferedImage) Itemp, "jpg", sImg);

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	/**
	 * 新闻发布导航(管理发布信息)
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String isSued() throws SystemExceptions {
		// List<Long> ids = idsStringToList(getSelectedIds());
		if (integration == null) {
			integration = new Integration(request, null);
		}
		integration.setName(request.getParameter("name"));
		String isSued = request.getParameter("isSued");
		if (isSued != null && isSued.length() > 0) {
			integration.setIsSued(Integer.parseInt(isSued));
		}
		integration.setNewsTypeName(request.getParameter("newsTypeName"));
		integrations = integrationService.paginated(integration);
		this.setParameters(FORWARD_ISSUED_INTEGRATION);
		return SUCCESS;
	}

	/**
	 * 撤消(操作)
	 * 
	 * @return
	 * @throws SystemExceptions
	 */
	public String undo() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) {
			throw new SystemExceptions("编号不可为空！");
		}
		List<Long> ids = idsStringToList(this.getSelectedIds());
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				Integration integration = integrationService.findById(id);
				integration.setIsSued(new Integer("1"));// 收回
				integrationService.save(integration);
			}
		}
		if(this.getPdid()!=null && this.getPdid()>0L){
		this.setParameters("撤消成功！","integration_individualMag.htm?id="+this.getPdid());	
		}else{
		this.setParameters("撤消成功！", "integration_manager.htm");
		}
		return SUCCESS;
	}

	/**
	 * 发布(操作)
	 * 
	 * @return
	 * @throws SystemExceptions
	 */
	public String rerouted() throws SystemExceptions {
		if (isNullOrEmptyString(getSelectedIds())) {
			throw new SystemExceptions("编号不可为空！");
		}
		List<Long> ids = idsStringToList(this.getSelectedIds());
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				Integration integration = integrationService.findById(id);
				integration.setIsSued(new Integer("99"));// 发布
				integration.setIssuedDay(DateHelper.getFullDate());
				integrationService.save(integration);
			}
		}
		if(this.getPdid()!=null && this.getPdid()>0L){
			this.setParameters("发布成功！","integration_individualMag.htm?id="+this.getPdid());	
			}else{
		this.setParameters("发布成功！", "integration_manager.htm");
			}
		return SUCCESS;
	}

	// /***
	// * ajax目录
	// * @return SUCCESS
	// * @throws SystemExceptions
	// * @throws IOException
	// * @throws Exception
	// */
	// public String ajax_Information() throws SystemExceptions, IOException{
	// response.setContentType("text/html;charset=utf-8");
	// StringBuilder strBuilder = new StringBuilder();
	// Long Lid=new Long(0);
	// if(request.getParameter("id")!=null){
	// Lid=Long.parseLong(request.getParameter("id"));
	// }
	// strBuilder.append("<div id=\"lfmenu\">");
	// strBuilder.append("<table cellspacing=\"0\" cellpadding=\"0\" width=\"88%\" border=\"0\">");
	// newstypes=newstypeService.getMapName(Lid);
	// for(int i=0;i<newstypes.size();i++){
	// newstype=newstypes.get(i);
	// String ddz="javascript:void(null)";
	//
	// if(newstype.getCategory()==1){
	// ddz="integration_list_home.htm?id="+newstype.getPid()+"&typeId="+newstype.getId()+"&typeA="+newstype.getCategory();
	// }else if(newstype.getCategory()==11){
	// ddz="integration_list_home.htm?id="+newstype.getPid()+"&typeId="+newstype.getId()+"&typeA="+newstype.getCategory();
	// }
	//
	// strBuilder.append("<tr>");
	// strBuilder.append(" <td height=\"23\" align=\"left\" valign=\"middle\" background=\"background\" style=\"PADDING-LEFT: 20px\">");
	// strBuilder.append("<img height=\"18\" src=\""+request.getSession().getServletContext().getContextPath()+"/default/images/bit05.gif\" width=\"15\" align=\"absmiddle\" />");
	// strBuilder.append("<a  onclick=\"javascript:ShowFLT("+(i+1)+")\" href=\""+ddz+"\">"+newstype.getName()+"</a> </td></tr>");
	//
	// if(ddz.equals("javascript:void(null)")){
	// strBuilder.append(" <tr id=\"LM"+(i+1)+"\" style=\"DISPLAY: none\"><td align=\"left\" valign=\"middle\">");
	// strBuilder.append("<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">");
	// integrations=integrationService.gettotal3(newstype.getPid(),newstype.getId());
	// for(int j=0;j<integrations.size();j++){
	// String
	// dz="integration_content.htm?id="+integrations.get(j).getId()+"&typeId="+Lid;
	// if(integrations.get(j).getCategory()==22){
	// dz="integration_spcontent.htm?id="+integrations.get(j).getId()+"&typeId="+Lid;
	// }
	// strBuilder.append("<tr><td style=\"PADDING-LEFT: 40px\" height=\"23\">");
	// strBuilder.append("<img height=\"7\" src=\""+request.getSession().getServletContext().getContextPath()+"/default/images/bit06.gif\" width=\"8\" align=\"absmiddle\" />");
	// strBuilder.append("<a title=\""+integrations.get(j).getName()+"\" href=\""+dz+"\" >"+integrations.get(j).getName()+"</a></td></tr>");
	// strBuilder.append("<tr><td background=\"background\" height=\"3\"></td></tr>");
	// }
	// strBuilder.append("</table>");
	// }
	//
	// }
	// strBuilder.append("</td></tr></table>");
	// strBuilder.append("</div>");
	// response.getWriter().print(strBuilder.toString());
	// response.getWriter().close();
	// return SUCCESS;
	// }
	//

	/***
	 * 删除全部数据
	 * 
	 * @return SUCCESS
	 * @throws SystemExceptions
	 */
	public String des() throws SystemExceptions {
		integrations = integrationService.findAll();
		for (int i = 0; i < integrations.size(); i++) {
			integrationService.remove(integrations.get(i));
		}
		return null;
	}

	public List<Integration> getIntegrations() {
		return integrations;
	}

	public void setIntegrations(List<Integration> integrations) {
		this.integrations = integrations;
	}

	public Integration getIntegration() {
		if (integration == null) {
			integration = new Integration();
		}
		return integration;
	}

	public void setIntegration(Integration integration) {
		this.integration = integration;
	}

	public List<Newstype> getNewstypes() {
		return newstypes;
	}

	public void setNewstypes(List<Newstype> newstypes) {
		this.newstypes = newstypes;
	}

	public Newstype getNewstype() {
		return newstype;
	}

	public void setNewstype(Newstype newstype) {
		this.newstype = newstype;
	}

	public Long getTypeA() {
		return typeA;
	}

	public void setTypeA(Long typeA) {
		this.typeA = typeA;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPagerSize() {
		return pagerSize;
	}

	public void setPagerSize(Integer pagerSize) {
		this.pagerSize = pagerSize;
	}

	public Integer getNextPager() {
		return nextPager;
	}

	public void setNextPager(Integer nextPager) {
		this.nextPager = nextPager;
	}

	public Integer getPrePager() {
		return prePager;
	}

	public void setPrePager(Integer prePager) {
		this.prePager = prePager;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getPdid() {
		return pdid;
	}

	public void setPdid(Long pdid) {
		this.pdid = pdid;
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

	
	
}