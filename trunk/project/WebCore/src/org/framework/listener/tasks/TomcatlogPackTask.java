package org.framework.listener.tasks;

import java.io.File;
import java.util.TimerTask;

import org.framework.config.ConfigParameter;
import org.framework.config.SystemPath;
import org.framework.listener.CacheContext;
import org.framework.utils.FileUtils;
import org.framework.utils.LogUtils;
import org.framework.utils.TimeUtils;
import org.framework.utils.ZIPUtils;
import org.zyweistartframework.config.Constants;
/**
 * Tomcat服务器日志备份
 * @author Start
 */
public class TomcatlogPackTask extends TimerTask {

	private static boolean flag = false;

	@Override
	public void run() {
		if(flag) {
			return;
		}
		flag = true;
		CacheContext.activeUser();
		LogUtils.logInfo(this.getClass().getSimpleName());
		new TomcatlogPackThread().start();
	}
	
	private class TomcatlogPackThread extends Thread {

		public void run() {
			try{
				String yesterday = TimeUtils.getTheDayBefore();
				String regex = "[0-9A-Za-z_-]*." + TimeUtils.getTheDayBefore_F() + ".[0-9A-Za-z]*";
				File[] filelist = FileUtils.getFilesByFilterRegex(SystemPath.TOMCATLOG_PATH, regex);
				if(filelist != null) {
					for(File subfile : filelist) {
						if(subfile.isFile()) {
							if(FileUtils.renameTo(subfile, SystemPath.TOMCATLOGBACKUP_PATH+ yesterday + Constants.FILESEPARATOR + subfile.getName())) {
								String inputFilePath = SystemPath.TOMCATLOGBACKUP_PATH+ yesterday + Constants.FILESEPARATOR;
								//backup/tomcatlog/2012/04/20120425(1).zip
								String zipFileName = SystemPath.TOMCATLOGBACKUP_PATH + yesterday.substring(0, 4) + Constants.FILESEPARATOR + 
										yesterday.substring(4, 6) + Constants.FILESEPARATOR + yesterday + "(" +
										ConfigParameter.BALANCED_WORKER_INDEX + ")" +ZIPUtils.ZIP;
								if(FileUtils.isFileExists(zipFileName)) {
									if(ZIPUtils.unZip(zipFileName, SystemPath.TOMCATLOGBACKUP_PATH)) {
										FileUtils.deleteFile(zipFileName);
									}
								}
								if(ZIPUtils.zip(inputFilePath, zipFileName, ConfigParameter.SYSTEMNAME + "TOMCAT(" + ConfigParameter.BALANCED_WORKER_INDEX + ")日志备份" + yesterday)) {
									FileUtils.deleteDir(inputFilePath);
								}
							}
						}
					}
				}
			}finally{
				CacheContext.inactiveUser();
				flag = false;
			}
		}

	}

}