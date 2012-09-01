package org.zyweistartframework.exception;

/**
 * 持久化异常
 * @author Start
 */
public class HibernateException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public HibernateException(Throwable e) {
		super(e.getCause()!=null?e.getCause():e);
	}
	
	public HibernateException(String message) {
		super(message);
	}
}