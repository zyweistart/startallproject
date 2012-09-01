package org.zyweistartframework.persistence.annotation;

import org.zyweistartframework.persistence.annotation.dt.CascadeType;
import org.zyweistartframework.persistence.annotation.dt.FetchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 多对多
 * 级联操作时如果需要级联添加的话则必须有主键值
 * @author Start
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToMany {
	/**
	 * 设置关系维护端字段名
	 */
	String mappedBy() default "";
	
	CascadeType[] cascade() default {CascadeType.REFRESH};
	
	FetchType fetch() default FetchType.LAZY;
	/**
	 * 所要映射的类
	 */
	Class<?> tarEntityClass() default Object.class;
}
