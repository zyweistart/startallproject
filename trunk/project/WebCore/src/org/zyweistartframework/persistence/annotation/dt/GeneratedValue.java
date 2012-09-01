package org.zyweistartframework.persistence.annotation.dt;

/**
 * 主键的生成策略
 * IDENTITY
 * 主键自动增长策略,目前只支持MSSQLServer,MySQL,Oracle不支持该模式
 * UID
 * 统一的主键生成策略
 * NONE
 * 需要自己添加主键值
 */
public enum GeneratedValue {
	IDENTITY,
	UID,
	NONE
}
