package com.start.security;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Vibrator;

import com.start.bean.Period;
import com.start.utils.TimeUtils;

public class AppContext extends Application {
	
	/**
	 * 判断某个服务是否已经开启
	 */
	public boolean isServiceStart(String className) {
		ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager.getRunningServices(30);
		for (int i = 0; i < mServiceList.size(); i++) {
			if (className.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	
	private Vibrator mVibrator;
	private AudioManager mAudioManager;
	private SensorManager mSensorManager;
	private KeyguardManager mKeyguardManager;
	private NotificationManager mNotificationManager;
	
	public NotificationManager getNotificationManager(){
		if(mNotificationManager==null){
			mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		}
		return mNotificationManager;
	}
	/**
	 * 手机震动管理器
	 */
	public Vibrator getVibrator(){
		if(mVibrator==null){
			mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		}
		return mVibrator;
	}
	/**
	 * 音频管理器
	 */
	public AudioManager getAudioManager(){
		if(mAudioManager==null){
			mAudioManager= (AudioManager)getSystemService(Context.AUDIO_SERVICE );
		}
		return mAudioManager;
	}
	/**
	 * 键盘管理器
	 */
	public KeyguardManager getKeyguardManager(){
		if(mKeyguardManager==null){
			mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		}
		return mKeyguardManager;
	}
	/**
	 * 感应器管理器
	 */
	public SensorManager getSensorManager(){
		if(mSensorManager==null){
			mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		}
		return mSensorManager;
	}

	public Boolean isPeriodBetween(){
		List<Period> periods=new ArrayList<Period>();
		
		Period p1=new Period();
		p1.setStart("00:00");
		p1.setEnd("01:11");
		p1.setStatus(true);
		p1.setRepeat("1;2;3;4;5;6;7");
		periods.add(p1);
		
		SimpleDateFormat sdf=new SimpleDateFormat(Period.format);
		try{
			for(Period p:periods){
				if(p.getStatus()){
					if(p.getRepeat().contains(TimeUtils.getWeekIndex()+"")){
						long startlong=sdf.parse(p.getStart()).getTime();
						long curlong=sdf.parse(sdf.format(new Date())).getTime();
						long endlong=sdf.parse(p.getEnd()).getTime();
						if(startlong<=curlong&&curlong<=endlong){
							return true;
						}
					}
				}
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}
	
}