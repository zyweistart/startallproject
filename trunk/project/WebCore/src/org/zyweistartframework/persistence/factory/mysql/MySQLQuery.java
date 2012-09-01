package org.zyweistartframework.persistence.factory.mysql;

import org.zyweistartframework.persistence.EntityManager;
import org.zyweistartframework.persistence.Query;

public final class MySQLQuery extends Query {
	
	public MySQLQuery(EntityManager entityManager,String nQuery){
		super(entityManager,nQuery);
	}
	
	@Override
	public String nativeSql(){
		StringBuilder executeQuery=new StringBuilder(getConvertSql().toString());
		if(getFirstResult()!=null||getFirstResult()!=0){
			executeQuery.append(" LIMIT "+getFirstResult());
			if(getMaxResult()!=null){
				executeQuery.append(","+getMaxResult());
			}
		}
		return executeQuery.toString();
	}
	
}