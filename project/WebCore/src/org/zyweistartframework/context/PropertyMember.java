package org.zyweistartframework.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.zyweistartframework.config.Message;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.exception.EntityDefinitionError;
import org.zyweistartframework.persistence.annotation.Column;


public final class PropertyMember{
	/**
	 * 当前字段所包含的Annotation
	 */
	private Annotation currentFieldAnnotation;
	/**
	 * 当前字段所包含的Annotation
	 */
	private Annotation[] currentFieldAnnotations;
	/**
	 * 字段
	 */
	private Field field;
	/**
	 * 当前的成员方法
	 */
	private Method GMethod;
	/**
	 * 标记用于多对多关系中标有JoinTable时为true否则为false
	 */
	private Boolean flag=false;
	/**
	 * 当前成员方法的Set方法
	 */
	private Method SMethod;
	/**
	 * 关系映射时的目标方的类
	 */
	private Class<?> tarClass;
	/**
	 * 关系映射目标方上的属性字段
	 */
	private Annotation mappedByAnnotation;
	/**
	 * 关系映射时的目标方的方法
	 */
	private Method mappedByMethod;
	/**
	 * 所对应的Set方法
	 */
	private Method mappedBySetMethod;
	/**
	 * 对应的字段成员
	 */
	private String fieldName;
	/**
	 * 所对应的外键表-多对多时使用
	 */
	private String joinTableName;
	/**
	 * 外键对应的字段-多对多时使用
	 */
	private String foreignKeysFieldName;
	/**
	 * 返回类型
	 */
	private String returnTypeSimpleName;
	/**
	 * 延迟加载标记
	 */
	private boolean lazyflag=false;
	/**
	 * 当前字段是否为日期型
	 */
	private boolean dateflag=false;
	/**
	 * 当前字段是否为枚举
	 */
	private boolean enumflag=false;

	public Annotation getAnnotation(Annotation annotation){
		for(Annotation an:getCurrentFieldAnnotations()){
			if(an.equals(annotation)){
				return an;
			}
		}
		throw new NullPointerException(Message.getMessage(Message.PM_1005,
				getField().getName(),annotation));
	}
	
	public Annotation getCurrentFieldAnnotation() {
		return currentFieldAnnotation;
	}

	public void setCurrentFieldAnnotation(Annotation currentFieldAnnotation) {
		this.currentFieldAnnotation = currentFieldAnnotation;
	}
	
	public Annotation[] getCurrentFieldAnnotations() {
		return currentFieldAnnotations;
	}

	public void setCurrentFieldAnnotations(Annotation[] currentFieldAnnotations) {
		this.currentFieldAnnotations = currentFieldAnnotations;
	}

	public Field getField() {
		return field;
	}
	
	public void setField(Field field) {
		this.field = field;
	}

	public Method getGMethod() {
		return GMethod;
	}

	public void setGMethod(Method gMethod) {
		GMethod = gMethod;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Method getSMethod() {
		return SMethod;
	}

	public void setSMethod(Method sMethod) {
		SMethod = sMethod;
	}

	public Class<?> getTarClass() {
		return tarClass;
	}

	public void setTarClass(Class<?> tarClass) {
		this.tarClass = tarClass;
	}

	public Annotation getMappedByAnnotation() {
		return mappedByAnnotation;
	}

	public void setMappedByAnnotation(Annotation mappedByAnnotation) {
		this.mappedByAnnotation = mappedByAnnotation;
	}

	public Method getMappedByMethod() {
		return mappedByMethod;
	}

	public void setMappedByMethod(Method mappedByMethod) {
		this.mappedByMethod = mappedByMethod;
	}

	public Method getMappedBySetMethod() {
		return mappedBySetMethod;
	}

	public void setMappedBySetMethod(Method mappedBySetMethod) {
		this.mappedBySetMethod = mappedBySetMethod;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		if(fieldName.length()>Constants.MAXLENGTH){
			throw new EntityDefinitionError(Message.getMessage(Message.PM_1004,
					Constants.FIELDNAME,fieldName,Constants.MAXLENGTH));
		}
		this.fieldName = fieldName.toUpperCase();
	}

	public String getJoinTableName() {
		return joinTableName;
	}

	public void setJoinTableName(String joinTableName) {
		if(joinTableName.length()>Constants.MAXLENGTH){
			throw new EntityDefinitionError(Message.getMessage(Message.PM_1004,
					Constants.JOINTABLENAME,joinTableName,Constants.MAXLENGTH));
		}
		this.joinTableName = joinTableName;
	}

	public String getForeignKeysFieldName() {
		return foreignKeysFieldName;
	}

	public void setForeignKeysFieldName(String foreignKeysFieldName) {
		if(foreignKeysFieldName.length()>Constants.MAXLENGTH){
			throw new EntityDefinitionError(Message.getMessage(Message.PM_1004,
					Constants.FOREIGNKEYSFIELDNAME,foreignKeysFieldName,Constants.MAXLENGTH));
		}
		this.foreignKeysFieldName = foreignKeysFieldName.toUpperCase();
	}

	public String getReturnTypeSimpleName() {
		return returnTypeSimpleName;
	}

	public void setReturnTypeSimpleName(String returnTypeSimpleName) {
		this.returnTypeSimpleName = returnTypeSimpleName;
	}
	/**
	 * 获取当前字段的默认值
	 */
	public String getDefaultValue(){
		if(getCurrentFieldAnnotation()!=null){
			if(getCurrentFieldAnnotation().annotationType().equals(Column.class)){
				return ((Column)getCurrentFieldAnnotation()).initVal();
			}
		}
		return null;
	}
	/**
	 * 判断当前成员是否为日期型
	 */
	public boolean isDateflag() {
		return dateflag;
	}

	public void setDateflag(boolean dateflag) {
		this.dateflag = dateflag;
	}

	public boolean isLazyflag() {
		return lazyflag;
	}

	public void setLazyflag(boolean lazyflag) {
		this.lazyflag = lazyflag;
	}

	public boolean isEnumflag() {
		return enumflag;
	}

	public void setEnumflag(boolean enumflag) {
		this.enumflag = enumflag;
	}
	
}
