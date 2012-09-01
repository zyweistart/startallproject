package org.zyweistartframework.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zyweistartframework.context.IServletContext;
import org.zyweistartframework.exception.ConfigInitError;
import org.zyweistartframework.utils.ClassLoaderUtils;
/**
 * 全局配置
 * @author Start
 */
public final class GlobalConfig {

	static{
		Constants=new HashMap<String,String>();
		Interceptors=new ArrayList<Class<?>>();
		Contexts=new ArrayList<Class<?>>();
		Persistents=new HashMap<String,String>();
		ConnectionConfigs=new HashMap<String,Properties>();
		//初始化配置文件
		new GlobalConfig().loadConfigFile();
	}
	/**
	 * 常量配置
	 */
	public final static Map<String,String> Constants;
	/**
	 * 拦截器列表
	 */
	public final static List<Class<?>> Interceptors;
	/**
	 * 初始化时容器的列表
	 */
	public final static List<Class<?>> Contexts;
	/**
	 * 持久化配置
	 */
	public final static Map<String,String> Persistents;
	/**
	 * 连接池对象
	 */
	public final static Map<String,Properties> ConnectionConfigs;
	/**
	 * 加载解析配置文件
	 */
	private void loadConfigFile() {
		Constants.clear();
		Interceptors.clear();
		Contexts.clear();
		Persistents.clear();
		ConnectionConfigs.clear();
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder=null;
		try{
			factory=DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			///////////////////////////////加载默认配置//////////////////////////////////////
			readXml(builder.parse(ClassLoaderUtils.getResourceAsStream(XMLConfigConstants.FRAMEWORKCONFIGFILEPATH,GlobalConfig.class)));
			///////////////////////////////加载自定义配置//////////////////////////////////////
			readXml(builder.parse(new File(XMLConfigConstants.APPCONFIGFILEPATH)));
		} catch (Exception e) {
			throw new ConfigInitError(e);
		}finally{
			builder=null;
			factory=null;
		}
	}
	
