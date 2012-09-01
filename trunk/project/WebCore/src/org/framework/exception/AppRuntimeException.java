package org.framework.exception;


/**
 * 应用运行时异常信息由该异常引发的内容会回传给调用端
 * @author Start
 */
public class AppRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public AppRuntimeException(Throwable e){
		super(e);
	}
	
	public AppRuntimeException(int info){
		super(String.valueOf(info));
	}
}
