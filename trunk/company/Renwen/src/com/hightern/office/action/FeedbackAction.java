/**
 * Copyright (c) 2009. All rights reserved.
 * 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.office.action;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hightern.kernel.action.BaseAction;
import com.hightern.kernel.exception.SystemExceptions;
import com.hightern.kernel.util.DateHelper;
import com.hightern.office.model.Feedback;
import com.hightern.office.service.FeedbackService;

@Scope("prototype")
@Controller("feedbackAction")
public class FeedbackAction extends BaseAction<Feedback> {

    private static final long serialVersionUID = 1L;    
    private static final String ACTION_MANAGER_FEEDBACK = "feedback_manager.htm";
    private static final String ACTION_SHOWADD_FEEDBACK = "feedback_showAdd.htm";
    private static final String FORWARD_MANAGER_FEEDBACK = "/office/manager_feedback.jsp";
    private static final String FORWARD_LIST_FEEDBACK = "/office/list_feedback.jsp";
    private static final String FORWARD_SHOWADD_FEEDBACK = "/office/add_feedback.jsp";
    private static final String FORWARD_SHOWEDIT_FEEDBACK = "/office/edit_feedback.jsp";
    private static final String FORWARD_DETAIL_FEEDBACK = "/office/detail_feedback.jsp";
    private static final String FORWARD_BBS_FEEDBACK = "/home/mboard.jsp";
    @Resource(name = "feedbackService")
    private FeedbackService feedbackService;
    private List<Feedback> feedbacks;
    private Feedback feedback;
    
    /**保存数据（添加或修改对象）
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String save() throws SystemExceptions {
        if (null != feedback) {
            if (feedback.getId() > 0) {
                this.setParameters("数据编辑成功！", ACTION_MANAGER_FEEDBACK);
            } else {
                this.setParameters("数据保存成功！", ACTION_SHOWADD_FEEDBACK);
            }
            if(feedback.getStartDay()==null){
            	feedback.setStartDay(DateHelper.getFullDate());
            }
            feedbackService.save(feedback);
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
        feedbackService.remove(idsStringToList(getSelectedIds()));
        setParameters("删除成功！",ACTION_MANAGER_FEEDBACK);
        return SUCCESS;
    }
    
    /**
     * 管理所有
     */
    public String manager() throws SystemExceptions {
        if(feedback == null){
            feedback = new Feedback(request, null);
        }
        feedback.setName(request.getParameter("name"));
        feedback.setContact(request.getParameter("contact"));
        feedback.setEmail(request.getParameter("email"));
        feedbacks = feedbackService.paginated(feedback);
        this.setParameters(FORWARD_MANAGER_FEEDBACK);
        return SUCCESS;
    }
    
    /**
     * 查看所有信息
     * 
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String list() throws SystemExceptions {
        if(feedback == null){
            feedback = new Feedback(request, null);
        }
        feedbacks = feedbackService.paginated(feedback);
        this.setParameters(FORWARD_LIST_FEEDBACK);
        return SUCCESS;
    }
    
    /**添加导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String showAdd() throws SystemExceptions {
        this.setParameters(FORWARD_SHOWADD_FEEDBACK);
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
        feedback = feedbackService.findById(idsStringToList(getSelectedIds()).get(0));
        this.setParameters(FORWARD_SHOWEDIT_FEEDBACK);
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
        feedback = feedbackService.findById(getId());
        this.setParameters(FORWARD_DETAIL_FEEDBACK);
        return SUCCESS;
    }

    
    /***
     * 留言导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String viewmag()throws SystemExceptions{
    	
    	this.setParameters(FORWARD_BBS_FEEDBACK);
    	return SUCCESS;
    }
    

    /***
     * 留言导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String viewsave()throws SystemExceptions{
        if(feedback==null){feedback=new Feedback(request,null);}
        if(request.getParameter("name")!=null && request.getParameter("name").toString().length()>0){
        	feedback.setName(request.getParameter("name"));
        }
        if(request.getParameter("contact")!=null && request.getParameter("contact").toString().length()>0){
        	feedback.setContact(request.getParameter("contact"));
        }
        if(request.getParameter("email")!=null && request.getParameter("email").toString().length()>0){
        	feedback.setEmail(request.getParameter("email"));
        }
        if(request.getParameter("content")!=null && request.getParameter("content").toString().length()>0){
        	feedback.setContent(request.getParameter("content"));
        }
        if(feedback.getStartDay()==null){
         	feedback.setStartDay(DateHelper.getFullDate());
         }
         feedbackService.save(feedback);
        
         setParameters("/common/qisuccess.jsp","感谢，您的留言!","/home/mboard.jsp");
         
    	return SUCCESS;
    }
    
    

    /**编辑导航
     * @return SUCCESS
     * @throws SystemExceptions
     */
    public String view() throws SystemExceptions {
        if (isNullOrEmptyString(getId())) {
            throw new SystemExceptions("编号不能为空！");
        }
        feedback = feedbackService.findById(getId());
        this.setParameters("/office/edit_feedback.jsp");
        return SUCCESS;
    }
    
    
    
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Feedback getFeedback() {
    	if(feedback == null){
    		feedback = new Feedback();
    	}
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}