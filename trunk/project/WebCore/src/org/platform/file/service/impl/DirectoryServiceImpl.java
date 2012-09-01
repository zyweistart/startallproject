package org.platform.file.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.file.dao.DirectoryDao;
import org.platform.file.entity.Directory;
import org.platform.file.service.DirectoryService;

@Service("directoryService")
public final class DirectoryServiceImpl extends RootServiceImpl<Directory,String> 
implements DirectoryService {

	@SuppressWarnings("unused")
	private DirectoryDao directoryDao;
	
	public DirectoryServiceImpl(@Qualifier("directoryDao")DirectoryDao directoryDao) {
		super(directoryDao);
		this.directoryDao=directoryDao;
	}

}
