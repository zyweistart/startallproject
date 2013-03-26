package com.zyweistart.app;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * 传感器服务,重力与距离感器
 * @author Start
 */
public final class SecuritySensorService extends Service {

	private final static String TAG = "SecuritySensorService";

	private SensorServiceEventListener mSensorEventListener;
	private ScreenLockUnlockMonitor mScreenLockUnlockMonitor;
	/**
	 * 定时器
	 */
	private Timer timer;
	/**
	 * 距离传感器
	 */
	private Sensor mProximitySensor=null;
	/**
	 * 手机震动频率
	 */
	private long[] pattern ={100,500,400,500};
	/**
	 * 警报的媒体音
	 */
	private MediaPlayer mAlarmMP=null;
	/**
	 * 是否处于安全监控中
	 */
	private boolean isMonitoring=false;
	private int mStreamMaxVolume=0;
	private float x;
	
	private Boolean firstFlag=false;
	
	public AppContext getAppContext(){
		return (AppContext)getApplication();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		if(mScreenLockUnlockMonitor==null){
			mScreenLockUnlockMonitor=new ScreenLockUnlockMonitor(this);
			mScreenLockUnlockMonitor.setObserver(
					new Handler(){
						@Override
						public void handleMessage(Message msg) {
							switch(msg.what){
							case ScreenLockUnlockMonitor.ACTION_SCREEN_LOCKUNLOCK:
								String action=msg.getData().getString(ScreenLockUnlockMonitor.KEY_SCREEN_LOCKUNLOCK);
								//解锁事件触发
								if(Intent.ACTION_USER_PRESENT.equals(action)){
									if(isMonitoring){
										Log.i(TAG,"已解除安全警报");
										if(mAlarmMP!=null){
											try{
												if(mAlarmMP.isPlaying()){
													mAlarmMP.stop();
												}
											}finally{
												mAlarmMP.release();
												mAlarmMP=null;
											}
										}else{
											//提示一下表示已解锁
											getAppContext().getVibrator().vibrate(pattern, -1);
										}
										isMonitoring=false;
									}
									unLock();
								}else if(Intent.ACTION_SCREEN_OFF.equals(action)){
									Log.i(TAG,"设备监控已开启，提示用户在10秒之内迅速把手机放入口袋或包中 ！");
									//TODO:设备监控已开启，提示用户在10秒之内迅速把手机放入口袋或包中 
									getAppContext().getVibrator().vibrate(pattern, -1);
									//数秒后传感器变更
									timer.schedule(new TimerTask() {
										@Override
										public void run() {
											if(mProximitySensor==null){
												//注册距离传感器
												mProximitySensor = getAppContext().getSensorManager().getDefaultSensor(Sensor.TYPE_PROXIMITY);
												getAppContext().getSensorManager().registerListener(mSensorEventListener, mProximitySensor,SensorManager.SENSOR_DELAY_FASTEST);
											}
											firstFlag=false;
											Log.i(TAG,"表示已经进入监控状态，提示用户如果为自己从口袋中拿出手机要记得立即解锁防止报警!");
											//TODO:表示已经进入监控状态，提示用户如果为自己从口袋中拿出手机要记得立即解锁防止报警
											getAppContext().getVibrator().vibrate(pattern, -1);
										}
									}, 10000);
								}
								break;
							}
						}
					});
		}
		timer=new Timer(true);
		mScreenLockUnlockMonitor.openMonitor();
		mSensorEventListener = new SensorServiceEventListener();
		mStreamMaxVolume=getAppContext().getAudioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		unLock();
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		mScreenLockUnlockMonitor.closeMonitor();
		getAppContext().getSensorManager().unregisterListener(mSensorEventListener);
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	/**
	 * 传感器监听器
	 */
	private final class SensorServiceEventListener implements SensorEventListener {
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch(event.sensor.getType()){
			//距离传感器
			case Sensor.TYPE_PROXIMITY:
				if (getAppContext().getKeyguardManager().inKeyguardRestrictedInputMode()) {
					//防止重复监控
					if(!isMonitoring){
						x = event.values[SensorManager.DATA_X];
//						Log.v(TAG,"距离："+x);
						if(x<=1){
							//小于等于1表示手机是先在口袋中的
							firstFlag=true;
						}
						if(firstFlag){
							//如果距离突然增大则表示手机肯定从口袋或包中拿出
							if (x > 1) {
								Log.w(TAG, "安全警报系统已启动，如果为用户自己拿出手机则需要在5秒内迅速解锁，否则将报警！");
								//TODO:安全警报系统已启动，如果为用户自己拿出手机则需要在5秒内迅速解锁，否则将报警
								isMonitoring=true;
								timer.schedule(new TimerTask() {
									@Override
									public void run() {
										try {
											//如果运行到该处还没有解锁，那么你的爱机可能已经被盗，注意小偷!!!
											if (getAppContext().getKeyguardManager().inKeyguardRestrictedInputMode()) {
												Log.e(TAG, "报警,抓小偷...");
												//TODO:提示报警音等动作
												mAlarmMP=MediaPlayer.create(SecuritySensorService.this, R.raw.alarm);
												mAlarmMP.setVolume(mStreamMaxVolume, mStreamMaxVolume);
												mAlarmMP.setLooping(true);
												if (mAlarmMP != null) { 
													mAlarmMP.stop();
											    }
												mAlarmMP.prepare();
												mAlarmMP.start();
												mAlarmMP.setOnCompletionListener(new OnCompletionListener() {
													@Override
													public void onCompletion(MediaPlayer mp) {
														mAlarmMP.release();
														mAlarmMP=null;
													}
												});
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}, 5000);
							}
						}
					}
				}else{
					//如果键盘已解锁距离传感器的值还在发生变化则解除距离传感器监控
					unLock();
				}
				break;
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}
	/**
	 * 解锁屏后距离传感器失效并重新启动重力传感器监听
	 */
	private void unLock(){
		// 解除距离传感器
		if(mProximitySensor!=null){
			getAppContext().getSensorManager().unregisterListener(mSensorEventListener,mProximitySensor);
			mProximitySensor=null;
		}
	}
	
}