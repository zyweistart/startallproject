package org.zyweistartframework.context;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.config.DataTypeValidation;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.annotation.Controller;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.context.annotation.Repository;
import org.zyweistartframework.context.annotation.Service;
import org.zyweistartframework.exception.AnnoationError;
import org.zyweistartframework.exception.EntityDefinitionError;
import org.zyweistartframework.persistence.PersistenceConfig;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Id;
import org.zyweistartframework.persistence.annotation.JoinColumn;
import org.zyweistartframework.persistence.annotation.JoinTable;
import org.zyweistartframework.persistence.annotation.Lob;
import org.zyweistartframework.persistence.annotation.ManyToMany;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.MappedSuperclass;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.OneToOne;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.Transient;
import org.zyweistartframework.persistence.annotation.dt.FetchType;
import org.zyweistartframework.persistence.annotation.dt.GeneratedValue;
import org.zyweistartframework.utils.ClassUtils;

/**
 * 全局的实体容器
 */
public final class GlobalEntityContext {
	
	private final static Log log=LogFactory.getLog(GlobalEntityContext.class);
	
	static{
		actions=new HashMap<String,Class<?>>();
		injections=new HashMap<String,Class<?>>();
		entitys=new HashMap<Class<?>,EntityMember>();
		globalContextInjection=new ContextInjection();
	}
	/**
	 * 控制层的Action标有Controller的注解
	 */
	private final static Map<String,Class<?>> actions;
	/**
	 * 一般用于注入向服务层和数据访问层来自于标有Service和Repository的注解
	 */
	private final static Map<String,Class<?>> injections;
	/**
	 * 实体类标有Entity的注解
	 */
	private final static Map<Class<?>,EntityMember> entitys;
	/**
	 * 全局的注入容器
	 */
	private final static ContextInjection globalContextInjection;;
	
	public static Map<String, Class<?>> getActions() {
		return actions;
	}
	
	public static Map<String, Class<?>> getInjections() {
		return injections;
	}
	
