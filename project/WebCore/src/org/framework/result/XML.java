package org.framework.result;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.framework.config.ConfigParameter;
import org.framework.entity.PageQuery;
import org.framework.entity.Root;
import org.framework.utils.LogUtils;
import org.framework.utils.PropertiesUtils;
import org.framework.utils.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.DataTypeValidation;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.controller.IActionResult;
import org.zyweistartframework.persistence.EntityFactory;
import org.zyweistartframework.utils.StackTraceInfo;

public class XML  implements IActionResult {

	public final static String RESULT="result";
	public final static String INFO="info";
	public final static String CODE="code";
	public final static String MESSAGE="message";
	
	public final static String PAGEINFO="pageinfo";
	public final static String TOTALPAGE="totalpage";
	public final static String PAGESIZE="pagesize";
	public final static String TOTALCOUNT="totalcount";
	public final static String CURRENTPAGE="currentpage";
	
	public final static String COLLECTIONS="collections";
	
	private Integer info;
	
	private Object obj;
	
	public XML(Integer info,Object obj){
		this.info=info;
		this.obj=obj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doExecute(ActionResultInvocation invocation) {
		HttpServletResponse response=invocation.getResponse();
		Document doc = new Document();
		Element root = new Element(RESULT);
		doc.setRootElement(root);
		Element eleInfo=new Element(INFO);
		root.getChildren().add(eleInfo);
		eleInfo.getChildren().add(new Element(CODE).setText(String.valueOf(info)));
		eleInfo.getChildren().add(new Element(MESSAGE).setText(PropertiesUtils.getMessage(String.valueOf(info))));
		try {
			if(obj instanceof PageQuery){
				PageQuery pq=(PageQuery)obj;
				Element elePageInfo=new Element(PAGEINFO);
				root.getChildren().add(elePageInfo);
				elePageInfo.getChildren().add(new Element(TOTALPAGE).setText(String.valueOf(pq.getTotalPage())));
				elePageInfo.getChildren().add(new Element(PAGESIZE).setText(String.valueOf(pq.getPageSize())));
				elePageInfo.getChildren().add(new Element(TOTALCOUNT).setText(String.valueOf(pq.getTotalCount())));
				elePageInfo.getChildren().add(new Element(CURRENTPAGE).setText(String.valueOf(pq.getCurrentPage())));
				Element eles=new Element(COLLECTIONS);
				root.getChildren().add(eles);
				for(Object o:pq.getEntitys()){
					if(o instanceof Root){
						EntityMember entityMember=EntityFactory.getInstance().getEntityMember(o.getClass());
						entityBuilderXML(eles,entityMember,o);
					}
				}
			}else if(obj instanceof Root){
				EntityMember entityMember=EntityFactory.getInstance().getEntityMember(obj.getClass());
				entityBuilderXML(root,entityMember,obj);
			}
			Format format = Format.getPrettyFormat();
			format.setEncoding(ConfigConstant.ENCODING);
			if(ConfigParameter.SYSTEMFLAG){
				//去除空格
				format.setIndent("");
				//去除格式
				format.setLineSeparator("");
			}
			XMLOutputter xmlOutputter = new XMLOutputter(format);
			if(response != null) {
				response.setCharacterEncoding(ConfigConstant.ENCODING);
		    	response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				xmlOutputter.output(doc, response.getWriter());
				LogUtils.logInfo("输出XML：" + xmlOutputter.outputString(doc).trim());
			} else {
				xmlOutputter.output(doc, System.out);
			}
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + "输出XML异常：" + StringUtils.nullToStrTrim(e.getMessage()));
		} finally {
			doc = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void entityBuilderXML(Element root,EntityMember entityMember,Object entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Element entityNameInfo=new Element(entityMember.getEntityName().toLowerCase());
		root.getChildren().add(entityNameInfo);
		for(PropertyMember propertyMember :entityMember.getPropertyMembers()){
			Object value=propertyMember.getGMethod().invoke(entity);
			if(value!=null){
				entityNameInfo.getChildren().add(new Element(propertyMember.getField().getName().toLowerCase()).setText(value.toString()));
			}
		}
		//@Transient注解的对象只支持转换数据型或字符型
		for(PropertyMember propertyMember :entityMember.getTransientPropertyMembers()){
			Object value=propertyMember.getGMethod().invoke(entity);
			if(value!=null){
				if(DataTypeValidation.isNumber.contains(propertyMember.getReturnTypeSimpleName())||
						DataTypeValidation.isString.equals(propertyMember.getReturnTypeSimpleName())){
					entityNameInfo.getChildren().add(new Element(propertyMember.getField().getName().toLowerCase()).setText(value.toString()));
				}else{
					LogUtils.printError("@Transient注解的"+propertyMember.getField().getName()+
							"字段数据类型为"+propertyMember.getField().getName()+"无法转换为XML ");
				}
			}
		}
		for(PropertyMember propertyMember :entityMember.getMainOneToOneProperyMembers()){
			Object value=propertyMember.getGMethod().invoke(entity);
			if(value!=null){
				entityBuilderXML(entityNameInfo,EntityFactory.getInstance().getEntityMember(value.getClass()),value);
			}
		}
		for(PropertyMember propertyMember :entityMember.getOneToOneProperyMembers()){
			Object value=propertyMember.getGMethod().invoke(entity);
			if(value!=null){
				entityBuilderXML(entityNameInfo,EntityFactory.getInstance().getEntityMember(value.getClass()),value);
			}
		}
		for(PropertyMember propertyMember :entityMember.getManyToOnePropertyMembers()){
			Object value=propertyMember.getGMethod().invoke(entity);
			if(value!=null){
				entityBuilderXML(entityNameInfo,EntityFactory.getInstance().getEntityMember(value.getClass()),value);
			}
		}
		for(PropertyMember propertyMember :entityMember.getOneToManyPropertyMembers()){
			Object value=propertyMember.getGMethod().invoke(entity);
			if(value!=null){
				Element otm = new Element(propertyMember.getField().getName().toLowerCase());
				entityNameInfo.getChildren().add(otm);
				for(Object en:(Collection<Object>)value){
					entityBuilderXML(otm,EntityFactory.getInstance().getEntityMember(en.getClass()),en);
				}
			}
		}
		for(PropertyMember propertyMember :entityMember.getManyToManyPropertyMembers()){
			Object value=propertyMember.getGMethod().invoke(entity);
			if(value!=null){
				Element mtm = new Element(propertyMember.getField().getName().toLowerCase());
				entityNameInfo.getChildren().add(mtm);
				for(Object en:(Collection<Object>)value){
					entityBuilderXML(mtm,EntityFactory.getInstance().getEntityMember(en.getClass()),en);
				}
			}
		}
	}
}
