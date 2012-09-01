package org.framework.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.framework.config.ConfigParameter;
import org.framework.config.Variable;
import org.framework.entity.Root;
import org.framework.exception.AppRuntimeException;
import org.framework.utils.LogUtils;
import org.framework.utils.MD5;
import org.framework.utils.StringCheck;
import org.framework.utils.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.message.IMsg;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.config.DataTypeValidation;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.persistence.EntityFactory;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.utils.StackTraceInfo;

public class RequestUtil {
	
	private final static String METHOD_GET = "GET";
	private final static String METHOD_POST = "POST";
	
	private final static String ACCESSID="accessid";
	private final static String REQUESTTIME="requesttime";
	private final static String SIGNATURE="signature";
	
	private HttpServletRequest request;
	
	private Map<String,String> headers;
	/**
	 * 签名的数据集合
	 */
	private List<String> signatureDatas;
	
	public RequestUtil(HttpServletRequest request){
		this.request=request;
	}
	/**
	 * 获取访问ID
	 */
	public String getAccessID(){
		return StringUtils.nullToStrTrim(getHeaderValue(ACCESSID));
	}
	/**
	 * 获取请求时间
	 */
	public String getRequestTime(){
		return StringUtils.nullToStrTrim(getHeaderValue(REQUESTTIME));
	}
	/**
	 * 获取请求头中的值可以不加请求前缀
	 */
	public String getHeaderValue(String headerName){
		headerName=StringUtils.nullToStrTrim(headerName.toLowerCase());
		String value=getHeaders().get(headerName);
		if(value==null){
			value=getHeaders().get(Variable.RequestPrefix+headerName);
		}
		return value;
	}
	/**
	 * 获取请求头键值对
	 */
	public Map<String,String> getHeaders(){
		if(headers==null){
			headers=new HashMap<String,String>();
			Enumeration<String> headerNames=request.getHeaderNames();
			while(headerNames.hasMoreElements()){
				String headerName=headerNames.nextElement();
				//请求头中以x--开头的参数名称表示为请求参数
				if(headerName.startsWith(Variable.RequestPrefix)){
					if(signatureDatas==null){
						signatureDatas=new ArrayList<String>();
					}
					signatureDatas.add(headerName);
				}
				//把所有请求头名称转为小写并对请求头的值进行URL解码操作还原原始数据
				headers.put(headerName.toLowerCase(),StringUtils.decode(request.getHeader(headerName)));
			}
		}
		return headers;
	}
	/**
	 * 获取请求方的IP地址
	 */
	public String getRequestIP() {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (StringUtils.isEmpty(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (StringUtils.isEmpty(ip)) {
					ip=getHeaderValue("IP");
					if(StringUtils.isEmpty(ip)){
						ip = request.getRemoteAddr();
					}
				}
			}
		}
		if(!StringUtils.isEmpty(ip)){
			String[] ips = ip.split(Constants.COMMA);
			if(ips.length > 1) {
				for(int i=0; i<ips.length; i++) {
					ip= StringUtils.nullToStrTrim(ips[i]);
					if(!StringUtils.isEmpty(ip)) {
						//如果存在多个IP地址则默认取第一个IP地址
						break;
					}
				}
			}
		}else{
			//IP地址不能为空
			throw new AppRuntimeException(IMsg._2015);
		}
		if(!StringCheck.checkIp(ip)){
			//IP地址格式不正确
			throw new AppRuntimeException(IMsg._2016);
		}
		return ip;
	}
	
	public <T extends Root> T getData(String method, Class<T> prototype){
		return getData(method, false, false, prototype,ConfigParameter.MAX_REQUEST_BODY_CONTENT_LENGTH);
	}
	
	public <T extends Root> T getGetData(Class<T> prototype){
		return getData(METHOD_GET, false, false, prototype,ConfigParameter.MAX_REQUEST_BODY_CONTENT_LENGTH);
	}
	
