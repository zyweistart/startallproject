package org.platform.file.dao.impl;

import org.zyweistartframework.context.annotation.Repository;
import org.framework.dao.impl.RootDaoImpl;
import org.platform.file.dao.UploadApplyDao;
import org.platform.file.entity.UploadApply;

@Repository("uploadApplyDao")
public final class UploadApplyDaoImpl extends RootDaoImpl<UploadApply,String>implements UploadApplyDao {

	public UploadApplyDaoImpl() {
		super(UploadApply.class);
	}

}