	public static Map<Class<?>, EntityMember> getEntitys() {
		return entitys;
	}
	/**
	 * 全局的容器注入对象
	 */
	public static ContextInjection getGlobalcontextinjection() {
		return globalContextInjection;
	}
	/**
	 * 初始化加载类信息
	 */
	public void initLoadClass(){
		List<Class<?>> classes=new ArrayList<Class<?>>();
    	//扫描类文件
    	for(String packName:ConfigConstant.CLASSSCANPATH.split(Constants.COMMA)){
			ClassUtils.findAndAddClassesInPackageByFile(packName, ConfigConstant.RESOURCEPATH+packName.replace(".", Constants.FILESEPARATOR), true, classes);
		}
		for(Class<?> clasz:classes){
			//控制层
			Controller controller=clasz.getAnnotation(Controller.class);
			if(controller!=null){
				if(getActions().get(controller.value())==null){
					getActions().put(controller.value(), clasz);
					continue;
				}
				throw new AnnoationError(Message.getMessage(Message.PM_3000, controller.value()));
			}
			//服务层
			Service service=clasz.getAnnotation(Service.class);
			if(service!=null){
				if(getInjections().get(service.value())==null){
					getInjections().put(service.value(), clasz);
					continue;
				}
				throw new AnnoationError(Message.getMessage(Message.PM_3000, service.value()));
			}
			//数据访问层
			Repository repository=clasz.getAnnotation(Repository.class);
			if(repository!=null){
				if(getInjections().get(repository.value())==null){
					getInjections().put(repository.value(), clasz);
					continue;
				}
				throw new AnnoationError(Message.getMessage(Message.PM_3000, repository.value()));
			}
			Entity entity=clasz.getAnnotation(Entity.class);
			if(entity!=null){
				if(getEntitys().get(clasz)==null){
					EntityMember entityMember=new EntityMember();
					//实体名称
					entityMember.setEntityName(entity.value());
					//是否生成表的映射
					entityMember.setMapTable(entity.mapTable());
					//是否存在@Table表名注解如果不存在则默认表名使用实体类的名称
					Table table=clasz.getAnnotation(Table.class);
					if(table!=null){
						//表名
						entityMember.setTableName(table.value());
					}else{
						//如果为注解@Table则表名默认为实体名
						entityMember.setTableName(entityMember.getEntityName());
					}
					entityMember.setPrototype(clasz);
					while(true){
						if(clasz!=null){
							if(clasz.getAnnotation(Entity.class)!=null||clasz.getAnnotation(MappedSuperclass.class)!=null){
								//只获取当前类中定义的字段
								Field[] fields=clasz.getDeclaredFields();
								for(Field field : fields){
									//只对1:public、2:private、4:protected修饰的成员有效果
									if(field.getModifiers()==1||field.getModifiers()==2||field.getModifiers()==4){
										PropertyMember propertyMember=new PropertyMember();
										propertyMember.setField(field);
										//获取字段上所有的注解
										propertyMember.setCurrentFieldAnnotations(field.getAnnotations());
										//在数据表中对应的字段列的名称
										propertyMember.setFieldName(field.getName()+ConfigConstant.ADDFILTERCHAR);
										//字段的返回类型的简单名称例：java.lang.String返回String
										propertyMember.setReturnTypeSimpleName(field.getType().getSimpleName());
										PropertyDescriptor pd=null;
										try {
											//根据JavaBean的字段名称获取实体对象中get,set方法
											pd = new PropertyDescriptor(field.getName(),clasz);
											//当前字段的get方法
											propertyMember.setGMethod(pd.getReadMethod());
											//当前有字段的set方法
											propertyMember.setSMethod(pd.getWriteMethod());
										} catch (IntrospectionException e) {
											throw new EntityDefinitionError(e);
										}
										//如果该字段不映射则跳出
										Transient trans=field.getAnnotation(Transient.class);
										if(trans!=null){
											if(field.getType().isEnum()){
												propertyMember.setEnumflag(true);
												propertyMember.setReturnTypeSimpleName("enum");
											}
											entityMember.getTransientPropertyMembers().add(propertyMember);
											continue;
										}
										OneToOne oneToOne=field.getAnnotation(OneToOne.class);
										//一对一
										if(oneToOne!=null){
											propertyMember.setLazyflag(oneToOne.fetch()==FetchType.LAZY);
											//获取一对一注解对象
											propertyMember.setCurrentFieldAnnotation(oneToOne);
											//获取字段返回的类型对象
											propertyMember.setTarClass((Class<?>)field.getGenericType());
											//获取目标类的映射字段
											Field tarClassDeclaredField=null;
											String mappedBy=oneToOne.mappedBy();
											if(mappedBy.isEmpty()){
												//为""则表示获取 映射名称为当前类的实体名
												mappedBy=entityMember.getEntityName();
											}
											try {
												//获取映射方的字段 
												tarClassDeclaredField = propertyMember.getTarClass().getDeclaredField(mappedBy);
											} catch (SecurityException e) {
												throw new AnnoationError(Message.getMessage(Message.PM_3010,
														propertyMember.getTarClass().getSimpleName(),mappedBy));
											} catch (NoSuchFieldException e) {
												throw new AnnoationError(Message.getMessage(Message.PM_3011,
														propertyMember.getTarClass().getSimpleName(),mappedBy));
											}
											propertyMember.setMappedByAnnotation(tarClassDeclaredField.getAnnotation(OneToOne.class));
											if(propertyMember.getMappedByAnnotation()!=null){
												try {
													//映射方字段的get、set方法
													pd=new PropertyDescriptor(mappedBy,propertyMember.getTarClass());
													//当前字段的get方法
													propertyMember.setMappedByMethod(pd.getReadMethod());
													//当前有字段的set方法
													propertyMember.setMappedBySetMethod(pd.getWriteMethod());
												} catch (IntrospectionException e) {
													throw new EntityDefinitionError(e);
												}
												//关系映射方
												if(oneToOne.mapping()){
													JoinColumn joinColumn=field.getAnnotation(JoinColumn.class);
													if(joinColumn!=null){
														propertyMember.setFieldName(joinColumn.value());
													}
													//判断被映射方是否也定义为关系映射方
													oneToOne=tarClassDeclaredField.getAnnotation(OneToOne.class);
													if(oneToOne.mapping()){
														throw new AnnoationError(Message.getMessage(Message.PM_3008,
																clasz,field.getName(),propertyMember.getTarClass(),tarClassDeclaredField.getName()));
													}else{
														//判断被映射方是否定义了@JoinColumn，由于不为关系映射方定义该注解为错误方式
														joinColumn=tarClassDeclaredField.getAnnotation(JoinColumn.class);
														if(joinColumn!=null){
															log.warn(Message.getMessage(Message.PM_3018,
																	propertyMember.getTarClass(),tarClassDeclaredField.getName()));
														}
													}
													entityMember.getMainOneToOneProperyMembers().add(propertyMember);
												}else{
													//判断一对一对方是否为映射方
													oneToOne=tarClassDeclaredField.getAnnotation(OneToOne.class);
													if(!oneToOne.mapping()){
														//如果对方也不是映射方
														throw new AnnoationError(Message.getMessage(Message.PM_3009,
																clasz,field.getName(),propertyMember.getTarClass(),tarClassDeclaredField.getName()));
													}
													entityMember.getOneToOneProperyMembers().add(propertyMember);
												}
												continue;
											}else{
												throw new AnnoationError(Message.getMessage(Message.PM_3007,
														propertyMember.getTarClass(),tarClassDeclaredField.getName()));
											}
										}
										OneToMany oneToMany=field.getAnnotation(OneToMany.class);
										if(oneToMany!=null){
											propertyMember.setLazyflag(oneToMany.fetch()==FetchType.LAZY);
											propertyMember.setCurrentFieldAnnotation(oneToMany);
											if(oneToMany.tarEntityClass()==Object.class){
												//获取泛型中的类型
												ParameterizedType parameterizedType = (ParameterizedType)field.getGenericType();
												if(parameterizedType.getActualTypeArguments().length==1){
													propertyMember.setTarClass((Class<?>)parameterizedType.getActualTypeArguments()[0]);
												}else{
													throw new AnnoationError(Message.getMessage(Message.PM_3012,
															entityMember.getEntityName(),propertyMember.getFieldName()));
												}
											}else{
												propertyMember.setTarClass(oneToMany.tarEntityClass());
											}
											String mappedBy=oneToMany.mappedBy();
											if(mappedBy.isEmpty()){
												mappedBy=entityMember.getEntityName();
											}
											//对应的多的一方
											Field tarField=null;
											try {
												tarField = propertyMember.getTarClass().getDeclaredField(mappedBy);
											} catch (SecurityException e) {
												throw new AnnoationError(Message.getMessage(Message.PM_3010,
														propertyMember.getTarClass().getSimpleName(),mappedBy));
											} catch (NoSuchFieldException e) {
												throw new AnnoationError(Message.getMessage(Message.PM_3011,
														propertyMember.getTarClass().getSimpleName(),mappedBy));
											}
											propertyMember.setMappedByAnnotation(tarField.getAnnotation(ManyToOne.class));
											if(propertyMember.getMappedByAnnotation()!=null){
												try {
													pd=new PropertyDescriptor(mappedBy,propertyMember.getTarClass());
													//当前字段的get方法
													propertyMember.setMappedByMethod(pd.getReadMethod());
													//当前有字段的set方法
													propertyMember.setMappedBySetMethod(pd.getWriteMethod());
												} catch (IntrospectionException e) {
													throw new EntityDefinitionError(e);
												}
												JoinColumn joinColumn=tarField.getAnnotation(JoinColumn.class);
												if(joinColumn!=null){
													propertyMember.setFieldName(joinColumn.value());
												}else{
													propertyMember.setFieldName(mappedBy+ConfigConstant.ADDFILTERCHAR);
												}
												entityMember.getOneToManyPropertyMembers().add(propertyMember);
											}else{
												throw new AnnoationError(Message.getMessage(Message.PM_3005,
														entityMember.getEntityName(),propertyMember.getFieldName()));
											}
											continue;
										}
										ManyToOne manyToOne=field.getAnnotation(ManyToOne.class);
										if(manyToOne!=null){
											propertyMember.setLazyflag(manyToOne.fetch()==FetchType.LAZY);
											propertyMember.setCurrentFieldAnnotation(manyToOne);
											propertyMember.setTarClass((Class<?>)field.getGenericType());
											String mappedBy=manyToOne.mappedBy();
											if(mappedBy.isEmpty()){
												mappedBy=entityMember.getEntityName()+"s";
											}
											try {
												propertyMember.setMappedByAnnotation(propertyMember.getTarClass().getDeclaredField(mappedBy).getAnnotation(OneToMany.class));
											} catch (SecurityException e) {
												throw new AnnoationError(Message.getMessage(Message.PM_3010,
														propertyMember.getTarClass().getSimpleName(),mappedBy));
											} catch (NoSuchFieldException e) {
												throw new AnnoationError(Message.getMessage(Message.PM_3011,
														propertyMember.getTarClass().getSimpleName(),mappedBy));
											}
											if(propertyMember.getMappedByAnnotation()!=null){
												try {
													pd=new PropertyDescriptor(mappedBy,propertyMember.getTarClass());
													//当前字段的get方法
													propertyMember.setMappedByMethod(pd.getReadMethod());
													//当前有字段的set方法
													propertyMember.setMappedBySetMethod(pd.getWriteMethod());
												} catch (IntrospectionException e) {
													throw new EntityDefinitionError(e);
												}
												JoinColumn joinColumn=field.getAnnotation(JoinColumn.class);
												if(joinColumn!=null){
													propertyMember.setFieldName(joinColumn.value());
												}
												entityMember.getManyToOnePropertyMembers().add(propertyMember);
											}else{
												throw new AnnoationError(Message.getMessage(Message.PM_3005,
														entityMember.getEntityName(),propertyMember.getFieldName()));
											}
											continue;
										}
										ManyToMany manyToMany=field.getAnnotation(ManyToMany.class);
										if(manyToMany!=null){
											propertyMember.setLazyflag(manyToMany.fetch()==FetchType.LAZY);
											propertyMember.setCurrentFieldAnnotation(manyToMany);
											if(manyToMany.tarEntityClass()==Object.class){
												ParameterizedType parameterizedType = (ParameterizedType)field.getGenericType();
												if(parameterizedType.getActualTypeArguments().length==1){
													propertyMember.setTarClass((Class<?>)parameterizedType.getActualTypeArguments()[0]);
												}else{
													throw new AnnoationError(Message.getMessage(Message.PM_3012,
															entityMember.getEntityName(),propertyMember.getFieldName()));
												}
											}else{
												propertyMember.setTarClass(manyToMany.tarEntityClass());
											}
											String mappedBy=manyToMany.mappedBy();
											if(mappedBy.isEmpty()){
												mappedBy=entityMember.getEntityName()+"s";
											}
											try {
												pd=new PropertyDescriptor(mappedBy,propertyMember.getTarClass());
												//当前字段的get方法
												propertyMember.setMappedByMethod(pd.getReadMethod());
												//当前有字段的set方法
												propertyMember.setMappedBySetMethod(pd.getWriteMethod());
											} catch (IntrospectionException e) {
												throw new EntityDefinitionError(e);
											}
											//获取当前字段对应的列
											JoinColumn joinColumn=field.getAnnotation(JoinColumn.class);
											if(joinColumn!=null){
												propertyMember.setFieldName(joinColumn.value());
											}else{
												propertyMember.setFieldName(mappedBy+ConfigConstant.ADDFILTERCHAR);
											}
											//获取外键对应的字段列
											Field tarField=null;
											try {
												tarField = propertyMember.getTarClass().getDeclaredField(mappedBy);
											} catch (SecurityException e) {
												throw new AnnoationError(Message.getMessage(Message.PM_3010,
														propertyMember.getTarClass().getSimpleName(),mappedBy));
											} catch (NoSuchFieldException e) {
												throw new AnnoationError(Message.getMessage(Message.PM_3011,
														propertyMember.getTarClass().getSimpleName(),mappedBy));
											}
											joinColumn=tarField.getAnnotation(JoinColumn.class);
											if(joinColumn!=null){
												propertyMember.setForeignKeysFieldName(joinColumn.value());
											}else{
												manyToMany=tarField.getAnnotation(ManyToMany.class);
												if(manyToMany!=null){
													Entity eMap=propertyMember.getTarClass().getAnnotation(Entity.class);
													if(eMap!=null){
														propertyMember.setForeignKeysFieldName(eMap.value()+"s");
													}else{
														//@Entity not
													}
												}else{
													throw new AnnoationError(Message.getMessage(Message.PM_3006, 
															entityMember.getEntityName(),propertyMember.getFieldName()));
												}
											}
											JoinTable joinTable=field.getAnnotation(JoinTable.class);
											if(joinTable!=null){
												propertyMember.setFlag(true);
												propertyMember.setJoinTableName(joinTable.value());
												JoinTable FKJoinTable=tarField.getAnnotation(JoinTable.class);
												if(FKJoinTable!=null){
													throw new AnnoationError(Message.getMessage(Message.PM_3004, 
															entityMember.getEntityName(),propertyMember.getFieldName()));
												}
											}else{
												joinTable=tarField.getAnnotation(JoinTable.class);
												if(joinTable!=null){
													propertyMember.setJoinTableName(joinTable.value());
												}else{
													throw new AnnoationError(Message.getMessage(Message.PM_3003, 
															entityMember.getEntityName(),propertyMember.getFieldName()));
												}
											}
											entityMember.getManyToManyPropertyMembers().add(propertyMember);
											continue;
										}
										Id id=field.getAnnotation(Id.class);
										if(id!=null){
											if (id.value() == GeneratedValue.IDENTITY){
									            if (!DataTypeValidation.isNumber.contains(propertyMember.getReturnTypeSimpleName())){
									                throw new AnnoationError(Message.getMessage(Message.PM_3015,
									                		entity.value(),field.getName()));
									            }
									            //Oracle数据库不支持主键自动增长
									            if(ConfigConstant.DIALECT.equals(PersistenceConfig.ORACLEDIALECT)){
									            	throw new AnnoationError(Message.getMessage(Message.PM_3017,
									            			entity.value(),field.getName()));
									            }
									        }else if (id.value() == GeneratedValue.UID){
									            if (!DataTypeValidation.isString.contains(propertyMember.getReturnTypeSimpleName())){
									                throw new AnnoationError(Message.getMessage(Message.PM_3016,
									                		entity.value(),field.getName()));
									            }
									        }
											propertyMember.setCurrentFieldAnnotation(id);
											if(entityMember.getPKPropertyMember()==null){
												if(!id.name().isEmpty()){
													propertyMember.setFieldName(id.name());
												}
												entityMember.setPKPropertyMember(propertyMember);
											}else{
														
												throw new AnnoationError(Message.getMessage(Message.PM_3002, 
														entityMember.getEntityName()));
											}
											continue;
										}
										Column column=field.getAnnotation(Column.class);
										if(column!=null){
											if(field.getType().isEnum()){
												propertyMember.setEnumflag(true);
												propertyMember.setReturnTypeSimpleName("enum");
											}
											propertyMember.setLazyflag(column.fetch()==FetchType.LAZY);
											propertyMember.setCurrentFieldAnnotation(column);
											if(!column.name().isEmpty()){
												propertyMember.setFieldName(column.name());
											}
											entityMember.getPropertyMembers().add(propertyMember);
											continue;
										}
										Temporal temporal=field.getAnnotation(Temporal.class);
										if(temporal!=null){
											if(!DataTypeValidation.isString.contains(propertyMember.getReturnTypeSimpleName())){
												throw new AnnoationError(Message.getMessage(Message.PM_3013, 
														entity.value(),field.getName()));
											}
											propertyMember.setDateflag(true);
											propertyMember.setLazyflag(temporal.fetch()==FetchType.LAZY);
											propertyMember.setCurrentFieldAnnotation(temporal);
											if(!temporal.name().isEmpty()){
												propertyMember.setFieldName(temporal.name());
											}
											entityMember.getPropertyMembers().add(propertyMember);
											continue;
										}
										Lob lob=field.getAnnotation(Lob.class);
										if(lob!=null){
											if(!DataTypeValidation.isString.contains(propertyMember.getReturnTypeSimpleName())){
												throw new AnnoationError(Message.getMessage(Message.PM_3014, 
														entity.value(),field.getName()));
											}
											propertyMember.setLazyflag(lob.fetch()==FetchType.LAZY);
											propertyMember.setCurrentFieldAnnotation(lob);
											if(!lob.name().isEmpty()){
												propertyMember.setFieldName(lob.name());
											}
										}
										entityMember.getPropertyMembers().add(propertyMember);
									}
								}
								//获取超类
								clasz=clasz.getSuperclass();
								continue;
							}
						}
						break;
					}
					//实体主键为必须
					if(entityMember.getPKPropertyMember()==null){
						throw new AnnoationError(Message.getMessage(Message.PM_3001, 
								entityMember.getEntityName()));
					}
					//把实体加入全局容器中
					getEntitys().put(entityMember.getPrototype(), entityMember);
				}else{
					//重复注册实体
					throw new AnnoationError(Message.getMessage(Message.PM_3000, entity.value()));
				}
			}
		}
	}
	
}