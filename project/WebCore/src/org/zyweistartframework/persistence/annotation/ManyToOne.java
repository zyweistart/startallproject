package org.zyweistartframework.persistence.annotation;

import org.zyweistartframework.persistence.annotation.dt.CascadeType;
import org.zyweistartframework.persistence.annotation.dt.FetchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 多对一
 * @author Start
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {

	CascadeType[] cascade() default {CascadeType.REFRESH};
	
	FetchType fetch() default FetchType.LAZY;
	/**
	 * 设置关系维护端字段
	 * 默认为实体名加s
	 */
	String mappedBy() default "";
	/**
	 * 可选
	 * true  表示可以为null
	 * false 表示不可以为null,对象必须存在,建立约束条件
	 */
	boolean optional() default false;
	
}
