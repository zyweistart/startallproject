package com.start.bean;

import java.io.Serializable;

/**
 * 实体基类：实现序列化
 * @author start
 *
 */
public abstract class Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}