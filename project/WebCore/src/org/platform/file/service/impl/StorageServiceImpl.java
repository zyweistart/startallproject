package org.platform.file.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.file.dao.StorageDao;
import org.platform.file.entity.Storage;
import org.platform.file.service.StorageService;

@Service("storageService")
public final class StorageServiceImpl extends RootServiceImpl<Storage,String> 
implements StorageService {

	@SuppressWarnings("unused")
	private StorageDao storageDao;
	
	public StorageServiceImpl(@Qualifier("storageDao")StorageDao storageDao) {
		super(storageDao);
		this.storageDao=storageDao;
	}

}
