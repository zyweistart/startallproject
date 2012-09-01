package org.framework.listener.tasks;

import java.io.IOException;
import java.util.TimerTask;

import org.framework.config.SystemPath;
import org.framework.listener.CacheContext;
import org.framework.utils.FileUtils;
import org.framework.utils.LogUtils;
import org.framework.utils.TimeUtils;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.utils.StackTraceInfo;

/**
 * 数据库备份
 * @author Start
 */
public class DataBaseBackupTask extends TimerTask {

	private final static String FILENAME = "OracleBackUp";

	private static boolean flag = false;

	@Override
	public void run() {
		if(flag) {
			return;
		}
		flag = true;
		CacheContext.activeUser();
		LogUtils.logInfo(this.getClass().getSimpleName());
		new ProgramBackupThread().start();
	}

	private class ProgramBackupThread extends Thread {

		public void run() {
			String path=SystemPath.DATABASEBACKUP_PATH+TimeUtils.getSysyearmonthInt()+Constants.FILESEPARATOR;
			if(FileUtils.mkdirs(path)){
				//备份文件的完整路径
				String backupPath = path + FILENAME + TimeUtils.getSysdateInt() + ".dmp";
				try {
					//格式：exp 用户名/密码@数据库实例名 file=文件存放的完整路径 full=y(备份整个数据库实例)
					Runtime.getRuntime().exec("exp start/orcl@ORCL file="+backupPath);
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo()+e.getMessage());
				}
			}
			CacheContext.inactiveUser();
			flag=false;
		}
	}

}
