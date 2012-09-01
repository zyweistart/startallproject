package org.platform.file.service;

import org.framework.service.RootService;
import org.platform.file.entity.UploadApply;

public interface UploadApplyService extends RootService<UploadApply,String> {
	/**
	 * 加载当前上传申请信息
	 */
	UploadApply loadPassKeyByUploadApply(String passKey);
	/**
	 * 把申请记录更新为已使用
	 */
	void applyUse(UploadApply entity);
}
