package org.platform.file.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.file.dao.StorageDao;
import org.platform.file.entity.Storage;

@Repository("storageDao")
public final class StorageDaoImpl extends RootDaoImpl<Storage,String>implements StorageDao {

	public StorageDaoImpl() {
		super(Storage.class);
	}

}
