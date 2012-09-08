package org.zyweistartframework.persistence.factory.mysql;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zyweistartframework.collections.HashSetString;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.GlobalEntityContext;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.exception.GenerateTableError;
import org.zyweistartframework.persistence.ContextAnnotation;
import org.zyweistartframework.persistence.EntityManager;
import org.zyweistartframework.persistence.PersistenceConfig;
import org.zyweistartframework.persistence.Query;
import org.zyweistartframework.persistence.EntityFactory;
import org.zyweistartframework.persistence.annotation.Id;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.OneToOne;
import org.zyweistartframework.persistence.ds.Session;



public final class MySQLDataContext extends EntityManager {

	public MySQLDataContext(){
		super(new MySQLAnnotation());
	}
	@Override
	public void generateTable() throws SQLException {
		final Session session=getSession();
		final ContextAnnotation contextAnnotation=getContextAnnotation();
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start=System.currentTimeMillis();
				ResultSet resultSet=null;
				DatabaseMetaData databaseMetaData=null;
				//执行语句
				List<String> executeBatchs=new ArrayList<String>();
				//已经存在的表集合
				HashSetString eTableCollection=new HashSetString();
				//添加表字段
				Set<String> alterTableSqls=new HashSet<String>();
				//注释
				Set<String> comments=new HashSet<String>();
				try{
					databaseMetaData=session.getConnection().getMetaData();
					try{
						//获取所有已经存在的表
						resultSet=databaseMetaData.getTables(null, null, null, null);
						while(resultSet.next()){
							eTableCollection.add(resultSet.getString("TABLE_NAME"));
						}
					}finally{
						Session.closeResultSet(resultSet);
					}
					for(EntityMember entityMember : GlobalEntityContext.getEntitys().values()){
						//是否映射只有映射才创建表
						if(entityMember.isMapTable()){
							//已经存在的字段列
							HashSetString existColumns=new HashSetString();
							if(eTableCollection.equalsIgnoreCaseContains(entityMember.getTableName())){
								try{
									//获取所有已经创建的表的所有字段(提前是表必须已经存在)
									resultSet=databaseMetaData.getColumns(null, null,entityMember.getTableName(),null);
									while(resultSet.next()){
										existColumns.add(resultSet.getString("COLUMN_NAME"));
									}
								}finally{
									Session.closeResultSet(resultSet);
								}
							}
							//SQL字段定义字符
							HashSetString fieldDefinitions=new HashSetString();
							//主键
							if(!existColumns.equalsIgnoreCaseContains(entityMember.getPKPropertyMember().getFieldName())){
								String comment=contextAnnotation.comment(entityMember.getTableName(),
										entityMember.getPKPropertyMember().getFieldName(),entityMember.getPKPropertyMember().getCurrentFieldAnnotations());
								if(comment!=null){
									comments.add(comment);
								}
								fieldDefinitions.add(contextAnnotation.convertSQLCreateQuery(entityMember.getPKPropertyMember(), entityMember.getTableName()));
							}
							//一般成员
							for(PropertyMember propertyMember:entityMember.getPropertyMembers()){
								if(!existColumns.equalsIgnoreCaseContains(propertyMember.getFieldName())){
									String comment=contextAnnotation.comment(entityMember.getTableName(),
											entityMember.getPKPropertyMember().getFieldName(),entityMember.getPKPropertyMember().getCurrentFieldAnnotations());
									if(comment!=null){
										comments.add(comment);
									}
									fieldDefinitions.add(contextAnnotation.convertSQLCreateQuery(propertyMember, entityMember.getTableName()));
								}
							}
							//一对一
							for(PropertyMember propertyMember:entityMember.getMainOneToOneProperyMembers()){
								if(!existColumns.equalsIgnoreCaseContains(propertyMember.getFieldName())){
									String comment=contextAnnotation.comment(entityMember.getTableName(),
											entityMember.getPKPropertyMember().getFieldName(),entityMember.getPKPropertyMember().getCurrentFieldAnnotations());
									if(comment!=null){
										comments.add(comment);
									}
									StringBuilder addFieldSql= new StringBuilder();
									addFieldSql.append(PersistenceConfig.LEFTSEPARATED);
									addFieldSql.append(propertyMember.getFieldName());
									addFieldSql.append(PersistenceConfig.RIGHTSEPARATED);
									addFieldSql.append(" ");
									EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
									Id id=(Id)tarMember.getPKPropertyMember().getCurrentFieldAnnotation();
									contextAnnotation.fieldDefinition(addFieldSql,tarMember.getPKPropertyMember().getReturnTypeSimpleName(),id.length());
									OneToOne oneToOne=(OneToOne)propertyMember.getCurrentFieldAnnotation();
									//如果一对一的关系为必须则建立约束条件
									if(!oneToOne.optional()){
										addFieldSql.append(" NOT");
										//只有为必须非空时才需设置关系约束
										StringBuilder atSql=new StringBuilder();
										atSql.append("ALTER TABLE ");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(entityMember.getTableName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(" ADD CONSTRAINT ");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append("F");
										atSql.append(entityMember.getTableName());
										atSql.append("_");
										atSql.append(propertyMember.getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(" FOREIGN KEY ");
										atSql.append("(");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(propertyMember.getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(")");
										atSql.append(" REFERENCES ");
										atSql.append(tarMember.getTableName());
										atSql.append("(");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(tarMember.getPKPropertyMember().getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(")");
										alterTableSqls.add(atSql.toString());
									}
									addFieldSql.append(" NULL,");
			                        fieldDefinitions.add(addFieldSql.toString());
								}
							}
							//多对一
							for(PropertyMember propertyMember:entityMember.getManyToOnePropertyMembers()){
								if(!existColumns.equalsIgnoreCaseContains(propertyMember.getFieldName())){
									String comment=contextAnnotation.comment(entityMember.getTableName(),
											entityMember.getPKPropertyMember().getFieldName(),entityMember.getPKPropertyMember().getCurrentFieldAnnotations());
									if(comment!=null){
										comments.add(comment);
									}
									StringBuilder addFieldSql= new StringBuilder();
									addFieldSql.append(PersistenceConfig.LEFTSEPARATED);
									addFieldSql.append(propertyMember.getFieldName());
									addFieldSql.append(PersistenceConfig.RIGHTSEPARATED);
									addFieldSql.append(" ");
									EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
									Id id=(Id)tarMember.getPKPropertyMember().getCurrentFieldAnnotation();
									contextAnnotation.fieldDefinition(addFieldSql,tarMember.getPKPropertyMember().getReturnTypeSimpleName(),id.length());
									ManyToOne manyToOne=(ManyToOne)propertyMember.getCurrentFieldAnnotation();
									//如果多对一的关系为必须则建立约束条件
									if(!manyToOne.optional()){
										addFieldSql.append(" NOT");
										//只有为必须非空时才需设置关系约束
										StringBuilder atSql=new StringBuilder();
										atSql.append("ALTER TABLE ");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(entityMember.getTableName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(" ADD CONSTRAINT ");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append("F");
										atSql.append(entityMember.getTableName());
										atSql.append("_");
										atSql.append(propertyMember.getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(" FOREIGN KEY ");
										atSql.append("(");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(propertyMember.getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(")");
										atSql.append(" REFERENCES ");
										atSql.append(tarMember.getTableName());
										atSql.append("(");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(tarMember.getPKPropertyMember().getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(")");
										alterTableSqls.add(atSql.toString());
										
									}
									addFieldSql.append(" NULL,");
			                        fieldDefinitions.add(addFieldSql.toString());
								}
							}
							if(eTableCollection.equalsIgnoreCaseContains(entityMember.getTableName())){
								for(String field : fieldDefinitions){
									//生成添加字段语句(去掉NOT NULL防止出错)
									StringBuilder alterTableSql=new StringBuilder();
									alterTableSql.append("ALTER TABLE ");
									alterTableSql.append(PersistenceConfig.LEFTSEPARATED);
									alterTableSql.append(entityMember.getTableName());
									alterTableSql.append(PersistenceConfig.RIGHTSEPARATED);
									alterTableSql.append(" ADD ");
									alterTableSql.append(field.replaceAll(" NOT NULL", ""));
									alterTableSql.deleteCharAt(alterTableSql.length()-1);
//									alterTableSql.append(";");
									alterTableSqls.add(alterTableSql.toString());
								}
							}else{
								//表创建语句
								StringBuilder cSql=new StringBuilder();
								cSql.append("CREATE TABLE ");
								cSql.append(PersistenceConfig.LEFTSEPARATED);
								cSql.append(entityMember.getTableName());
								cSql.append(PersistenceConfig.RIGHTSEPARATED);
								cSql.append("(");
								for(String field : fieldDefinitions){
									cSql.append(field);
								}
								cSql.append("PRIMARY KEY(");
								cSql.append(PersistenceConfig.LEFTSEPARATED);
								cSql.append(entityMember.getPKPropertyMember().getFieldName());
								cSql.append(PersistenceConfig.RIGHTSEPARATED);
								cSql.append("))TYPE=INNODB CHARSET=UTF8;");
								executeBatchs.add(cSql.toString());
							}
							for(PropertyMember propertyMember:entityMember.getManyToManyPropertyMembers()){
								//只有在标有JoinTable语句才能创建(防重复创建)
								if (!eTableCollection.equalsIgnoreCaseContains(propertyMember.getJoinTableName())){
									if(propertyMember.getFlag()){
										String comment=contextAnnotation.comment(propertyMember.getJoinTableName(),
												propertyMember.getFieldName(),propertyMember.getCurrentFieldAnnotations());
										if(comment!=null){
											comments.add(comment);
										}
										//多对多创建表
										StringBuilder mcSql=new StringBuilder();
										mcSql.append("CREATE TABLE ");
										mcSql.append(PersistenceConfig.LEFTSEPARATED);
										mcSql.append(propertyMember.getJoinTableName());
										mcSql.append(PersistenceConfig.RIGHTSEPARATED);
										mcSql.append("(");
										mcSql.append(PersistenceConfig.LEFTSEPARATED );
										mcSql.append(propertyMember.getFieldName());
										mcSql.append(PersistenceConfig.RIGHTSEPARATED);
										mcSql.append(" ");
										Id id=(Id)entityMember.getPKPropertyMember().getCurrentFieldAnnotation();
										contextAnnotation.fieldDefinition(mcSql, entityMember.getPKPropertyMember().getReturnTypeSimpleName(), id.length());
										mcSql.append(" NOT NULL,");
										mcSql.append(PersistenceConfig.LEFTSEPARATED);
										mcSql.append(propertyMember.getForeignKeysFieldName());
										mcSql.append(PersistenceConfig.RIGHTSEPARATED);
										mcSql.append(" ");
										EntityMember tarMember = EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
										id=(Id)tarMember.getPKPropertyMember().getCurrentFieldAnnotation();
										contextAnnotation.fieldDefinition(mcSql, tarMember.getPKPropertyMember().getReturnTypeSimpleName(), id.length());
										mcSql.append(" NOT NULL");
										mcSql.append(")TYPE=INNODB CHARSET=UTF8;");
										//创建多对多关系表
										executeBatchs.add(mcSql.toString());
										//约束条件
										StringBuilder atSql=new StringBuilder();
										atSql.append("ALTER TABLE ");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(propertyMember.getJoinTableName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(" ADD CONSTRAINT ");
										atSql.append("F");
										atSql.append(propertyMember.getJoinTableName());
										atSql.append("_");
										atSql.append(propertyMember.getFieldName());
										atSql.append("1");
										atSql.append(" FOREIGN KEY ");
										atSql.append("(");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(propertyMember.getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(")");
										atSql.append(" REFERENCES ");
										atSql.append(entityMember.getTableName());
										atSql.append("(");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(entityMember.getPKPropertyMember().getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(")");
										alterTableSqls.add(atSql.toString());
										atSql.delete(0, atSql.length());
										
										atSql.append("ALTER TABLE ");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(propertyMember.getJoinTableName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(" ADD CONSTRAINT ");
										atSql.append("F");
										atSql.append(propertyMember.getJoinTableName());
										atSql.append("_");
										atSql.append(propertyMember.getForeignKeysFieldName());
										atSql.append(" FOREIGN KEY ");
										atSql.append("(");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(propertyMember.getForeignKeysFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(")");
										atSql.append(" REFERENCES ");
										atSql.append(tarMember.getTableName());
										atSql.append("(");
										atSql.append(PersistenceConfig.LEFTSEPARATED);
										atSql.append(tarMember.getPKPropertyMember().getFieldName());
										atSql.append(PersistenceConfig.RIGHTSEPARATED);
										atSql.append(")");
										alterTableSqls.add(atSql.toString());
									}else{
										String commentFore=contextAnnotation.comment(propertyMember.getJoinTableName(),
												propertyMember.getFieldName(),propertyMember.getCurrentFieldAnnotations());
										if(commentFore!=null){
											comments.add(commentFore);
										}
									}
								}
							}
						}
					}
					executeBatchs.addAll(alterTableSqls);
//					executeBatchs.addAll(comments);
					for(String sql:executeBatchs){
						session.executeUpdate(sql);
					}
					System.out.println(Message.getMessage(Message.PM_5007,
							(System.currentTimeMillis()-start)));
				}catch(SQLException e){
					throw new GenerateTableError(e);
				}finally{
					databaseMetaData=null;
					eTableCollection=null;
					alterTableSqls=null;
					session.closeConnection();
				}
			}
		}).start();
	}

	public Query createQuery(String query) {
		return new MySQLQuery(this,query);
	}

}