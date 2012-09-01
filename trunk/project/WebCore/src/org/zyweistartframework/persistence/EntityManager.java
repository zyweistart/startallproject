package org.zyweistartframework.persistence;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.collections.HashSetString;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.EntityMember;
import org.zyweistartframework.context.GlobalEntityContext;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.exception.HibernateException;
import org.zyweistartframework.persistence.annotation.Id;
import org.zyweistartframework.persistence.annotation.ManyToMany;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.OneToOne;
import org.zyweistartframework.persistence.annotation.dt.CascadeType;
import org.zyweistartframework.persistence.annotation.dt.FetchType;
import org.zyweistartframework.persistence.annotation.dt.GeneratedValue;
import org.zyweistartframework.persistence.ds.Session;
import org.zyweistartframework.persistence.ds.SessionHolder;
import org.zyweistartframework.persistence.ds.Transaction;
import org.zyweistartframework.persistence.proxy.ASMObjectProxyHandle;
import org.zyweistartframework.utils.StackTraceInfo;

/**
 * 实体管理器
 * @author Start
 */
public abstract class EntityManager {
	
	private final static Log log=LogFactory.getLog(EntityManager.class);
	
	private Session session;
	private Transaction transaction;
	private ContextAnnotation contextAnnotation;

	public EntityManager(ContextAnnotation contextAnnotation){
		this.contextAnnotation=contextAnnotation;
	}
	/**
	 * 持久化对象
	 * @return
	 * 返回持久化后主键的值
	 */
	public Object persist(Object entity){
		EntityMember entityMember=EntityFactory.getInstance().getEntityMember(entity.getClass());
		if(entityMember.isMapTable()){
			//主键的值
			Object primaryKeyValue = null;
			StringBuilder iSql = new StringBuilder();
	        StringBuilder vSql = new StringBuilder();
	        List<Object> parameters=new ArrayList<Object>();
			try{
				Id id=(Id)entityMember.getPKPropertyMember().getCurrentFieldAnnotation();
	             //如果主键不为自动增长则进行插入
	             if (id.value() != GeneratedValue.IDENTITY){
	             	//如果为UID生成策略则自动生成一个UID值
	             	if(id.value()==GeneratedValue.UID){
	             		primaryKeyValue=EntityFactory.getInstance().generateKeyBy32();
	             		//注入到实体主键中
	             		entityMember.getPKPropertyMember().getSMethod().invoke(entity, primaryKeyValue);
	             	}else if(id.value()==GeneratedValue.NONE){
	             		primaryKeyValue=entityMember.getPKPropertyMember().getGMethod().invoke(entity);
	             	}
	             	//判断主键为有值否则异常
	             	if(primaryKeyValue!=null){
	             		iSql.append(PersistenceConfig.LEFTSEPARATED);
	             		iSql.append(entityMember.getPKPropertyMember().getFieldName());
	             		iSql.append(PersistenceConfig.RIGHTSEPARATED);
	             		iSql.append(Constants.COMMA);
	             		
                 		vSql.append(Constants.KEY);
                 		vSql.append(Constants.COMMA);
                 		
	             		parameters.add(primaryKeyValue);
	             	}else{
	             		String message=Message.getMessage(Message.PM_5001, entityMember.getEntityName());
	    				log.error(StackTraceInfo.getTraceInfo()+message);
	    		 		throw new HibernateException(message);
	             	}
	             }
	             try{
	            	getTransaction().beginTrans();
	            	//一对一
		            for(PropertyMember propertyMember:entityMember.getMainOneToOneProperyMembers()){
		             	EntityMember tarMember = EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
		         		Object value = propertyMember.getGMethod().invoke(entity);
		         		OneToOne oneToOne=(OneToOne)propertyMember.getCurrentFieldAnnotation();
		         		if(value!=null){
		         			if(EntityFactory.getInstance().isCascade(oneToOne.cascade(), CascadeType.PERSIST)){
		                 		//当前对象必须不存在才能级联插入数据
		             			if(load(tarMember.getPrototype(),(Serializable)tarMember.getPKPropertyMember().getGMethod().invoke(value))==null){
		             				value=persist(value);
		             			}else{
		             				//如果数据表中已经存在则在该处返回的是主键的值
		                            value = tarMember.getPKPropertyMember().getGMethod().invoke(value);
		             			}
		                 	}else{
		                 		 //如果不级联保存则只取出主键的值
		                         value = tarMember.getPKPropertyMember().getGMethod().invoke(value);
		                 	}
		                 	if(value!=null){
		                 		iSql.append(PersistenceConfig.LEFTSEPARATED);
		                 		iSql.append(propertyMember.getFieldName());
		                 		iSql.append(PersistenceConfig.RIGHTSEPARATED);
		                 		iSql.append(Constants.COMMA);
		                 		
		                 		vSql.append(Constants.KEY);
		                 		vSql.append(Constants.COMMA);
		                 		//数据类型转换并插入到集合列表
		                 		parameters.add(EntityFactory.getInstance().dataFormatVerifyConvert(value,tarMember, propertyMember));
		                 	}
		         		}else{
		         			//如果为必须而插入的时候该对象主键为空则报异常
			         		if(!oneToOne.optional()){
			         			String message=Message.getMessage(Message.PM_5002,
			         					propertyMember.getJoinTableName(),propertyMember.getFieldName());
			    				log.error(StackTraceInfo.getTraceInfo()+message);
			    		 		throw new HibernateException(message);
			         		}
		         		}
		             }
	            	 //多对一
		             for(PropertyMember propertyMember:entityMember.getManyToOnePropertyMembers()){
		             	EntityMember tarMember = EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
		         		Object value = propertyMember.getGMethod().invoke(entity);
		         		ManyToOne manyToOne=(ManyToOne)propertyMember.getCurrentFieldAnnotation();
		         		if(value!=null){
		         			if(EntityFactory.getInstance().isCascade(manyToOne.cascade(), CascadeType.PERSIST)){
		                 		//当前对象必须不存在才能级联插入数据
		             			if(load(tarMember.getPrototype(),(Serializable)tarMember.getPKPropertyMember().getGMethod().invoke(value))==null){
		             				value=persist(value);
		             			}else{
		             				//如果数据表中已经存在则在该处返回的是主键的值
		                            value = tarMember.getPKPropertyMember().getGMethod().invoke(value);
		             			}
		                 	}else{
		                 		 //如果不级联保存则只取出主键的值
		                         value = tarMember.getPKPropertyMember().getGMethod().invoke(value);
		                 	}
		                 	if(value!=null){
		                 		iSql.append(PersistenceConfig.LEFTSEPARATED);
		                 		iSql.append(propertyMember.getFieldName());
		                 		iSql.append(PersistenceConfig.RIGHTSEPARATED);
		                 		iSql.append(Constants.COMMA);
		                 		
		                 		vSql.append(Constants.KEY);
		                 		vSql.append(Constants.COMMA);
		                 		//数据类型转换并插入到集合列表
		                 		parameters.add(EntityFactory.getInstance().dataFormatVerifyConvert(value,tarMember, propertyMember));
		                 	}
		         		}else{
		         			//如果为必须而插入的时候该对象主键为空则报异常
			         		if(!manyToOne.optional()){
			         			String message=Message.getMessage(Message.PM_5002,propertyMember.getJoinTableName(),propertyMember.getFieldName());
			    				log.error(StackTraceInfo.getTraceInfo()+message);
			    		 		throw new HibernateException(message);
			         		}
		         		}
		             }
		             //主体字段部分
		             for(PropertyMember propertyMember:entityMember.getPropertyMembers()){
		             	Object value = propertyMember.getGMethod().invoke(entity);
		             	iSql.append(PersistenceConfig.LEFTSEPARATED);
		             	iSql.append(propertyMember.getFieldName());
		             	iSql.append(PersistenceConfig.RIGHTSEPARATED);
		             	iSql.append(Constants.COMMA);
		             	EntityFactory.getInstance().handleInsertUpdateDataRow(propertyMember, vSql,true);
		             	//数据类型转换并插入到集合列表
		         		parameters.add(EntityFactory.getInstance().dataFormatVerifyConvert(value,entityMember, propertyMember));
		             }
		             if (iSql.length() > 0 && vSql.length() > 0){
		            	 iSql.insert(0,  
		            			 Constants.INSERT+
		            			 Constants.SPACE+
		            			 Constants.INTO+
		            			 Constants.SPACE+
		            			 PersistenceConfig.LEFTSEPARATED + 
		            			 entityMember.getTableName() + 
		            			 PersistenceConfig.RIGHTSEPARATED+
		            			 Constants.LEFTBRACKETS);
		            	 vSql.insert(0, "VALUES(");
		                 String nSql=iSql.toString().substring(0, iSql.length() - 1) + 
		                		 Constants.RIGHTSBRACKETS+
		                		 Constants.SPACE + 
		                		 vSql.toString().substring(0, vSql.length() - 1) + 
		                		 Constants.RIGHTSBRACKETS;
		                 //如果主键类型为自动生成则把主键设为刚刚生成的主键值
		                 if (id.value()== GeneratedValue.IDENTITY){
		                	 //主键为自动增长时则获取主键的值
		                	 primaryKeyValue =getSession().executeUpdate(nSql, true, parameters.toArray());
		                     //为对象设置自动生成的主键值
		                	 EntityFactory.getInstance().injectParameter(entity,entityMember.getPKPropertyMember().getSMethod(), 
		                    		 entityMember.getPKPropertyMember().getReturnTypeSimpleName(),primaryKeyValue);
		                 }else{
		                 	//返回的是响应的行数
		                	 getSession().executeUpdate(nSql,false, parameters.toArray());
		                 }
		             }
		             //一对一
		             for(PropertyMember propertyMember:entityMember.getOneToOneProperyMembers()){
		             	if(EntityFactory.getInstance().isCascade(((OneToOne)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.PERSIST)){
		             		Object en=propertyMember.getGMethod().invoke(entity);
		         			if(en!=null){
		         				//将当前对象的参数值置为空
		         				Object args=null;
		         				propertyMember.getSMethod().invoke(entity,args);
		         				//由于存在主键顾将映射的目标方的Set方法设为当前对象
	         					propertyMember.getMappedBySetMethod().invoke(en, entity);
	         					persist(en);
		         			}
		             	}
		             }
		             //一对多
		             for(PropertyMember propertyMember:entityMember.getOneToManyPropertyMembers()){
		             	if(EntityFactory.getInstance().isCascade(((OneToMany)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.PERSIST)){
		             		Collection<?> entitys=(Collection<?>)propertyMember.getGMethod().invoke(entity);
		         			if(entitys!=null){
		         				//将当前对象的参数值置为空
		         				Object args=null;
		         				propertyMember.getSMethod().invoke(entity,args);
		         				for(Object en:entitys){
		         					//由于存在主键顾将映射的目标方的Set方法设为当前对象
		         					propertyMember.getMappedBySetMethod().invoke(en, entity);
		         					persist(en);
		         				}
		         			}
		             	}
		             }
		             //多对多
		             for(PropertyMember propertyMember:entityMember.getManyToManyPropertyMembers()){
		             	Collection<?> entitys=(Collection<?>)propertyMember.getGMethod().invoke(entity);
	         			if(entitys!=null){
	         				String executeSQL=CacheSQL.getPMManyToManyInsertCacheSQL(entityMember, propertyMember);
	         				for(Object en:entitys){
	         					Object enPrimaryKeyValue=null;
	         					if(EntityFactory.getInstance().isCascade(((ManyToMany)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.PERSIST)){
	         						enPrimaryKeyValue=persist(en);
	        	             	}else{
	        	             		EntityMember tarMember = EntityFactory.getInstance().getEntityMember(en.getClass());
	        	             		enPrimaryKeyValue=tarMember.getPKPropertyMember().getGMethod().invoke(en);
	        	             	}
	         					if(enPrimaryKeyValue!=null){
	         						getSession().executeUpdate(executeSQL,false,primaryKeyValue,enPrimaryKeyValue);
	         					}
	         				}
	         			}
		             }
		             getTransaction().commitTrans();
	             } catch (SQLException e) {
					try {
						getTransaction().rollbackTrans();
					} catch (SQLException ex) {
	    				log.error(StackTraceInfo.getTraceInfo()+ex.getMessage());
	             		throw new HibernateException(ex);
					}
					log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
             		throw new HibernateException(e);
				}
	            return primaryKeyValue;
			}catch(Exception e){
         		throw new HibernateException(e);
			}finally{
				iSql=null;
				vSql=null;
				entityMember=null;
				parameters=null;
			}
		}else{
			String message=Message.getMessage(Message.PM_5000, entityMember.getEntityName());
			log.error(StackTraceInfo.getTraceInfo()+message);
	 		throw new HibernateException(message);
		}
	}
	/**
	 * 持久化更新对象
	 */
	public void merge(Object entity){
		EntityMember entityMember=EntityFactory.getInstance().getEntityMember(entity.getClass());
		if(entityMember.isMapTable()){
			try{
				//更新是主键必须有值
				Object primaryKeyValue=entityMember.getPKPropertyMember().getGMethod().invoke(entity);
				if(primaryKeyValue!=null){
					try{
						getTransaction().beginTrans();
						//多对多
						for(PropertyMember propertyMember:entityMember.getManyToManyPropertyMembers()){
							Collection<?> entitys=(Collection<?>)propertyMember.getGMethod().invoke(entity);
	            			if(entitys!=null){
	            				//由于多对多特殊原因顾先将对应的关系全部删除，只有当对象为NULL的时候才不做任何操作
	            				getSession().executeUpdate(CacheSQL.getPMManyToManyDeleteCacheSQL(entityMember,propertyMember),
	            						false,primaryKeyValue);
								if(EntityFactory.getInstance().isCascade(((ManyToMany)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.MERGE)){
									String executeSQL=CacheSQL.getPMManyToManyInsertCacheSQL(entityMember,propertyMember);
									for(Object en:entitys){
										EntityMember tarMember = EntityFactory.getInstance().getEntityMember(en.getClass());
										if(EntityFactory.getInstance().isCascade(((ManyToMany)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.MERGE)){
											merge(en);
										}
										Object enPrimaryKeyValue=tarMember.getPKPropertyMember().getGMethod().invoke(en);
										if(enPrimaryKeyValue!=null){
											getSession().executeUpdate(executeSQL,false,primaryKeyValue,enPrimaryKeyValue);
										}
	                				}
								}
	            			}
						}
						//一对一
						for(PropertyMember propertyMember:entityMember.getOneToOneProperyMembers()){
							if(EntityFactory.getInstance().isCascade(((OneToOne)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.MERGE)){
								Object en=propertyMember.getGMethod().invoke(entity);
								if(en!=null){
									EntityMember tarMember = EntityFactory.getInstance().getEntityMember(en.getClass());
									Object enPrimaryKeyValue=tarMember.getPKPropertyMember().getGMethod().invoke(en);
									if(enPrimaryKeyValue!=null){
										merge(en);
									}else{
										persist(en);
									}
								}
							}
						}
						//一对多
						for(PropertyMember propertyMember:entityMember.getOneToManyPropertyMembers()){
							if(EntityFactory.getInstance().isCascade(((OneToMany)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.MERGE)){
								Collection<?> entitys=(Collection<?>)propertyMember.getGMethod().invoke(entity);
								if(entitys!=null){
									for(Object en:entitys){
										EntityMember tarMember = EntityFactory.getInstance().getEntityMember(en.getClass());
										Object enPrimaryKeyValue=tarMember.getPKPropertyMember().getGMethod().invoke(en);
										if(enPrimaryKeyValue!=null){
											merge(en);
										}else{
											persist(en);
										}
									}
								}
							}
						}
						StringBuilder values = new StringBuilder();
						List<Object> parameters=new ArrayList<Object>();
						//主一对一
						for(PropertyMember propertyMember:entityMember.getMainOneToOneProperyMembers()){
							if(EntityFactory.getInstance().isCascade(((OneToOne)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.MERGE)){
								Object value=propertyMember.getGMethod().invoke(entity);
								if(value!=null){
									EntityMember tarMember =EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
									Object enPrimaryKeyValue=tarMember.getPKPropertyMember().getGMethod().invoke(value);
									if(enPrimaryKeyValue!=null){
										merge(value);
									}else{
										persist(value);
									}
									values.append(PersistenceConfig.LEFTSEPARATED);
									values.append(propertyMember.getFieldName());
									values.append(PersistenceConfig.RIGHTSEPARATED);
									values.append(Constants.EQ);
									values.append(Constants.KEY);
									values.append(Constants.COMMA);
									//该处转变为目标类的主键--数据类型转换并添加到集合列表
									parameters.add(EntityFactory.getInstance().dataFormatVerifyConvert(tarMember.getPKPropertyMember().getGMethod().invoke(value), 
											tarMember,propertyMember));
								}
							}
						}
						//多对一
						for(PropertyMember propertyMember:entityMember.getManyToOnePropertyMembers()){
							if(EntityFactory.getInstance().isCascade(((ManyToOne)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.MERGE)){
								Object value=propertyMember.getGMethod().invoke(entity);
								if(value!=null){
									EntityMember tarMember =EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
									Object enPrimaryKeyValue=tarMember.getPKPropertyMember().getGMethod().invoke(value);
									if(enPrimaryKeyValue!=null){
										merge(value);
									}else{
										persist(value);
									}
									values.append(PersistenceConfig.LEFTSEPARATED);
									values.append(propertyMember.getFieldName());
									values.append(PersistenceConfig.RIGHTSEPARATED);
									values.append(Constants.EQ);
									values.append(Constants.KEY);
									values.append(Constants.COMMA);
									//该处转变为目标类的主键--数据类型转换并添加到集合列表
									parameters.add(EntityFactory.getInstance().dataFormatVerifyConvert(tarMember.getPKPropertyMember().getGMethod().invoke(value), 
											tarMember,propertyMember));
								}
							}
						}
						//主体字段
						for(PropertyMember propertyMember:entityMember.getPropertyMembers()){
							Object value=propertyMember.getGMethod().invoke(entity);
							values.append(PersistenceConfig.LEFTSEPARATED);
							values.append(propertyMember.getFieldName());
							values.append(PersistenceConfig.RIGHTSEPARATED);
							EntityFactory.getInstance().handleInsertUpdateDataRow(propertyMember, values,false);
							//数据类型转换并添加到集合列表
							parameters.add(EntityFactory.getInstance().dataFormatVerifyConvert(value,entityMember, propertyMember));
						}
						if(values.length()>0){
							StringBuilder uSql = new StringBuilder();
							uSql.append(Constants.UPDATE);
							uSql.append(Constants.SPACE);
							uSql.append(PersistenceConfig.LEFTSEPARATED );
							uSql.append(entityMember.getTableName());
							uSql.append(PersistenceConfig.RIGHTSEPARATED);
							uSql.append(Constants.SPACE);
							uSql.append(Constants.SET);
							uSql.append(Constants.SPACE);
							
	                        StringBuilder wSql = new StringBuilder();
	                        wSql.append(Constants.SPACE);
							wSql.append(Constants.WHERE);
							wSql.append(Constants.SPACE);
	                        wSql.append(PersistenceConfig.LEFTSEPARATED );
	                        wSql.append(entityMember.getPKPropertyMember().getFieldName());
	                        wSql.append(PersistenceConfig.RIGHTSEPARATED);
	                        wSql.append(Constants.EQ);
							wSql.append(Constants.KEY);
	                        parameters.add(primaryKeyValue);
	                        getSession().executeUpdate(uSql.toString() + values.toString().substring(0, values.length() - 1) + 
	                        		wSql.toString(),false,parameters.toArray());
						}
						getTransaction().commitTrans();
					} catch (SQLException e) {
						try {
							getTransaction().rollbackTrans();
						} catch (SQLException ex) {
							log.error(StackTraceInfo.getTraceInfo()+ex.getMessage());
				     		throw new HibernateException(ex);
						}
						log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
			     		throw new HibernateException(e);
					}
				}else{
					String message=Message.getMessage(Message.PM_5001, entityMember.getEntityName());
					log.error(StackTraceInfo.getTraceInfo()+message);
			 		throw new HibernateException(message);
				}
			}catch(Exception e){
	     		throw new HibernateException(e);
			}
		}else{
			String message=Message.getMessage(Message.PM_5000,entityMember.getEntityName());
			log.error(StackTraceInfo.getTraceInfo()+message);
	 		throw new HibernateException(message);
		}
	}
	/**
	 * 持久化级联删除对象
	 */
	public void remove(Object entity){
		Class<?> prototype=entity.getClass();
		EntityMember entityMember=EntityFactory.getInstance().getEntityMember(prototype);
		if(entityMember.isMapTable()){
			try{
				//主键值
				Object primaryKeyValue=entityMember.getPKPropertyMember().getGMethod().invoke(entity);
				if(primaryKeyValue!=null){
					try {
						getTransaction().beginTrans();
						//多对多
						for(PropertyMember propertyMember:entityMember.getManyToManyPropertyMembers()){
							//首先删除所有与该对象对应的关系
							getSession().executeUpdate(CacheSQL.getPMManyToManyDeleteCacheSQL(entityMember, propertyMember),
									false,primaryKeyValue);
							if(EntityFactory.getInstance().isCascade(((ManyToMany)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.REMOVE)){
								//如果可以级联删除则再删除对象
								Collection<?> entitys=(Collection<?>)propertyMember.getGMethod().invoke(entity);
			        			if(entitys!=null){
			        				for(Object en:entitys){
			        					remove(en);
			        				}
			        			}
							}
						}
						//一对多
						for(PropertyMember propertyMember:entityMember.getOneToManyPropertyMembers()){
							if(((ManyToOne)propertyMember.getMappedByAnnotation()).optional()){
								if(EntityFactory.getInstance().isCascade(((OneToMany)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.REMOVE)){
									Collection<?> entitys=(Collection<?>)propertyMember.getGMethod().invoke(entity);
			            			if(entitys!=null){
			            				for(Object en:entitys){
			            					remove(en);
			            				}
			            			}
								}
							}else{
								//如果有相应的约束关系则统一删除
								EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
								ResultSet resultSet=null;
								PreparedStatement preparedStatement=null;
								try{
									preparedStatement=getSession().executeQuery(CacheSQL.getOneToManyCacheSQL(tarMember, propertyMember), primaryKeyValue);
									resultSet=preparedStatement.executeQuery();
									HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(resultSet,propertyMember.getTarClass());
									while(resultSet.next()){
										Object PK=resultSet.getObject(tarMember.getPKPropertyMember().getFieldName());
										remove(getSingleEntity(resultSet,tarMember,ConfigConstant.LAZYLOADMODE?
												ASMObjectProxyHandle.create(this,tarMember,PK):propertyMember.getTarClass().newInstance(),
												null,null,null,false,PK,columnNames));
									}
								}finally{
									Session.closeResultSet(resultSet);
									Session.closePreparedStatement(preparedStatement);
								}
							}
						}
						//一对一
						for(PropertyMember propertyMember:entityMember.getOneToOneProperyMembers()){
							if(((OneToOne)propertyMember.getMappedByAnnotation()).optional()){
								if(EntityFactory.getInstance().isCascade(((OneToOne)propertyMember.getCurrentFieldAnnotation()).cascade(), CascadeType.REMOVE)){
									Object en=propertyMember.getGMethod().invoke(entity);
			            			if(en!=null){
			            				remove(en);
			            			}
								}
							}else{
								//如果有相应的约束关系则统一删除
								EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
								ResultSet resultSet=null;
								PreparedStatement preparedStatement=null;
								try{
									preparedStatement=getSession().executeQuery(CacheSQL.getOneToOneCacheSQL(tarMember, propertyMember), primaryKeyValue);
									resultSet=preparedStatement.executeQuery();
									HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(resultSet,propertyMember.getTarClass());
									while(resultSet.next()){
										Object PK=resultSet.getObject(tarMember.getPKPropertyMember().getFieldName());
										remove(getSingleEntity(resultSet,tarMember,ConfigConstant.LAZYLOADMODE?
												ASMObjectProxyHandle.create(this,tarMember,PK):propertyMember.getTarClass().newInstance(),
												null,null,null,false,PK,columnNames));
									}
								}finally{
									Session.closeResultSet(resultSet);
									Session.closePreparedStatement(preparedStatement);
								}
							}
						}
						getSession().executeUpdate(CacheSQL.getDeleteCacheSQL(entityMember),false,primaryKeyValue);
						//多对一
						for(PropertyMember propertyMember:entityMember.getManyToOnePropertyMembers()){
							//只有在不为必须的时候才能删除对方的数据
							if(((ManyToOne)propertyMember.getCurrentFieldAnnotation()).optional()){
								remove(propertyMember.getGMethod().invoke(entity));
							}
						}
						//一对一
						for(PropertyMember propertyMember:entityMember.getMainOneToOneProperyMembers()){
							//只有在不为必须的时候才能删除对方的数据
							if(((OneToOne)propertyMember.getCurrentFieldAnnotation()).optional()){
								remove(propertyMember.getGMethod().invoke(entity));
							}
						}
						getTransaction().commitTrans();
					} catch (SQLException e) {
						try {
							getTransaction().rollbackTrans();
						} catch (SQLException ex) {
							log.error(StackTraceInfo.getTraceInfo()+ex.getMessage());
				     		throw new HibernateException(ex);
						}
						log.error(StackTraceInfo.getTraceInfo()+e.getMessage());
			     		throw new HibernateException(e);
					}
				}else{
					//主键为空
					String message=Message.getMessage(Message.PM_5001, entityMember.getEntityName());
    				log.error(StackTraceInfo.getTraceInfo()+message);
    		 		throw new HibernateException(message);
				}
			}catch(Exception e){
	     		throw new HibernateException(e);
			}
		}else{
			String message=Message.getMessage(Message.PM_5000, entityMember.getEntityName());
			log.error(StackTraceInfo.getTraceInfo()+message);
	 		throw new HibernateException(message);
		}
	}
	///////////////////////////////////////Select///////////////////////////////////////////////////
	/**
	 * 获取实体对象
	 * @param prototype
	 * 实体对象
	 * @param PK
	 * 主键值
	 * 没有延迟加载特性
	 */
	public <T> T load(Class<T> prototype,Serializable PK){
		return load(prototype, PK,true);
	}
	/**
	 * 获取实体对象
	 * @param prototype
	 * 实体对象
	 * @param PK
	 * 主键值
	 * @param flag
	 * <pre>
	 * 是否需要延迟加载
	 * true: 没有延迟加载特性
	 * false:获取实体对象，用于延迟加载
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public <T> T load(Class<T> prototype,Serializable PK,Boolean flag){
		EntityMember entityMember =GlobalEntityContext.getEntitys().get(prototype);
		if(entityMember.isMapTable()){
			ResultSet resultSet=null;
			PreparedStatement preparedStatement=null;
	        try{
	            preparedStatement=getSession().executeQuery(CacheSQL.getLoadCacheSQL(entityMember),PK);
	            resultSet=preparedStatement.executeQuery();
	            HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(resultSet,prototype);
	            if(resultSet.next()){
	            	 return (T) getSingleEntity(resultSet,entityMember,
	            			 flag?prototype.newInstance(): ASMObjectProxyHandle.create(this,entityMember,PK),
	            					 null,null, null,false,PK,columnNames);
	            }
	            return null;
	        }catch (Exception e){
	     		throw new HibernateException(e);
	        }finally{
	        	Session.closeResultSet(resultSet);
				Session.closePreparedStatement(preparedStatement);
	        }
		}else{
			String message=Message.getMessage(Message.PM_5000, entityMember.getEntityName());
			log.error(StackTraceInfo.getTraceInfo()+message);
	 		throw new HibernateException(message);
		}
	}
	/**
	 * 创建本地SQL语句查询
	 * @param prototype
	 * 所对象的实体对象类
	 * @param nativeSQL
	 * SQL语句
	 * @param params
	 * 附带的参数	
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> createNativeSQL(Class<T> prototype,String nativeSQL,Object...params){
		//本地语句查询，可能会是多表查询，实体对象类可以是不跟数据表有映射
		EntityMember entityMember=EntityFactory.getInstance().getEntityMember(prototype);
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
        try{
            preparedStatement=getSession().executeQuery(nativeSQL,params);
            resultSet=preparedStatement.executeQuery();
            //获取查询的字段列
            HashSetString columnNames=CacheSQL.getColumnNamesByResultSet(resultSet,prototype);
            List<T> entitys=new ArrayList<T>();
            while(resultSet.next()){
            	Object PK=resultSet.getObject(entityMember.getPKPropertyMember().getFieldName());
            	entitys.add((T) getSingleEntity(resultSet,entityMember,ConfigConstant.LAZYLOADMODE?
						ASMObjectProxyHandle.create(this,entityMember,PK):prototype.newInstance(),
						null,null,null,false,PK,columnNames));
            }
            return entitys;
        } catch (Exception e) {
     		throw new HibernateException(Message.getMessage(Message.PM_5009, nativeSQL,e.getMessage()));
		} finally{
        	Session.closeResultSet(resultSet);
			Session.closePreparedStatement(preparedStatement);
        }
	}
	/**
	 * 获取实体对象
	 * @param resultSet
	 * 本方法中不会关闭该对象资源
	 * @param entityMember
	 * @param curObject
	 * @param BackupObject
	 * @param BackupListObject
	 * @param manyToManyClass
	 * @param manyToManyInjectFlag
	 * @param primaryKeyValue
	 * 主键值
	 * @param columnNames
	 * 查询的对象字段列表
	 * @return
	 */
	public <T> T getSingleEntity(ResultSet resultSet, EntityMember entityMember, T curObject,Object backupObject,Collection<?> backupListObject,Class<?> manyToManyClass,Boolean manyToManyInjectFlag, Object primaryKeyValue,HashSetString columnNames)throws Exception{
		ResultSet tmpResultSet=null;
		PreparedStatement preparedStatement=null;
		try{
			if(columnNames.equalsIgnoreCaseContains(entityMember.getPKPropertyMember().getFieldName())){
				//主键
				EntityFactory.getInstance().resultSetInject(resultSet, entityMember.getPKPropertyMember(), curObject);
			}
			//一般成员
			for(PropertyMember propertyMember:entityMember.getPropertyMembers()){
				if(columnNames.equalsIgnoreCaseContains(propertyMember.getFieldName())){
					if(!propertyMember.isLazyflag()){
						EntityFactory.getInstance().resultSetInject(resultSet,propertyMember, curObject);
					}
				}
			}
			//主一对一
			for(PropertyMember propertyMember:entityMember.getMainOneToOneProperyMembers()){
				if(columnNames.equalsIgnoreCaseContains(propertyMember.getFieldName())){
					OneToOne oneToOne=(OneToOne)propertyMember.getCurrentFieldAnnotation();
					if(EntityFactory.getInstance().isCascade(oneToOne.cascade(), CascadeType.REFRESH)){
						if(oneToOne.fetch()==FetchType.EAGER){
							if(backupObject!=null&&backupObject.getClass()==propertyMember.getTarClass()){
								propertyMember.getSMethod().invoke(curObject,backupObject);
							}else{
								Object oneToOnePKValue=resultSet.getObject(propertyMember.getFieldName());
								if(oneToOnePKValue!=null){
									try{
										EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
										preparedStatement=getSession().executeQuery(CacheSQL.getMOneToOneCacheSQL(tarMember),oneToOnePKValue);
										tmpResultSet=preparedStatement.executeQuery();
										HashSetString tmpColumnNames=CacheSQL.getColumnNamesByResultSet(tmpResultSet,propertyMember.getTarClass());
										if(tmpResultSet.next()){
											propertyMember.getSMethod().invoke(curObject,getSingleEntity(tmpResultSet, tarMember, 
													ConfigConstant.LAZYLOADMODE?ASMObjectProxyHandle.create(this,tarMember,oneToOnePKValue):propertyMember.getTarClass().newInstance(),
													backupObject,backupListObject, null,false,oneToOnePKValue,tmpColumnNames));
										}
									}finally{
										Session.closeResultSet(tmpResultSet);
										Session.closePreparedStatement(preparedStatement);
									}
								}
							}
						}
					}
				}
			}
			//一对一
			for(PropertyMember propertyMember:entityMember.getOneToOneProperyMembers()){
				OneToOne oneToOne=(OneToOne)propertyMember.getCurrentFieldAnnotation();
				if(EntityFactory.getInstance().isCascade(oneToOne.cascade(), CascadeType.REFRESH)){
					if(oneToOne.fetch()==FetchType.EAGER){
						try{
							EntityMember tarMember =EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
							preparedStatement=getSession().executeQuery(CacheSQL.getOneToOneCacheSQL(tarMember, propertyMember),primaryKeyValue);
							tmpResultSet=preparedStatement.executeQuery();
							HashSetString tmpColumnNames=CacheSQL.getColumnNamesByResultSet(tmpResultSet,propertyMember.getTarClass());
							Object entity=null;
							if(tmpResultSet.next()){
								entity=getSingleEntity(tmpResultSet, tarMember, ConfigConstant.LAZYLOADMODE?
										ASMObjectProxyHandle.create(this,tarMember,primaryKeyValue):propertyMember.getTarClass().newInstance(),
										curObject,backupListObject,null,false, primaryKeyValue,tmpColumnNames);
							}
							propertyMember.getSMethod().invoke(curObject, entity);
						}finally{
							Session.closePreparedStatement(preparedStatement);
						}
					}
				}
			}
			//多对一
			for(PropertyMember propertyMember:entityMember.getManyToOnePropertyMembers()){
				if(columnNames.equalsIgnoreCaseContains(propertyMember.getFieldName())){
					ManyToOne manyToOne=(ManyToOne)propertyMember.getCurrentFieldAnnotation();
					if(EntityFactory.getInstance().isCascade(manyToOne.cascade(), CascadeType.REFRESH)){
						if(manyToOne.fetch()==FetchType.EAGER){
							if(backupObject!=null&&backupObject.getClass()==propertyMember.getTarClass()){
								propertyMember.getSMethod().invoke(curObject,backupObject);
							}else{
								Object ManyToOnePKValue=resultSet.getObject(propertyMember.getFieldName());
								if(ManyToOnePKValue!=null){
									try{
										EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
										preparedStatement=getSession().executeQuery(CacheSQL.getManyToOneCacheSQL(tarMember),ManyToOnePKValue);
										tmpResultSet=preparedStatement.executeQuery();
										HashSetString tmpColumnNames=CacheSQL.getColumnNamesByResultSet(tmpResultSet,propertyMember.getTarClass());
										if(tmpResultSet.next()){
											propertyMember.getSMethod().invoke(curObject,getSingleEntity(tmpResultSet, tarMember, ConfigConstant.LAZYLOADMODE?
													ASMObjectProxyHandle.create(this,tarMember,ManyToOnePKValue):propertyMember.getTarClass().newInstance(),
													backupObject,backupListObject, null,false,ManyToOnePKValue,tmpColumnNames));
										}
									}finally{
										Session.closeResultSet(tmpResultSet);
										Session.closePreparedStatement(preparedStatement);
									}
								}
							}
						}
					}
				}
			}
			//一对多
			for(PropertyMember propertyMember:entityMember.getOneToManyPropertyMembers()){
				if(columnNames.equalsIgnoreCaseContains(propertyMember.getFieldName())){
					OneToMany oneToMany=(OneToMany)propertyMember.getCurrentFieldAnnotation();
					if(EntityFactory.getInstance().isCascade(oneToMany.cascade(), CascadeType.REFRESH)){
						if(oneToMany.fetch()==FetchType.EAGER){
							try{
								EntityMember tarMember =EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
								preparedStatement=getSession().executeQuery(CacheSQL.getOneToManyCacheSQL(tarMember, propertyMember),primaryKeyValue);
								tmpResultSet=preparedStatement.executeQuery();
								HashSetString tmpColumnNames=CacheSQL.getColumnNamesByResultSet(tmpResultSet,propertyMember.getTarClass());
								Collection<Object> entitys=new ArrayList<Object>();
								while(tmpResultSet.next()){
									entitys.add(getSingleEntity(tmpResultSet, tarMember, ConfigConstant.LAZYLOADMODE?
											ASMObjectProxyHandle.create(this,tarMember,primaryKeyValue):propertyMember.getTarClass().newInstance(),
											curObject,backupListObject,null,false, primaryKeyValue,tmpColumnNames));
								}
								propertyMember.getSMethod().invoke(curObject, entitys);
							}finally{
								Session.closePreparedStatement(preparedStatement);
							}
						}
					}
				}
			}
			//多对多
			for(PropertyMember propertyMember:entityMember.getManyToManyPropertyMembers()){
				if(columnNames.equalsIgnoreCaseContains(propertyMember.getFieldName())){
					ManyToMany manyToMany=(ManyToMany)propertyMember.getCurrentFieldAnnotation();
					if(EntityFactory.getInstance().isCascade(manyToMany.cascade(), CascadeType.REFRESH)){
						if(manyToMany.fetch()==FetchType.EAGER){
							if(backupListObject!=null&&manyToManyClass!=null&&manyToManyClass==entityMember.getPrototype()&&manyToManyInjectFlag){
								propertyMember.getSMethod().invoke(curObject, backupListObject);
							}else{
								EntityMember tarMember=EntityFactory.getInstance().getEntityMember(propertyMember.getTarClass());
								Collection<Object> entitys=new ArrayList<Object>();
								try{
									preparedStatement=getSession().executeQuery(CacheSQL.getManyToManyCacheSQL(tarMember, propertyMember),primaryKeyValue);
									tmpResultSet=preparedStatement.executeQuery();
									HashSetString tmpColumnNames=CacheSQL.getColumnNamesByResultSet(tmpResultSet,propertyMember.getTarClass());
									if(backupListObject!=null&&manyToManyClass!=null&&manyToManyClass==propertyMember.getTarClass()&&!manyToManyInjectFlag){
										while(tmpResultSet.next()){
											Object PK=tmpResultSet.getObject(tarMember.getPKPropertyMember().getFieldName());
											entitys.add(getSingleEntity(tmpResultSet, tarMember,ConfigConstant.LAZYLOADMODE?
													ASMObjectProxyHandle.create(this,tarMember,PK):propertyMember.getTarClass().newInstance(),
													backupObject,backupListObject,propertyMember.getTarClass(),true,PK,tmpColumnNames));
										}
									}else{
										while(tmpResultSet.next()){
											Object PK=tmpResultSet.getObject(tarMember.getPKPropertyMember().getFieldName());
											entitys.add(getSingleEntity(tmpResultSet, tarMember, ConfigConstant.LAZYLOADMODE?
													ASMObjectProxyHandle.create(this,tarMember,PK):propertyMember.getTarClass().newInstance(),
													backupObject,entitys,entityMember.getPrototype(),false, tmpResultSet.getObject(tarMember.getPKPropertyMember().getFieldName()),tmpColumnNames));
										}
									}
								}finally{
									Session.closeResultSet(tmpResultSet);
									Session.closePreparedStatement(preparedStatement);
								}
								propertyMember.getSMethod().invoke(curObject, entitys);
							}
						}
					}
				}
			}
			return curObject;	
		}finally{
			tmpResultSet=null;
			preparedStatement=null;
		}
	}
	/**
	 * 自动生成表
	 */
	public abstract void generateTable() throws SQLException;
	/**
	 * 创建NQL查询
	 * 如果为多表需要设置prototype类型
	 */
	public abstract Query createQuery(String query);
	////////////////////////////////////////////////////////////////end select/////////////////////////////////////////////////////////////////////
	/**
	 * 数据操作管理类
	 */
	public Session getSession(){
		if(session==null){
			this.session=SessionHolder.getCurrentSession();
		}
		return session;
	}
	/**
	 * 获取事务对象
	 */
	public Transaction getTransaction(){
		if(transaction==null){
			transaction=new Transaction(getSession());
		}
		return transaction;
	}
	/**
	 * 实体注解解析类
	 */
	public ContextAnnotation getContextAnnotation() {
		return contextAnnotation;}
	
	@Override
	public void finalize() throws Throwable {
		if(session!=null){
			session.finalize();
		}
		super.finalize();
	}
	
}