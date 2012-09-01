package org.platform.system.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.system.dao.CountryDao;
import org.platform.system.entity.Country;

@Repository("countryDao")
public final class CountryDaoImpl extends RootDaoImpl<Country,String>implements CountryDao {

	public CountryDaoImpl() {
		super(Country.class);
	}

}
