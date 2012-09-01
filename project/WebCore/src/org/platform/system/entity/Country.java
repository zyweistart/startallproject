package org.platform.system.entity;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Table;
/**
 * 国家
 * @author Start
 */
@Entity("country")
@Table("SYS_COUNTRY")
public class Country extends Root {

	private static final long serialVersionUID = 1L;
	
	public Country(){}
	
	private String code;
	
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
