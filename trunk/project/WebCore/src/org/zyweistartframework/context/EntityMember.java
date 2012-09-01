package org.zyweistartframework.context;

import java.util.HashSet;
import java.util.Set;

import org.zyweistartframework.config.Message;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.exception.EntityDefinitionError;


/**
 * 实体成员
 */
public final class EntityMember {
	/**
	 * 是否创建并映射表
	 */
	private Boolean mapTable=true;
	/**
	 * 实体类名称 
	 */
	private String entityName;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 表名的注释
	 */
	private String tableNameComment;
	/**
	 * 类名
	 */
	private Class<?> prototype;
	/**
	 * 主键属性
	 */
	private PropertyMember PKPropertyMember;
	/**
	 * 普通成员
	 */
	private Set<PropertyMember> propertyMembers=new HashSet<PropertyMember>();
	/**
	 * 一对一成员
	 */
	private Set<PropertyMember> oneToOneProperyMembers=new HashSet<PropertyMember>();
	/**
	 * 一对一成员映射字段的一方
	 */
	private Set<PropertyMember> mainOneToOneProperyMembers=new HashSet<PropertyMember>();
	/**
	 * 一对多成员
	 */
	private Set<PropertyMember> oneToManyPropertyMembers=new HashSet<PropertyMember>();
	/**
	 * 多对一成员
	 */
	private Set<PropertyMember> manyToOnePropertyMembers=new HashSet<PropertyMember>();
	/**
	 * 多对多成员
	 */
	private Set<PropertyMember> manyToManyPropertyMembers=new HashSet<PropertyMember>();
	/**
	 * 表明不映射为数据表字段的列
	 */
	private Set<PropertyMember> transientPropertyMembers=new HashSet<PropertyMember>();
	
	public Boolean isMapTable() {
		return mapTable;
	}

	public void setMapTable(Boolean mapTable) {
		this.mapTable = mapTable;
	}

	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		if(tableName.length()>Constants.MAXLENGTH){
			throw new EntityDefinitionError(Message.getMessage(Message.PM_1004,
					Constants.TABLENAME,tableName,Constants.MAXLENGTH));
		}
		this.tableName = tableName;
	}
	
	public String getTableNameComment() {
		return tableNameComment;
	}

	public void setTableNameComment(String tableNameComment) {
		this.tableNameComment = tableNameComment;
	}

	public Class<?> getPrototype() {
		return prototype;
	}

	public void setPrototype(Class<?> prototype) {
		this.prototype = prototype;
	}

	public PropertyMember getPKPropertyMember() {
		return PKPropertyMember;
	}
	
	public void setPKPropertyMember(PropertyMember pKPropertyMember) {
		this.PKPropertyMember = pKPropertyMember;
	}

	public Set<PropertyMember> getOneToOneProperyMembers() {
		return oneToOneProperyMembers;
	}

	public void setOneToOneProperyMembers(
			Set<PropertyMember> oneToOneProperyMembers) {
		this.oneToOneProperyMembers = oneToOneProperyMembers;
	}
	
	public Set<PropertyMember> getMainOneToOneProperyMembers() {
		return mainOneToOneProperyMembers;
	}

	public void setMainOneToOneProperyMembers(
			Set<PropertyMember> mainOneToOneProperyMembers) {
		this.mainOneToOneProperyMembers = mainOneToOneProperyMembers;
	}

	public Set<PropertyMember> getPropertyMembers() {
		return propertyMembers;
	}
	
	public void setPropertyMembers(Set<PropertyMember> propertyMembers) {
		this.propertyMembers = propertyMembers;
	}
	
	public Set<PropertyMember> getOneToManyPropertyMembers() {
		return oneToManyPropertyMembers;
	}
	
	public void setOneToManyPropertyMembers(
			Set<PropertyMember> oneToManyPropertyMembers) {
		this.oneToManyPropertyMembers = oneToManyPropertyMembers;
	}
	
	public Set<PropertyMember> getManyToOnePropertyMembers() {
		return manyToOnePropertyMembers;
	}
	
	public void setManyToOnePropertyMembers(
			Set<PropertyMember> manyToOnePropertyMembers) {
		this.manyToOnePropertyMembers = manyToOnePropertyMembers;
	}
	
	public Set<PropertyMember> getManyToManyPropertyMembers() {
		return manyToManyPropertyMembers;
	}
	
	public void setManyToManyPropertyMembers(
			Set<PropertyMember> manyToManyPropertyMembers) {
		this.manyToManyPropertyMembers = manyToManyPropertyMembers;
	}
	
	public Set<PropertyMember> getTransientPropertyMembers() {
		return transientPropertyMembers;
	}
	
	public void setTransientPropertyMembers(
			Set<PropertyMember> transientPropertyMembers) {
		this.transientPropertyMembers = transientPropertyMembers;
	}

}
