package org.platform.wwwroot.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.wwwroot.dao.InformationDao;
import org.platform.wwwroot.entity.Information;
import org.platform.wwwroot.service.InformationService;

@Service("informationService")
public final class InformationServiceImpl extends RootServiceImpl<Information,String> 
implements InformationService {

	@SuppressWarnings("unused")
	private InformationDao informationDao;
	
	public InformationServiceImpl(@Qualifier("informationDao")InformationDao informationDao) {
		super(informationDao);
		this.informationDao=informationDao;
	}

}
