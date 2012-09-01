package org.framework.listener.tasks;

import java.io.File;
import java.util.TimerTask;

import org.framework.config.SystemPath;
import org.framework.listener.CacheContext;
import org.framework.utils.LogUtils;
import org.framework.utils.TimeUtils;
import org.framework.utils.ZIPUtils;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.utils.FileUtils;

/**
 * 程序日志备份
 * @author Start
 */
public class LogPackTask extends TimerTask {

	private static boolean flag = false;

	@Override
	public void run() {
		if(flag) {
			return;
		}
		flag = true;
		CacheContext.activeUser();
		LogUtils.logInfo(this.getClass().getSimpleName());
		new LogPackThread().start();
	}

	private class LogPackThread extends Thread {
		
		public void run() {
			try{
				String yesterday = TimeUtils.getTheDayBefore();
				String inputFilePath =SystemPath.LOG_PATH + yesterday + Constants.FILESEPARATOR;
				String zipFileName =SystemPath.LOGBACKUP_PATH  + yesterday.substring(0, 4) + Constants.FILESEPARATOR + 
						yesterday.substring(4, 6) + Constants.FILESEPARATOR + yesterday +ZIPUtils.ZIP;
				if(ZIPUtils.zip(inputFilePath, zipFileName)){
					FileUtils.remove(new File(inputFilePath));
				}
			}finally{
				CacheContext.inactiveUser();
				flag = false;
			}
		}

	}

}
