package org.zyweistartframework.persistence.factory.mssqlserver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.zyweistartframework.collections.HashSetString;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.exception.HibernateException;
import org.zyweistartframework.persistence.CacheSQL;
import org.zyweistartframework.persistence.EntityFactory;
import org.zyweistartframework.persistence.EntityManager;
import org.zyweistartframework.persistence.Query;
import org.zyweistartframework.persistence.ds.Session;


public final class MSSQLServerQuery extends Query {
	
	public MSSQLServerQuery(EntityManager entityManager,String nQuery){
		super(entityManager,nQuery);
	}
	
	/**
	 ***************************************************************************
	 * 第一种分页
	 * 适用于 SQL Server 2000/2005
	 * SELECT TOP 页大小 *
	 * FROM table1
	 * WHERE id NOT IN
	 * 				(
	 * 				SELECT TOP 页大小*(页数-1) id FROM table1 ORDER BY id
	 * 				)
	 * ORDER BY id
	 **************************************************************************
	 * 第二种分页
	 * 适用于 SQL Server 2000/2005
	 * SELECT TOP 页大小 *
	 * FROM table1
	 * WHERE id >
	 * 				(
	 * 				SELECT ISNULL(MAX(id),0) 
	 * 				FROM 
	 * 						(
	 * 						SELECT TOP 页大小*(页数-1) id FROM table1 ORDER BY id
	 * 						) A
	 * 				)
	 * ORDER BY id
	 ***************************************************************************
	 * 第三种分页方法
	 * 适用于 SQL Server 2005
	 * SELECT TOP 页大小 *
	 * FROM
	 * 			(
	 *         SELECT ROW_NUMBER() OVER (ORDER BY id) AS RowNumber,* FROM table1
	 *         	) A
	 * WHERE RowNumber > 页大小*(页数-1)
	 */
	@Override
	public String nativeSql(){
		if(getMaxResult()!=null){
//			StringBuilder executeSql=new StringBuilder();
//			executeSql.append("SELECT TOP "+getMaxResult()+" ");
//			executeSql.append(getNql().getColumnsBuilder());
//			executeSql.append(" FROM ");
//			executeSql.append(getNql().getTableBuilder());
//			executeSql.append(" WHERE 1=1 ");
//			EntityMember entityMember=SessionFactory.getInstance().getEntityMember(getPrototype());
//			String fieldPKName=entityMember.getPKPropertyMember().getFieldName();
//			String aliasName=new String();//别名
//			String tableName=new String();//表名
//			for(String ali:getNql().getTableMembers().keySet()){
//				EntityMember em=getNql().getTableMembers().get(ali);
//				if(em.getPrototype().equals(getPrototype())){
//					tableName=em.getTableName();
//					aliasName=ali;
//					break;
//				}
//			}
//			if(aliasName.isEmpty()){
//				throw new HibernateException("不存在当前数据表别名");
//			}
//			if(getFirstResult()!=null){
//				executeSql.append(" AND ("+aliasName+"."+fieldPKName+" NOT IN (");
//				executeSql.append("SELECT TOP "+getFirstResult()+" ");
//				executeSql.append(fieldPKName);
//				executeSql.append(" FROM ");
//				executeSql.append(tableName);
//				executeSql.append(" WHERE ");
//				executeSql.append(getNql().getWheresBuilder());
//				if(getNql().getOrderbysBuilder()!=null){
//					executeSql.append(" ORDER BY ");
//					if(getNql().getSqlParser().getOrderCols()!=null){
//						String[] orderCols=getNql().mergerSpace(getNql().getSqlParser().getOrderCols()).split(",");
//						for(String ocs:orderCols){
//							if(ocs.indexOf(".")!=-1){
//								String s=getNql().convertSql(ocs);
//								executeSql.append(s.substring(s.indexOf(".")+1));
//							}else{
//								executeSql.append(ocs);
//							}
//							executeSql.append(",");
//						}
//						executeSql.deleteCharAt(executeSql.length()-1);
//					}
//				}
//				executeSql.append("))");
//			}
//			if(getNql().getWheresBuilder()!=null){
//				executeSql.append(" AND "+getNql().getWheresBuilder());
//			}
//			if(getNql().getGroupbysBuilder()!=null){
//				executeSql.append(" GROUP BY ");
//				executeSql.append(getNql().getGroupbysBuilder());
//			}
//			if(getNql().getOrderbysBuilder()!=null){
//				executeSql.append(" ORDER BY ");
//				executeSql.append(getNql().getOrderbysBuilder());
//			}
//			return executeSql.toString();
		}
		return getConvertSql().toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(){
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
		try {
			EntityMember entityMember=EntityFactory.getInstance().getEntityMember(getPrototype());
			String nativeSql=nativeSql();
			/**
			 * 参数设值，主要是由于MSSQLServer中用到了NOT IN 分页查询
			 */
		    int j=0,num=0,length=nativeSql.length();
		    while(j<length){
			   char ca=nativeSql.charAt(j++);
			   if(ca=='?'){
				   num++;
			   }
		    }
			//设置的参数
			List<Object> parameters=new ArrayList<Object>();
			if(num>0){
				int size=getParams().size();
				for(int i=0;i<num/size;i++){
					parameters.addAll(getParams().values());
				}
			}else{
				parameters.addAll(getParams().values());
			}
			preparedStatement=getEntityManager().getSession().getConnection().prepareStatement(nativeSql);
			if(getParams()!=null){
				for(int i=0;i<parameters.size();i++	){
					preparedStatement.setObject(i+1,parameters.get(i));
				}
			}
			resultSet =preparedStatement.executeQuery();
			HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(resultSet,getPrototype());
			List<T> entitys=new ArrayList<T>();
			while(resultSet.next()){
				entitys.add((T) getEntityManager().getSingleEntity(resultSet, entityMember,getPrototype().newInstance(),null,null,null,false,resultSet.getObject(entityMember.getPKPropertyMember().getFieldName()),columnNames));
			}
			//如果全部执行正确则打印语句
			EntityFactory.getInstance().logConsole(nativeSql);
			return entitys;
		} catch (Exception e) {
	 		throw new HibernateException(Message.getMessage(Message.PM_5009,getConvertSql().getNativeNQL(),e.getMessage()));
		}finally{
			Session.closeResultSet(resultSet);
			Session.closePreparedStatement(preparedStatement);
		}
	}
}