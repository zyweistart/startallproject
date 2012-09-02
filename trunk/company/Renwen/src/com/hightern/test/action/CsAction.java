/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.test.action;
import java.io.File;
import java.util.List;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.test.model.Cs;
import com.hightern.test.service.CsService;
import com.hightern.test.util.bfDB;
//Mysql数据库备份 处理
@Scope("prototype")
@Controller("csAction")
public class CsAction extends BaseAction<Cs> {

    /**
     * 此CsAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_CS = "cs_manager.htm";
//    private static final String ACTION_SHOWADD_CS = "cs_showAdd.htm";
    private static final String FORWARD_MANAGER_CS = "/test/manager_cs.jsp";
    private static final String FORWARD_LIST_CS = "/test/list_cs.jsp";
    private static final String FORWARD_SHOWADD_CS = "/test/add_cs.jsp";
    private static final String FORWARD_SHOWEDIT_CS = "/test/edit_cs.jsp";
    private static final String FORWARD_DETAIL_CS = "/test/detail_cs.jsp";
    @Resource(name = "csService")
    private CsService csService;
    private List<Cs> css;
    private Cs cs;
    
    
    private String hyz;     //还原者
    
    private String username;  //用户
	private String password;  //密码
	private String host;      //IP地址
	private String PORT;      
	private String dbname;    //数据库
    
	
	
	
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != cs) {
        	File ss = new File(ServletActionContext.getServletContext()
					.getRealPath("/db/mysql"));
			if (!ss.exists()) {
				ss.mkdirs();
			}
			
            if (cs.getId() > 0) {
                this.setParameters("数据还原成功！", ACTION_MANAGER_CS);
                 csService.updateStort();  //更新状态
                 cs=csService.findById(cs.getId());
                  cs.setStort("<font color=red>最后还原<br/>"+getHyz()+"</font>");
                if(getHost()!=null && getHost().length()>0){
                bfDB.load(getUsername(), getPassword(), getHost(), getDbname(),
                		ServletActionContext.getServletContext().getRealPath("/")+"db\\mysql\\"+cs.getTitle()+".sql");
                    csService.save(cs);
                }else{
                	bfDB.load(getUsername(), getPassword(), null, getDbname(),
                    		ServletActionContext.getServletContext().getRealPath("/")+"db\\mysql\\"+cs.getTitle()+".sql");
                	csService.save(cs);
                }
                
            } else {
                this.setParameters("数据备份成功！", ACTION_MANAGER_CS);
                
                csService.updateStort();  //更新状态
               
                cs.setStort("<font color=red>最新备份</font>");
                cs.setStartDay(DateHelper.getFullDate());
                Long file=System.currentTimeMillis();
                cs.setTitle(file.toString());
                cs.setFilepath("/db/mysql/"+file+".sql");
                String backPath = ServletActionContext.getServletContext().getRealPath("/")+"db\\mysql\\"+file+".sql"; 
                if(getHost()!=null && getHost().length()>0){
                   bfDB.backup(getUsername(),getPassword(),getHost(),"",getDbname(),backPath);
                }else{
                   bfDB.backup(getUsername(),getPassword(),null,"",getDbname(),backPath);
                }
                csService.save(cs);
            }
            
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
        for (Long  n : idsStringToList(getSelectedIds())) {
			cs=csService.findById(n);
			if(cs!=null && cs.getFilepath()!=null && cs.getFilepath().length()>0){
				 File a=new File(ServletActionContext.getServletContext()
							.getRealPath(cs.getFilepath()));
				  if (a.exists()) {
						a.delete();
					}
			}
		}
        csService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_CS);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(cs == null){
            cs = new Cs(request, null);
        }
        css = csService.paginated(cs);
        this.setParameters(FORWARD_MANAGER_CS);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(cs == null){
            cs = new Cs(request, null);
        }
        css = csService.paginated(cs);
        this.setParameters(FORWARD_LIST_CS);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
    	
    	
//        bfDB aa=new bfDB();
//    	aa.config();
//    	String backPath = ServletActionContext.getServletContext().getRealPath("/")+"db\\mysql\\"+System.currentTimeMillis()+".sql"; 
//    	bfDB.backup("root","hightern",null,"","test",backPath);
//    	String backPath = ServletActionContext.getServletContext().getRealPath("/")+"db\\mysql\\1317010746312.sql"; 
//    	bfDB.load("root", "hightern", null,"test",backPath);
        setUsername("root");
    	setPassword("hightern");
    	setDbname("hbwh");
    	
    	
        this.setParameters(FORWARD_SHOWADD_CS);
        return SUCCESS;
    }

    /**编辑导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showEdit() throws SystemExceptions {
        if (isNullOrEmptyString(getId())) {
            throw new SystemExceptions("编号不能为空！");
        }
        cs = csService.findById(getId());
        setUsername("root");
    	setPassword("hightern");
    	setDbname("cyybsjyzz");
        this.setParameters(FORWARD_SHOWEDIT_CS);
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
        cs = csService.findById(getId());
        this.setParameters(FORWARD_DETAIL_CS);
        return SUCCESS;
    }

    public List<Cs> getCss() {
        return css;
    }

    public void setCss(List<Cs> css) {
        this.css = css;
    }

    public Cs getCs() {
    	if(cs == null){
    		cs = new Cs();
    	}
        return cs;
    }

    public void setCs(Cs cs) {
        this.cs = cs;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPORT() {
		return PORT;
	}

	public void setPORT(String pORT) {
		PORT = pORT;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getHyz() {
		return hyz;
	}

	public void setHyz(String hyz) {
		this.hyz = hyz;
	}
    
    
}