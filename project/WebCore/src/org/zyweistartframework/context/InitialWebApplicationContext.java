package org.zyweistartframework.context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.GlobalConfig;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.persistence.ds.DataSourceConfig;
import org.zyweistartframework.utils.FileUtils;

/**
 * @WebListener 注册监听器
 * 注解需要服务器环境：Tomcat7及以上
 */
//@WebListener
public class InitialWebApplicationContext implements ServletContextListener {
	
	private final static Log log=LogFactory.getLog(InitialWebApplicationContext.class);
	
	private final List<IServletContext> servletContexts=new ArrayList<IServletContext>();
	
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	log.info(Message.getMessage(Message.PM_1000));
    	try {
    		new GlobalEntityContext().initLoadClass();
    		//运行容顺实例
    		for(final Class<?> prototype:GlobalConfig.Contexts){
    			IServletContext context=(IServletContext)GlobalEntityContext.getGlobalcontextinjection().createInstance(prototype);
    			servletContexts.add(context);
    			context=null;
    		}
    		for(final IServletContext context:servletContexts){
    			context.initialized(servletContextEvent);
    		}
    		//自动生成表
    		if(ConfigConstant.GENERATETABLE){
    	    	servletContextEvent.getServletContext().log(Message.getMessage(Message.PM_1001));
    	    	GlobalEntityContext.getGlobalcontextinjection().getEntityManager().generateTable();
    	    	GlobalEntityContext.getGlobalcontextinjection().getEntityManager().finalize();
    		}
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e.getMessage());
		}
    }
    /**
     * 关闭服务器容器时触发
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	//析构容顺实例
		for(IServletContext context:servletContexts){
			context.destroyed(servletContextEvent);
		}
		try {
			if(GlobalEntityContext.getGlobalcontextinjection()!=null){
				GlobalEntityContext.getGlobalcontextinjection().finalize();
			}
			DataSourceConfig.closeDataSource();
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e.getMessage());
		}
		if(ConfigConstant.TMPPATH!=null){
			//删除临时文件夹
        	FileUtils.remove(new File(ConfigConstant.TMPPATH));
    	}
		log.info(Message.getMessage(Message.PM_1002));
    }
    
}