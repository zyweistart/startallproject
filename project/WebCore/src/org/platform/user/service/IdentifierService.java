package org.platform.user.service;

import org.framework.service.RootService;
import org.platform.user.entity.Identifier;

public interface IdentifierService extends RootService<Identifier,String> {
	/**
	 * 加载识别码信息
	 */
	Identifier loadIdentifierByPassKey(String passkey);
	/**
	 * 重新生成激活码
	 */
	void regenerate(Identifier identifier);
	/**
	 * 激活帐户
	 */
	void activation(Identifier identifier);
	
}
