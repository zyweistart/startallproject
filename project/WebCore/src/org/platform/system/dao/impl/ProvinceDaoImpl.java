package org.platform.system.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.system.dao.ProvinceDao;
import org.platform.system.entity.Province;

@Repository("provinceDao")
public final class ProvinceDaoImpl extends RootDaoImpl<Province,String>implements ProvinceDao {

	public ProvinceDaoImpl() {
		super(Province.class);
	}

}
