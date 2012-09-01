package org.platform.system.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.system.dao.CityDao;
import org.platform.system.entity.City;

@Repository("cityDao")
public final class CityDaoImpl extends RootDaoImpl<City,String>implements CityDao {

	public CityDaoImpl() {
		super(City.class);
	}

}
