package org.zyweistartframework.persistence.ds;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.GlobalConfig;
import org.zyweistartframework.exception.DataSourceError;
import org.zyweistartframework.utils.StackTraceInfo;

public final class DataSourceConfig {
	
	private final static Log log=LogFactory.getLog(DataSourceConfig.class);
	
	private final static BasicDataSource basicDataSource ;
	
	static{
		try {
			basicDataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(GlobalConfig.ConnectionConfigs.get(ConfigConstant.DIALECT));
		} catch (Exception e) {
			log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
			throw new DataSourceError(e);
		}
	}
	/**
	 * 获取连接对象
	 */
	public static Connection getConnection() throws SQLException {
		return basicDataSource.getConnection();
	}
	/**
	 * 关闭数据源对象
	 * @throws SQLException 
	 */
	public static void closeDataSource() throws SQLException {
//		DriverManager.deregisterDriver(DriverManager.getDriver(currentConnectionProperties.getProperty(ConfigConstant.DRIVERCLASSNAME)));
		basicDataSource.close();
	}
}
