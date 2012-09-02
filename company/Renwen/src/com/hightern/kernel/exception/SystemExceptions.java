/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.exception;

public class SystemExceptions extends RuntimeException {
	
	private static final long serialVersionUID = -6271941159118647957L;
	
	public SystemExceptions() {
		super();
	}
	
	public SystemExceptions(String message) {
		super(message);
	}
	
	public SystemExceptions(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SystemExceptions(Throwable cause) {
		super(cause);
	}
}
