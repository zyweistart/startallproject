
package org.framework.listener;

import java.util.Timer;

import org.framework.config.Business;
import org.framework.config.ConfigParameter;
import org.framework.config.Variable;
import org.framework.listener.tasks.DataBaseBackupTask;
import org.framework.listener.tasks.LogPackTask;
import org.framework.listener.tasks.ProgramBackupTask;
import org.framework.listener.tasks.ServerMonitorTask;
import org.framework.listener.tasks.TomcatlogPackTask;

public class TimerTasks {

	private Timer timer = new Timer();

	public void run() {
		if(ConfigParameter.SYSTEMSTATUS){
			timer.schedule(new ServerMonitorTask(), Variable.ONESECOND, Variable.HOUR_MILLISECOND);
			if(ConfigParameter.SYSTEMFLAG){
				//只在正式环境中执行备份任务
				if(ConfigParameter.BALANCED_WORKER_INDEX.equals(Business.BACKUP_BALANCED_WORKER_INDEX)){
					timer.schedule(new DataBaseBackupTask(),Variable.TOMORROWHOURTIME,Variable.DAY_MILLISECOND);
					timer.schedule(new LogPackTask(),Variable.TOMORROWHOURTIME,Variable.DAY_MILLISECOND);
					timer.schedule(new TomcatlogPackTask(),Variable.TOMORROWHOURTIME,Variable.DAY_MILLISECOND);
					timer.schedule(new ProgramBackupTask(),Variable.TOMORROWHOURTIME,Variable.DAY_MILLISECOND);
				}
			}
		}
	}

	public void destroy() {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}

}