	public <T extends Root> T getPostData(Class<T> prototype){
		return getData(METHOD_POST, false, false, prototype,ConfigParameter.MAX_REQUEST_BODY_CONTENT_LENGTH);
	}
	/**
	 * 上传申请数据
	 */
	public <T extends Root> T getApplyDataUpload(Class<T> prototype)  {
		return getData(METHOD_POST, true, false, prototype, ConfigParameter.MAX_APPLY_FILEUPLOADSIZE);
	}
	/**
	 *文件上传数据
	 */
	public <T extends Root> T getDataUpload(Class<T> prototype) {
		return getData(METHOD_POST, true, false, prototype, ConfigParameter.MAX_FILE_UPLOADSIZE);
	}
	/**
	 * 文件子文件数据
	 */
	public <T extends Root> T getSubDataUpload(Class<T> prototype) {
		return getData(METHOD_POST, true, false, prototype, ConfigParameter.MAX_SUBFILE_UPLOADSIZE);
	}
	/**
	 * 文本上传数据
	 */
	public <T extends Root> T getTextDataUpload(Class<T> prototype) {
		return getData(METHOD_POST, true, true, prototype, ConfigParameter.MAX_TEXT_UPLOADSIZE);
	}
	/**
	 * @param method
	 * 请求的方法GET、POST
	 * @param isFileUpload
	 * 文件上传标记
	 * @param nullflag
	 * 请求内容是否可以为空
	 * @param prototype
	 * @param length
	 * 请求的内容长度
	 * @return
	 */
	private <T extends Root> T getData(String method, boolean fileUploadflag, boolean nullflag, Class<T> prototype, long length) {
		//请求时间验证前后10分钟有效
		StringCheck.checkRequesttime(getRequestTime(),Variable.TEN_MILLISECOND);
		//访问方式验证
		if (!method.equalsIgnoreCase(request.getMethod())) {
			throw new AppRuntimeException(IMsg._405);
		}
		if(method.equalsIgnoreCase(METHOD_GET)) {
			//请求参数的长度验证例：http://127.0.0.1/webcore.htm?id=1&name=test中的id=1&name=test的长度
			if(StringUtils.nullToStrTrim(request.getQueryString()).length() > ConfigParameter.MAX_REQUEST_QUERYSTRING_LENGTH) {
				throw new AppRuntimeException(IMsg._414);
			}
		}else if(method.equalsIgnoreCase(METHOD_POST)) {
			//主体内容的大小
			long contentLength = request.getContentLength();
			if(fileUploadflag) {
				if(contentLength == 0) {
					if(!nullflag) {
						throw new AppRuntimeException(IMsg._411);
					}
				} else {
					if(contentLength > length) {
						throw new AppRuntimeException(IMsg._413);
					}
				}
			} else {
				if(contentLength > ConfigParameter.MAX_REQUEST_BODY_CONTENT_LENGTH) {
					throw new AppRuntimeException(IMsg._413);
				}
			}
		}
		T entity=null;
		if(prototype!=null){
			//注入数据到对象
			try {
				entity = prototype.newInstance();
			} catch (Exception e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
				throw new AppRuntimeException(IMsg._500);
			}
			EntityMember entityMember=EntityFactory.getInstance().getEntityMember(prototype);
			Set<PropertyMember> pMembers=new HashSet<PropertyMember>();
			pMembers.add(entityMember.getPKPropertyMember());
			pMembers.addAll(entityMember.getPropertyMembers());
			pMembers.addAll(entityMember.getTransientPropertyMembers());
			for(PropertyMember pMember:pMembers){
				Object value=getHeaderValue(pMember.getField().getName());
				if(value!=null){
					//数据验证
					if(pMember.getCurrentFieldAnnotation()!=null){
						if(pMember.getCurrentFieldAnnotation().annotationType().equals(Column.class)){
							Column column=(Column)pMember.getCurrentFieldAnnotation();
							if(StringUtils.getLength(String.valueOf(value))>column.length()){
								throw new AppRuntimeException(IMsg._2012);
							}
						}else if(pMember.getCurrentFieldAnnotation().annotationType().equals(Temporal.class)){
							if(!StringCheck.checkTime(String.valueOf(value))){
								//TODO:时间检测
								System.out.println("时间注入异常");
							}
						}
					}
					if(DataTypeValidation.isBoolean.contains(pMember.getReturnTypeSimpleName())){
						if(StringUtils.isNotEmpty(String.valueOf(value))){
							value=StringUtils.nullToBoolean(String.valueOf(value));
						}
					}else if(DataTypeValidation.isInteger.contains(pMember.getReturnTypeSimpleName())){
						if(StringUtils.isNotEmpty(String.valueOf(value))){
							value=StringUtils.nullToIntZero(String.valueOf(value));
						}
					}else if(DataTypeValidation.isLong.contains(pMember.getReturnTypeSimpleName())){
						if(StringUtils.isNotEmpty(String.valueOf(value))){
							value=StringUtils.nullToLongZero(String.valueOf(value));
						}
					}else if(DataTypeValidation.isDouble.contains(pMember.getReturnTypeSimpleName())){
						if(StringUtils.isNotEmpty(String.valueOf(value))){
							value=StringUtils.nullToDoubleZero(String.valueOf(value));
						}
					}else if(DataTypeValidation.isEnum.contains(pMember.getReturnTypeSimpleName())){
						if(StringUtils.isNotEmpty(String.valueOf(value))){
							try {
								value = Enum.class.getDeclaredMethod(Constants.VALUEOF,new Class<?>[] { Class.class, String.class }).
										invoke(null, new Object[] {pMember.getSMethod().getParameterTypes()[0],value});
							} catch (Exception e) {
								Throwable cause=e.getCause();
								if(cause!=null){
									LogUtils.logError(StackTraceInfo.getTraceInfo() + cause.getMessage());
								}else{
									LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
								}
								throw new AppRuntimeException(IMsg._2011);
							}
						}
					}
					try {
						pMember.getSMethod().invoke(entity,value);
					} catch (Exception e) {
						Throwable cause=e.getCause();
						if(cause!=null){
							LogUtils.logError(StackTraceInfo.getTraceInfo() + cause.getMessage());
						}else{
							LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
						}
						throw new AppRuntimeException(IMsg._2011);
					} 
				}
			}
		}
		return entity;
	}
	/**
	 * 获取XML主体文档
	 */
	public Element getXMLDocumentRoot(){
		try {
			Document  document=new SAXBuilder().build(request.getInputStream());
			return document.getRootElement();
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
			throw new AppRuntimeException(IMsg._2013);
		}
	}
	/**
	 * 设置MD5签名数据.
	 * 1.签名只对请求头中以x--开头的请求参数
	 * 2.签名顺序根据元素的自然顺序 对指定列表按升序进行排序
	 * 3.签名规则：请求参数1+"\n"+请求参数2+"\n"+.......+"\n"+请求参数n+"\n"
	 */
	public void signaturesMD5(){
		//按自然顺序排序
		Collections.sort(signatureDatas);
		StringBuilder siging=new StringBuilder();
		for(String name:signatureDatas){
			siging.append(getHeaders().get(name));
			siging.append(Constants.SIGN_SPLITSTR);
		}
		if(!StringUtils.nullToStrTrim(MD5.md5(siging.toString())).
				equalsIgnoreCase(StringUtils.nullToStrTrim(getHeaders().get(SIGNATURE)))) {
			throw new AppRuntimeException(IMsg._2006);
		}
	}
	/**
	 * 设置HmacSHA1签名数据
	 * 1.签名只对请求头中以x--开头的请求参数
	 * 2.签名顺序根据元素的自然顺序 对指定列表按升序进行排序
	 * 3.签名规则：请求参数1+"\n"+请求参数2+"\n"+.......+"\n"+请求参数n+"\n"
	 */
	public void signaturesHmacSHA1(String key) {
		//按自然顺序排序
		Collections.sort(signatureDatas);
		StringBuilder siging=new StringBuilder();
		for(String name:signatureDatas){
			siging.append(getHeaders().get(name));
			siging.append(Constants.SIGN_SPLITSTR);
		}
		if(!StringUtils.signatureHmacSHA1(siging.toString(), key).
				equals(StringUtils.nullToStrTrim(getHeaders().get(SIGNATURE)))){
			throw new AppRuntimeException(IMsg._2006);
		}
	}
}