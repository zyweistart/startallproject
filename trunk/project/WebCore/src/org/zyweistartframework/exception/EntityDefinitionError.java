package org.zyweistartframework.exception;

/**
 * 实体类定义错误如方法无法访问，方法参数有误,系统严重错误
 * <pre>
 *1.Security
 *2.NoSuchField
 * </pre>
 * @author Start
 */
public class EntityDefinitionError extends Error {
	
	private static final long serialVersionUID = 1L;

	public EntityDefinitionError(Throwable e){
		super(e);
	}
	
	public EntityDefinitionError(String message) {
		super(message);
	}
}