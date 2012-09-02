/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.consult.action;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.consult.model.News;
import com.hightern.consult.service.NewsService;

@Scope("prototype")
@Controller("newsAction")
public class NewsAction extends BaseAction<News> {

    /**
     * 此NewsAction由系统自动生成，但仅实现了基本的CRUD 功能
     * 请根据业务的需求补全其它功能及编码
     */
    
    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_NEWS = "news_manager.htm";
    private static final String ACTION_SHOWADD_NEWS = "news_showAdd.htm";
    private static final String FORWARD_MANAGER_NEWS = "/consult/manager_news.jsp";
    private static final String FORWARD_LIST_NEWS = "/consult/list_news.jsp";
    private static final String FORWARD_SHOWADD_NEWS = "/consult/add_news.jsp";
    private static final String FORWARD_SHOWEDIT_NEWS = "/consult/edit_news.jsp";
    private static final String FORWARD_DETAIL_NEWS = "/consult/detail_news.jsp";
    @Resource(name = "newsService")
    private NewsService newsService;
    private List<News> newss;
    private News news;
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != news) {
            if (news.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_NEWS);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_NEWS);
            }
            newsService.save(news);
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
        newsService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_NEWS);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String manager() throws SystemExceptions {
        if(news == null){
            news = new News(request, null);
        }
        newss = newsService.paginated(news);
        this.setParameters(FORWARD_MANAGER_NEWS);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(news == null){
            news = new News(request, null);
        }
        newss = newsService.paginated(news);
        this.setParameters(FORWARD_LIST_NEWS);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_NEWS);
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
        news = newsService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_NEWS);
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
        news = newsService.findById(getId());
        this.setParameters(FORWARD_DETAIL_NEWS);
        return SUCCESS;
    }

    public List<News> getNewss() {
        return newss;
    }

    public void setNewss(List<News> newss) {
        this.newss = newss;
    }

    public News getNews() {
    	if(news == null){
    		news = new News();
    	}
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}