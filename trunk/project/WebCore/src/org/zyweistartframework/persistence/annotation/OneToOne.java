package org.zyweistartframework.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.zyweistartframework.persistence.annotation.dt.CascadeType;
import org.zyweistartframework.persistence.annotation.dt.FetchType;



/**
 * @author Start
 * 一对一
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneToOne {

	String mappedBy() default "";
	
	CascadeType[] cascade() default {CascadeType.REFRESH};
	
	FetchType fetch() default FetchType.LAZY;
	/**
	 * 是否为必选
	 * true  表示可以为NULL
	 * false 表示必须为有值
	 */
	boolean optional() default false;
	/**
	 * 是否映射为关系字段方
	 */
	boolean mapping() default false;
}
