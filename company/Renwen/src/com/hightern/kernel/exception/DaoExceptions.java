package com.hightern.kernel.exception;

/**
 * 持久层异常
 * 
 * @author Stone
 */
public class DaoExceptions extends RuntimeException {

	private static final long serialVersionUID = 8517332958702487962L;

	public DaoExceptions() {
		super();
	}

	public DaoExceptions(String message) {
		super(message);
	}

	public DaoExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoExceptions(Throwable cause) {
		super(cause);
	}
}
