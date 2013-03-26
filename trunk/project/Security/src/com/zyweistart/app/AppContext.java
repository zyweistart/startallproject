package com.zyweistart.app;

import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Vibrator;

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

}