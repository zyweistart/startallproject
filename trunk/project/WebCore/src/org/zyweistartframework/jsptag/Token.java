package org.zyweistartframework.jsptag;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.zyweistartframework.controller.interceptor.ToKenInterceptor;


/**
 * 防止表单重复提交
 * 原理:创建一个隐藏域表单,为表单生成一个唯一标识码存入Session中在用户提交的时候判断是否对应的标识码存在,存在则通过并多Session中移除
 * @author Start
 */
public class Token extends TagSupport {

	private static final long serialVersionUID = -5266286001104941569L;

	@Override
	public int doStartTag() throws JspException {
		String UUIDVal=UUID.randomUUID().toString();
		pageContext.getSession().setAttribute(ToKenInterceptor.TOKENFORMNAME,UUIDVal);
		try {
			pageContext.getOut().print(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\"/>", ToKenInterceptor.TOKENFORMNAME,UUIDVal));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}