package org.zyweistartframework.persistence.factory.mssqlserver;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
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
import org.zyweistartframework.persistence.factory.mysql.MySQLAnnotation;



public final class MSSQLServerDataContext extends EntityManager {

	public MSSQLServerDataContext(){
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
				//查询已经存在的外键约束
		        final String FOREIGN_KEYS = "SELECT NAME FROM SYS.FOREIGN_KEYS";
				ResultSet resultSet=null;
				PreparedStatement preparedStatement=null;
				DatabaseMetaData databaseMetaData=null;
				//已经存在的外键约条
				HashSetString existForeignKeys=new HashSetString();
				//执行语句
				List<String> executeBatchs=new ArrayList<String>();
				//已经存在的表集合
				HashSetString eTableCollection=new HashSetString();
				//添加表字段
				Set<String> alterTableSqls=new HashSet<String>();
				try{
					try{
						databaseMetaData=session.getConnection().getMetaData();
						//获取所有已经存在的表
						resultSet=databaseMetaData.getTables(null, null, null, null);
						while(resultSet.next()){
							eTableCollection.add(resultSet.getString("TABLE_NAME"));
						}
					}finally{
						Session.closeResultSet(resultSet);
					}
					try{
						//获取所有已经创建的约束名称
						preparedStatement=session.executeQuery(FOREIGN_KEYS);
						resultSet=preparedStatement.executeQuery();
						while(resultSet.next()){
							existForeignKeys.add(resultSet.getString("NAME"));
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
								}catch(Exception e){
									Session.closeResultSet(resultSet);
								}
							}
							//SQL字段定义字符
							HashSetString fieldDefinitions=new HashSetString();
							//主键
							if(!existColumns.contains(entityMember.getPKPropertyMember().getFieldName())){
								fieldDefinitions.add(contextAnnotation.convertSQLCreateQuery(entityMember.getPKPropertyMember(), entityMember.getTableName()));
							}
							//一般成员
							for(PropertyMember propertyMember:entityMember.getPropertyMembers()){
								if(!existColumns.contains(propertyMember.getFieldName())){
									fieldDefinitions.add(contextAnnotation.convertSQLCreateQuery(propertyMember, entityMember.getTableName()));
								}
							}
							//一对一
							for(PropertyMember propertyMember:entityMember.getMainOneToOneProperyMembers()){
								if(!existColumns.contains(propertyMember.getFieldName())){
									StringBuilder addFieldSql= new StringBuilder(PersistenceConfig.LEFTSEPARATED + propertyMember.getFieldName() + PersistenceConfig.RIGHTSEPARATED+" ");
									EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
									Id id=(Id)tarMember.getPKPropertyMember().getCurrentFieldAnnotation();
									contextAnnotation.fieldDefinition(addFieldSql,tarMember.getPKPropertyMember().getReturnTypeSimpleName(),id.length());
									OneToOne oneToOne=(OneToOne)propertyMember.getCurrentFieldAnnotation();
									//如果多对一的关系为必须则建立约束条件
									if(!oneToOne.optional()){
										addFieldSql.append(" NOT");
										 //如果为必须则创建约束条件
										 String ConstraintName = "F_" + entityMember.getTableName() +"_"+propertyMember.getFieldName();
				                         if (!existForeignKeys.equalsIgnoreCaseContains(ConstraintName)){
				                        	 alterTableSqls.add("ALTER TABLE "+PersistenceConfig.LEFTSEPARATED + entityMember.getTableName() + PersistenceConfig.RIGHTSEPARATED+
				                        			 " ADD CONSTRAINT " +ConstraintName + " FOREIGN KEY("+PersistenceConfig.LEFTSEPARATED + propertyMember.getFieldName() + PersistenceConfig.RIGHTSEPARATED+") REFERENCES " + 
				                        			 PersistenceConfig.LEFTSEPARATED+tarMember.getTableName()+ PersistenceConfig.RIGHTSEPARATED +
				                        			 "(" +PersistenceConfig.LEFTSEPARATED+ tarMember.getPKPropertyMember().getFieldName() + PersistenceConfig.RIGHTSEPARATED+ ");");
				                         }
									}
									addFieldSql.append(" NULL,");
			                        fieldDefinitions.add(addFieldSql.toString());
								}
							}
							//多对一
							for(PropertyMember propertyMember:entityMember.getManyToOnePropertyMembers()){
								if(!existColumns.contains(propertyMember.getFieldName())){
									StringBuilder addFieldSql= new StringBuilder(PersistenceConfig.LEFTSEPARATED + propertyMember.getFieldName() + PersistenceConfig.RIGHTSEPARATED+" ");
									EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
									Id id=(Id)tarMember.getPKPropertyMember().getCurrentFieldAnnotation();
									contextAnnotation.fieldDefinition(addFieldSql,tarMember.getPKPropertyMember().getReturnTypeSimpleName(),id.length());
									ManyToOne manyToOne=(ManyToOne)propertyMember.getCurrentFieldAnnotation();
									//如果多对一的关系为必须则建立约束条件
									if(!manyToOne.optional()){
										addFieldSql.append(" NOT");
										 //如果为必须则创建约束条件
										 String ConstraintName = "F_" + entityMember.getTableName() +"_"+propertyMember.getFieldName();
				                         if (!existForeignKeys.equalsIgnoreCaseContains(ConstraintName)){
				                        	 alterTableSqls.add("ALTER TABLE "+PersistenceConfig.LEFTSEPARATED + entityMember.getTableName() + PersistenceConfig.RIGHTSEPARATED+
				                        			 " ADD CONSTRAINT " +ConstraintName + " FOREIGN KEY("+PersistenceConfig.LEFTSEPARATED + propertyMember.getFieldName() + PersistenceConfig.RIGHTSEPARATED+") REFERENCES " + 
				                        			 PersistenceConfig.LEFTSEPARATED+tarMember.getTableName()+ PersistenceConfig.RIGHTSEPARATED +
				                        			 "(" +PersistenceConfig.LEFTSEPARATED+ tarMember.getPKPropertyMember().getFieldName() + PersistenceConfig.RIGHTSEPARATED+ ");");
				                         }
									}
									addFieldSql.append(" NULL,");
			                        fieldDefinitions.add(addFieldSql.toString());
								}
							}
							if(eTableCollection.equalsIgnoreCaseContains(entityMember.getTableName())){
								for(String field : fieldDefinitions){
									StringBuilder aSql=new StringBuilder();
									//生成添加字段语句(去掉NOT NULL防止出错)
									aSql.append("ALTER TABLE "+PersistenceConfig.LEFTSEPARATED +entityMember.getTableName() + PersistenceConfig.RIGHTSEPARATED+" ADD " + field.replaceAll(" NOT NULL", ""));
									aSql.deleteCharAt(aSql.length()-1);
									aSql.append(";");
									alterTableSqls.add(aSql.toString());
								}
							}else{
								StringBuilder cSql=new StringBuilder();
								cSql.append("CREATE TABLE "+PersistenceConfig.LEFTSEPARATED+entityMember.getTableName()+PersistenceConfig.RIGHTSEPARATED+"(");
								for(String field : fieldDefinitions){
									cSql.append(field);
								}
								cSql.append("PRIMARY KEY ("+PersistenceConfig.LEFTSEPARATED + entityMember.getPKPropertyMember().getFieldName()+ PersistenceConfig.RIGHTSEPARATED+"));");
								executeBatchs.add(cSql.toString());
							}
							for(PropertyMember propertyMember:entityMember.getManyToManyPropertyMembers()){
								//只有在标有JoinTable语句才能创建(防重复创建)
								if (!eTableCollection.equalsIgnoreCaseContains(propertyMember.getJoinTableName())){
									if(propertyMember.getFlag()){
										StringBuilder mcSql=new StringBuilder();
										mcSql.append("CREATE TABLE "+PersistenceConfig.LEFTSEPARATED + propertyMember.getJoinTableName() +PersistenceConfig.RIGHTSEPARATED+"(");
										mcSql.append(PersistenceConfig.LEFTSEPARATED + propertyMember.getFieldName() + PersistenceConfig.RIGHTSEPARATED+" ");
										Id id=(Id)entityMember.getPKPropertyMember().getCurrentFieldAnnotation();
										contextAnnotation.fieldDefinition(mcSql, entityMember.getPKPropertyMember().getReturnTypeSimpleName(), id.length());
										mcSql.append(" NOT NULL,");
										mcSql.append(PersistenceConfig.LEFTSEPARATED+ propertyMember.getForeignKeysFieldName() +PersistenceConfig.RIGHTSEPARATED+" ");
										EntityMember tarMember = EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
										id=(Id)tarMember.getPKPropertyMember().getCurrentFieldAnnotation();
										contextAnnotation.fieldDefinition(mcSql, tarMember.getPKPropertyMember().getReturnTypeSimpleName(), id.length());
										mcSql.append(" NOT NULL);");
										executeBatchs.add(mcSql.toString());
										 //如果为必须则创建约束条件
										 String ConstraintName = "F_" + propertyMember.getJoinTableName()+"_"+propertyMember.getFieldName();
				                         if (!existForeignKeys.equalsIgnoreCaseContains(ConstraintName)){
				                        	 alterTableSqls.add("ALTER TABLE "+PersistenceConfig.LEFTSEPARATED + propertyMember.getJoinTableName() + PersistenceConfig.RIGHTSEPARATED+
				                        			 " ADD CONSTRAINT " +ConstraintName + " FOREIGN KEY("+PersistenceConfig.LEFTSEPARATED + propertyMember.getFieldName() + PersistenceConfig.RIGHTSEPARATED+") REFERENCES " + 
				                        			 PersistenceConfig.LEFTSEPARATED+entityMember.getTableName() +PersistenceConfig.RIGHTSEPARATED+"(" +PersistenceConfig.LEFTSEPARATED+ entityMember.getPKPropertyMember().getFieldName() +PersistenceConfig.RIGHTSEPARATED+ ");");
				                         }
										 ConstraintName = "F_" + propertyMember.getJoinTableName()+"_"+propertyMember.getForeignKeysFieldName();
				                         if (!existForeignKeys.equalsIgnoreCaseContains(ConstraintName)){
				                        	 alterTableSqls.add("ALTER TABLE "+PersistenceConfig.LEFTSEPARATED +propertyMember.getJoinTableName()+ PersistenceConfig.RIGHTSEPARATED+
				                        			 " ADD CONSTRAINT " +ConstraintName + " FOREIGN KEY("+PersistenceConfig.LEFTSEPARATED + propertyMember.getForeignKeysFieldName() + PersistenceConfig.RIGHTSEPARATED+") REFERENCES " + 
				                        			 PersistenceConfig.LEFTSEPARATED+tarMember.getTableName() +PersistenceConfig.RIGHTSEPARATED+"("+PersistenceConfig.LEFTSEPARATED + tarMember.getPKPropertyMember().getFieldName() +PersistenceConfig.RIGHTSEPARATED+ ");");
				                         }
									}
								}
							}
						}
					}
					//对表的修改
					executeBatchs.addAll(alterTableSqls);
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
		return new MSSQLServerQuery(this,query);
	}
	
}