	private void readXml(Document document) {
		NodeList  mainNodeList =document.getChildNodes();
		for(int i=0;i<mainNodeList.getLength();i++){
			Node mainNode=mainNodeList.item(i);
			NodeList nodes=mainNode.getChildNodes();
			for(int j=0;j<nodes.getLength();j++){
				Node node=nodes.item(j);
				if(node.getNodeName().equals(XMLConfigConstants.CONSTANTS)){
					//常量配置
					NodeList childNodes=node.getChildNodes();
					for(int k=0;k<childNodes.getLength();k++){
						Node childNode=childNodes.item(k);
						if(childNode.getNodeName().equals(XMLConfigConstants.CONSTANT)){
							NamedNodeMap nodeAtts=childNode.getAttributes();
							String key=null,value=null;
							for(int l=0;l<nodeAtts.getLength();l++){
								Node nodeAtt=nodeAtts.item(l);
								if(nodeAtt.getNodeName().equals(XMLConfigConstants.NAME)){
									key=nodeAtt.getNodeValue();
								}else if(nodeAtt.getNodeName().equals(XMLConfigConstants.VALUE)){
									value=nodeAtt.getNodeValue();
								}
							}
							if(value==null){
								value=childNode.getTextContent();
							}
							GlobalConfig.Constants.put(key, value);
						}
					}
				}else if(node.getNodeName().equals(XMLConfigConstants.INTERCEPTORS)){
					//是否重新加载拦截器配置
					Node reload=node.getAttributes().getNamedItem(XMLConfigConstants.RELOAD);
					if(reload!=null){
						if(Boolean.parseBoolean(reload.getNodeValue())){
							Interceptors.clear();
						}
					}
					//拦截器配置
					NodeList childNodes=node.getChildNodes();
					for(int k=0;k<childNodes.getLength();k++){
						Node childNode=childNodes.item(k);
						if(childNode.getNodeName().equals(XMLConfigConstants.INTERCEPTOR)){
							NamedNodeMap nodeAtts=childNode.getAttributes();
							for(int l=0;l<nodeAtts.getLength();l++){
								Node nodeAtt=nodeAtts.item(l);
								if(nodeAtt.getNodeName().equals(XMLConfigConstants.CLASS)){
									try {
										//加载时不进行初始化操作,只有在调用newInstance()方法才进行初始化操作
										GlobalConfig.Interceptors.add(Class.forName(nodeAtt.getNodeValue(),false,this.getClass().getClassLoader()));
									} catch (Exception e) {
										throw new ConfigInitError(e);
									}
								}
							}
						}
					}
				}else if(node.getNodeName().equals(XMLConfigConstants.SERVLETCONTEXT)){
					NodeList childNodes=node.getChildNodes();
					for(int k=0;k<childNodes.getLength();k++){
						Node childNode=childNodes.item(k);
						if(childNode.getNodeName().equals(XMLConfigConstants.CONTEXT)){
							NamedNodeMap nodeAtts=childNode.getAttributes();
							for(int l=0;l<nodeAtts.getLength();l++){
								Node nodeAtt=nodeAtts.item(l);
								if(nodeAtt.getNodeName().equals(XMLConfigConstants.CLASS)){
									try {
										Class<?> prototype = Class.forName(nodeAtt.getNodeValue());
										for(Class<?> inter:prototype.getInterfaces()){
											if(inter.equals(IServletContext.class)){
												//加载时不进行初始化操作,只有在调用newInstance()方法才进行初始化操作
												GlobalConfig.Contexts.add(Class.forName(nodeAtt.getNodeValue(),false,this.getClass().getClassLoader()));
												break;
											}
										}
									} catch (Exception e) {
										throw new ConfigInitError(e);
									}
								}
							}
						}
					}
				}else if(node.getNodeName().equals(XMLConfigConstants.PERSISTENT)){
					//持久化配置
					NodeList childNodes=node.getChildNodes();
					for(int k=0;k<childNodes.getLength();k++){
						Node childNode=childNodes.item(k);
						if(childNode.getNodeName().equals(XMLConfigConstants.PARAM)){
							NamedNodeMap nodeAtts=childNode.getAttributes();
							String key=null,value=null;
							for(int l=0;l<nodeAtts.getLength();l++){
								Node nodeAtt=nodeAtts.item(l);
								if(nodeAtt.getNodeName().equals(XMLConfigConstants.NAME)){
									key=nodeAtt.getNodeValue();
								}else if(nodeAtt.getNodeName().equals(XMLConfigConstants.VALUE)){
									value=nodeAtt.getNodeValue();
								}
							}
							if(value==null){
								value=childNode.getTextContent();
							}
							GlobalConfig.Persistents.put(key, value);
						}else if(childNode.getNodeName().equals(XMLConfigConstants.CONNECTION)){
							NodeList perChildNodes=childNode.getChildNodes();
							Properties properties=new Properties();
							for(int c=0;c<perChildNodes.getLength();c++){
								Node perNode=perChildNodes.item(c);
								if(perNode.getNodeName().equals(XMLConfigConstants.PROPERTIE)){
									properties.put(perNode.getAttributes().getNamedItem(XMLConfigConstants.NAME).getTextContent(),
											perNode.getTextContent());
								}
							}
							ConnectionConfigs.put(childNode.getAttributes().getNamedItem(XMLConfigConstants.NAME).getTextContent(),properties);
						}
					}
				}
			}
		}
	}
	
}
class XMLConfigConstants {
	/**
	 * 框架默认的配置文件
	 */
	final static String FRAMEWORKCONFIGFILEPATH=ConfigConstant.FRAMEWORKFILEPATH+"/META-INF/StartConfig.xml";
	/**
	 * 应用的配置文件 
	 */
	final static String APPCONFIGFILEPATH=ConfigConstant.RESOURCEPATH+"/PlatformConfig.xml";
	
	//////////////////////////COMM///////////////////////////////
	final static String NAME="name";
	final static String VALUE="value";
	final static String CLASS="class";
	final static String PROPERTIE="propertie";
	
	final static String CONSTANTS="Constants";
	final static String CONSTANT="Constant";
	
	final static String INTERCEPTORS="Interceptors";
	final static String INTERCEPTOR="Interceptor";
	final static String RELOAD="reload";
	
	final static String EXCEPTIONS="Exceptions";
	final static String EXCEPTION="Exception";
	
	final static String SERVLETCONTEXT="ServletContext";
	final static String CONTEXT="Context";
	
	final static String PERSISTENT="Persistent";
	final static String PARAM="Param";
	final static String CONNECTION="Connection";
	
}