package org.zyweistartframework.exception;


/**
 * 生成数据库表出错
 * @author Start
 */
public class GenerateTableError extends Error {

	private static final long serialVersionUID = 1L;

	public GenerateTableError(Throwable e) {
		super(e);
	}
	
	public GenerateTableError(String message) {
		super(message);
	}
	
}