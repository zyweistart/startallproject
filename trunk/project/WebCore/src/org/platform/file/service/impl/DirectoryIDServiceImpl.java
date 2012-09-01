package org.platform.file.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.file.dao.DirectoryIDDao;
import org.platform.file.entity.DirectoryID;
import org.platform.file.service.DirectoryIDService;

@Service("directoryIDService")
public final class DirectoryIDServiceImpl extends RootServiceImpl<DirectoryID,String> 
implements DirectoryIDService {

	@SuppressWarnings("unused")
	private DirectoryIDDao directoryIDDao;
	
	public DirectoryIDServiceImpl(@Qualifier("directoryIDDao")DirectoryIDDao directoryIDDao) {
		super(directoryIDDao);
		this.directoryIDDao=directoryIDDao;
	}

}
