/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.exception;

public class ServiceExceptions extends RuntimeException {
	
	private static final long serialVersionUID = -6271941159118647957L;
	
	public ServiceExceptions() {
		super();
	}
	
	public ServiceExceptions(String message) {
		super(message);
	}
	
	public ServiceExceptions(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceExceptions(Throwable cause) {
		super(cause);
	}
}
