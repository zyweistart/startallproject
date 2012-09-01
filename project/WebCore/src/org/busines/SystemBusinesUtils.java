package org.busines;

import org.framework.exception.AppRuntimeException;
import org.framework.utils.StringCheck;
import org.framework.utils.StringUtils;
import org.message.ISystem;

public class SystemBusinesUtils {
	/**
	 * 检测编号
	 */
	public static void checkAccessID(String accessId){
		if(StringUtils.isEmpty(accessId)){
			//通行证编号不能为空
			throw new AppRuntimeException(ISystem._10101004);
		}else if(!StringCheck.checkMd5(accessId)){
			//通行证编号格式不正确
			throw new AppRuntimeException(ISystem._10101005);
		}
	}
}