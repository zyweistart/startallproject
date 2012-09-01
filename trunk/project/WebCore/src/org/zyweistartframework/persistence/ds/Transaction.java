package org.zyweistartframework.persistence.ds;

import java.sql.Connection;
import java.sql.SQLException;

import org.zyweistartframework.config.Message;

public final class Transaction {
	
	private Connection connection;
	/**
	 * 计数器记录当前事务开启过几次
	 */
	private Integer count=0;
	
	private Session session;
	
	public Transaction(Session session){
		this.session=session;
	}
	
	public Connection getConnection() throws SQLException{
		if(connection==null){
			connection=session.getConnection();
		}
		return connection;
	}
	/**
	 * 开始事务
	 */
	public void beginTrans() throws SQLException{
		if(count==0){
			if(getConnection().getAutoCommit()){
				/**
				 * 大多数主流数据库的默认事务等级，
				 * 保证了一个事务不会读到另一个并行事务己修改但未提交的数据，避免了“脏读取".
				 */
				getConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				getConnection().setAutoCommit(false);
			}else{
				throw new SQLException(Message.getMessage(Message.PM_5016));
			}
		}
		count++;
	}
	/**
	 * 数据库提交
	 */
	public void commitTrans() throws SQLException {
		if(count>0){
			--count;
			if(count==0){
				if(getConnection().getAutoCommit()){
					throw new SQLException(Message.getMessage(Message.PM_5017));
				}else{
					getConnection().commit();
					getConnection().setAutoCommit(true);
				}
			}
		}else{
			count=0;
		}
	}
	/**
	 * 数据库回滚
	 */
	public void rollbackTrans() throws SQLException {
		if(count>0){
			--count;
			if(count==0){
				if(getConnection().getAutoCommit()){
					throw new SQLException(Message.getMessage(Message.PM_5018));
				}else{
					getConnection().rollback();
					getConnection().setAutoCommit(true);
				}
			}
		}else{
			count=0;
		}
	}
}
