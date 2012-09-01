package org.platform.system.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.system.dao.CityDao;
import org.platform.system.entity.City;
import org.platform.system.service.CityService;

@Service("cityService")
public final class CityServiceImpl extends RootServiceImpl<City,String> 
implements CityService {

	@SuppressWarnings("unused")
	private CityDao cityDao;
	
	public CityServiceImpl(@Qualifier("cityDao")CityDao cityDao) {
		super(cityDao);
		this.cityDao=cityDao;
	}

}
