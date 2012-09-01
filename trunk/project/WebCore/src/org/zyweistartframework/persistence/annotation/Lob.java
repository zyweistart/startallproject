package org.zyweistartframework.persistence.annotation;

import org.zyweistartframework.persistence.annotation.dt.FetchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 表示为长文本
 * @author Start
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lob {
	
	/**
	 * 字段名
	 */
	String name() default "";
	/**
	 * 加载模式
	 */
	FetchType fetch() default FetchType.LAZY;
	/**
	 * 是否为空
	 */
	boolean nullable() default true;
	
}