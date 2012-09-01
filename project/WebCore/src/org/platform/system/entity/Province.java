package org.platform.system.entity;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Table;
/**
 * 省份
 * @author Start
 */
@Entity("province")
@Table("SYS_PROVINCE")
public class Province extends Root {

	private static final long serialVersionUID = 1L;
	
	public Province(){}
	
	private String code;
	
	private String name;
	
	private String countryCode;

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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
}
