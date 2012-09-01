package org.platform.file.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.file.dao.RecordDao;
import org.platform.file.entity.Record;

@Repository("recordDao")
public final class RecordDaoImpl extends RootDaoImpl<Record,String>implements RecordDao {

	public RecordDaoImpl() {
		super(Record.class);
	}

}
