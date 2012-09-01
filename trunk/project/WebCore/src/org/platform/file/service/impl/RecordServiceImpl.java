package org.platform.file.service.impl;

import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.framework.service.impl.RootServiceImpl;

import org.platform.file.dao.RecordDao;
import org.platform.file.entity.Record;
import org.platform.file.service.RecordService;

@Service("recordService")
public final class RecordServiceImpl extends RootServiceImpl<Record,String> 
implements RecordService {

	@SuppressWarnings("unused")
	private RecordDao recordDao;
	
	public RecordServiceImpl(@Qualifier("recordDao")RecordDao recordDao) {
		super(recordDao);
		this.recordDao=recordDao;
	}

}
