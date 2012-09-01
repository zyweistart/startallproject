package org.platform.system.entity;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Table;
/**
 * 城市
 * @author Start
 */
@Entity("city")
@Table("SYS_CITY")
public class City extends Root {

	private static final long serialVersionUID = 1L;
	
	public City(){}
	
	private String code;
	
	private String name;
	
	private String provinceCode;

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

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
}
