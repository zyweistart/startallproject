package org.zyweistartframework.persistence.factory.oracle;

import org.zyweistartframework.persistence.EntityManager;
import org.zyweistartframework.persistence.Query;

public final class OracleQuery extends Query {
	/**
	 * @see 分页语句
	 * SELECT * FROM (
	 * 		SELECT TABLE_.*, ROWNUM ROWNUM_ 
	 * 			FROM (SELECT * FROM TABLE_NAME) TABLE_ 
	 * 				WHERE ROWNUM <= 40
	 * ) WHERE ROWNUM_ >= 21
	 */
	private final static String oraclePageSql=
						   "SELECT * FROM ("+
								   "SELECT TABLE_.*, ROWNUM ROWNUM_ "+
								   		"FROM (%s)  TABLE_ "+
								   	"WHERE ROWNUM<=%s"+
						   ") WHERE ROWNUM_>%s" ;
	
	public OracleQuery(EntityManager entityManager,String nQuery){
		super(entityManager,nQuery);
	}
	
	@Override
	public String nativeSql(){
		if(getMaxResult()!=null){
			//格式化字符
			return String.format(oraclePageSql, getConvertSql().toString(),
					getFirstResult()+getMaxResult(),getFirstResult());
		}
		return getConvertSql().toString();
	}
	
}