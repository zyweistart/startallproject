package org.framework.listener.tasks;

import java.util.TimerTask;

import org.framework.config.ConfigParameter;
import org.framework.config.SystemPath;
import org.framework.listener.CacheContext;
import org.framework.utils.FileUtils;
import org.framework.utils.LogUtils;
import org.framework.utils.TimeUtils;
import org.framework.utils.ZIPUtils;

/**
 * 程序备份
 * @author Start
 */
public class ProgramBackupTask extends TimerTask {

	private final static String FILENAME = "WebCoreProgram";
	private final static String COMMENT = ConfigParameter.SYSTEMINFO + "程序备份";
	private final static int maxtrytimes = 10;

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
			int trytimes = 1;
			while(true) {
				String date = TimeUtils.getSysdateInt();
				//备份文件的完整路径
				String backupPath = SystemPath.PROGRAMBACKUP_PATH + FILENAME + date + ZIPUtils.ZIP;
				//备注信息
				String comment = COMMENT + date;
				//删除备份文件
				if(FileUtils.deleteFile(backupPath)){
					if(ZIPUtils.zip(CacheContext.SERVLETCONTEXTREALPATH, backupPath, comment)) {
						LogUtils.log(flag, comment +"-->Success");
						CacheContext.inactiveUser();
						flag = false;
						return;
					} else {
						LogUtils.log(flag, comment +"-->Fail");
						if(trytimes >= maxtrytimes) {
							//TODO:发送邮件请示管理员
							CacheContext.inactiveUser();
							flag = false;
							return;
						} else {
							trytimes ++;
							TimeUtils.sleep(10000);
						}
					}
				}
			}
		}
		
	}

}
