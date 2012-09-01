package org.platform.system.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.system.dao.CountryDao;
import org.platform.system.entity.Country;
import org.platform.system.service.CountryService;

@Service("countryService")
public final class CountryServiceImpl extends RootServiceImpl<Country,String> 
implements CountryService {

	@SuppressWarnings("unused")
	private CountryDao countryDao;
	
	public CountryServiceImpl(@Qualifier("countryDao")CountryDao countryDao) {
		super(countryDao);
		this.countryDao=countryDao;
	}

}
