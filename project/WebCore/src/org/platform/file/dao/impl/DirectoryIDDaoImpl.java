package org.platform.file.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.file.dao.DirectoryIDDao;
import org.platform.file.entity.DirectoryID;

@Repository("directoryIDDao")
public final class DirectoryIDDaoImpl extends RootDaoImpl<DirectoryID,String>implements DirectoryIDDao {

	public DirectoryIDDaoImpl() {
		super(DirectoryID.class);
	}

}
