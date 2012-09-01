package org.zyweistartframework.persistence.annotation;

import org.zyweistartframework.persistence.annotation.dt.FetchType;
import org.zyweistartframework.persistence.annotation.dt.TemporalType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 日期类型注解于字符类型的字段上
 * @author Start
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Temporal {
	/**
	 * 字段名
	 */
	String name() default "";
	
	TemporalType value() default TemporalType.TIMESTAMP;
	/**
	 * 是否为空
	 */
	boolean nullable() default true;
	/**
	 * 加载模式
	 */
	FetchType fetch() default FetchType.EAGER;
	/**
	 * 日期时间格式
	 * Oracle:
	 * YYYY-MM-DD HH24:MI:SS
	 * MySQL:
	 * %Y-%m-%d %H:%i:%s
	 * MSSQLSERVER:
	 * yyyy-mm-dd hh:mm:ss
	 */
	String format() default "";
}
