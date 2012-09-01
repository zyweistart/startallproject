package org.zyweistartframework.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.zyweistartframework.persistence.annotation.dt.FetchType;

/**
 * 数据列定义
 * @author Start
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 字段名
	 */
	String name() default "";
	/**
	 * 长度
	 */
	int length() default 255;
	/**
	 * 是否唯一
	 */
	boolean unique() default false;
	/**
	 * 是否为空
	 */
	boolean nullable() default true;
	/**
	 * 加载模式
	 */
	FetchType fetch() default FetchType.EAGER;
	/**
	 * 自定义列
	 */
	String columnDefinition() default "";
	/**
	 * 默认值，一般用于什么数据都没有默认存放的值 
	 */
	String initVal() default "";
	
}
