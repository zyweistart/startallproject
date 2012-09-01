package org.zyweistartframework.persistence.annotation.dt;

/**
 * 级联操作
 */
public enum CascadeType {
	/**
	 * 使用级联操作时，如果启用了PERSIST,MERGE则启用的另一方必须为空，否则会报异常
	 */
	All,
	REFRESH,
	PERSIST,
	MERGE,
	REMOVE
}