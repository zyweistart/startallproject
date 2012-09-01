package org.zyweistartframework.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示不生成数据字段
 * @author Start
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient {
	/**
	 * 是否加载该实例
	 */
	boolean isLoad() default false;
	/**
	 * 加载实例绑定的字段
	 */
	String bindField() default "";
	/**
	 * 是否可读取，表示只用在读取，而不创建字段
	 * 因为数据表里不存在该注解的字段所以要读取必须用as 别名的方式去掉用，AS 后面加上这字段的名称
	 */
	boolean isRead() default false;
}
