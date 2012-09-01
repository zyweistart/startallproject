package org.platform.system.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.system.dao.ProvinceDao;
import org.platform.system.entity.Province;
import org.platform.system.service.ProvinceService;

@Service("provinceService")
public final class ProvinceServiceImpl extends RootServiceImpl<Province,String> 
implements ProvinceService {

	@SuppressWarnings("unused")
	private ProvinceDao provinceDao;
	
	public ProvinceServiceImpl(@Qualifier("provinceDao")ProvinceDao provinceDao) {
		super(provinceDao);
		this.provinceDao=provinceDao;
	}

}
