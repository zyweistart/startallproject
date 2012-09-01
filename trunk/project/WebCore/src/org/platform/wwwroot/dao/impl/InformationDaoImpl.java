package org.platform.wwwroot.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.wwwroot.dao.InformationDao;
import org.platform.wwwroot.entity.Information;

@Repository("informationDao")
public final class InformationDaoImpl extends RootDaoImpl<Information,String>implements InformationDao {

	public InformationDaoImpl() {
		super(Information.class);
	}

}
