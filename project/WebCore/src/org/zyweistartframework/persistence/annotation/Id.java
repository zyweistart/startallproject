package org.zyweistartframework.persistence.annotation;

import org.zyweistartframework.persistence.annotation.dt.GeneratedValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 设置为主键,
 * 注意：一个表有且只能有一个主键,其它可以用唯一约束来表示
 * @author Start
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
	
	GeneratedValue value() default GeneratedValue.UID; 
	
	int length() default 32;
	/**
	 * 字段名
	 */
	String name() default "";
}
