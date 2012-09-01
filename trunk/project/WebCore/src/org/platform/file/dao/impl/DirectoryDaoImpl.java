package org.platform.file.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.file.dao.DirectoryDao;
import org.platform.file.entity.Directory;

@Repository("directoryDao")
public final class DirectoryDaoImpl extends RootDaoImpl<Directory,String>implements DirectoryDao {

	public DirectoryDaoImpl() {
		super(Directory.class);
	}

}
