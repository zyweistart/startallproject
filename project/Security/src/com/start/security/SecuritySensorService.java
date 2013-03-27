package com.start.security;

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
	 * 手机震动频率
	 */
	private static long[] pattern ={100,500,400,500};
	/**
	 * 警报的媒体音
	 */
	private MediaPlayer mAlarmMP;
	/**
	 * 距离传感器
	 */
	private Sensor mProximitySensor;
	/**
	 * 最大音量大小
	 */
	private int mMaxVolume=0;
	/**
	 * 是否为锁屏状态
	 */
	private Boolean isLock=false;
	/**
	 * 是否开始监控
	 */
	private Boolean isStartMonitor=false;
	/**
	 * 是否已经放入口袋
	 */
	private Boolean isPutFlag=false;
	
	private Timer timer1;
	private Timer timer2;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mMaxVolume=getAppContext().getAudioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mScreenLockUnlockMonitor=new ScreenLockUnlockMonitor(this);
		mScreenLockUnlockMonitor.setObserver(
		new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				case ScreenLockUnlockMonitor.ACTION_SCREEN_LOCKUNLOCK:
					String action=msg.getData().getString(ScreenLockUnlockMonitor.KEY_SCREEN_LOCKUNLOCK);
					if(Intent.ACTION_USER_PRESENT.equals(action)){//解锁状态
						if(isLock&&isStartMonitor){
							Log.i(TAG,"已解除安全警报");
							if(mAlarmMP!=null){
								//关闭警报声音
								try{
									if(mAlarmMP.isPlaying()){
										mAlarmMP.stop();
									}
									mAlarmMP.release();
								}finally{
									mAlarmMP=null;
								}
							}else{
								//提示一下表示已解锁
								getAppContext().getVibrator().vibrate(pattern, -1);
							}
						}
						isLock=false;
						isStartMonitor=false;
						unTimer();
						unSensor();
					}else if(Intent.ACTION_SCREEN_OFF.equals(action)){//锁屏状态
						if(getAppContext().isPeriodBetween()){
							if(!isLock){
								isLock=true;
								isPutFlag=false;
								Log.i(TAG,"设备监控已开启，提示用户在10秒之内迅速把手机放入口袋或包中!");
								//TODO:
								getAppContext().getVibrator().vibrate(pattern, -1);
								timer1=new Timer();
								timer1.schedule(new TimerTask() {
									@Override
									public void run() {
										unSensor();
										//注册距离传感器
										mProximitySensor = getAppContext().getSensorManager().getDefaultSensor(Sensor.TYPE_PROXIMITY);
										mSensorEventListener = new SensorServiceEventListener();
										getAppContext().getSensorManager().registerListener(mSensorEventListener, mProximitySensor,SensorManager.SENSOR_DELAY_FASTEST);
									}
								}, 10000);
							}
						}else{
							Log.i(TAG,"当前时间不提供保护!");
						}
					}
					break;
				}
			}
		});
		mScreenLockUnlockMonitor.openMonitor();
	}

	@Override
	public void onDestroy() {
		mScreenLockUnlockMonitor.closeMonitor();
		unTimer();
		unSensor();
		super.onDestroy();
	}
	
	public AppContext getAppContext(){
		return (AppContext)getApplication();
	}
	
	public void unTimer(){
		if(timer1!=null){
			try{
				timer1.cancel();
			}finally{
				timer1=null;
			}
		}
		if(timer2!=null){
			try{
				timer2.cancel();
			}finally{
				timer2=null;
			}
		}
	}
	/**
	 * 解除传感器和距离监听器
	 */
	private void unSensor(){
		if(mSensorEventListener!=null&&mProximitySensor!=null){
			try{
				//解除距离传感器
				getAppContext().getSensorManager().unregisterListener(mSensorEventListener,mProximitySensor);
				//解除传感监听器
				getAppContext().getSensorManager().unregisterListener(mSensorEventListener);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				mProximitySensor=null;
				mSensorEventListener=null;
			}
		}
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
			case Sensor.TYPE_PROXIMITY:
				//判断是否为锁屏状态
				if (getAppContext().getKeyguardManager().inKeyguardRestrictedInputMode()) {
					//防止重复监控
					if(!isStartMonitor){
						float x = event.values[SensorManager.DATA_X];
						if(x<=1){
							//TODO:
							getAppContext().getVibrator().vibrate(pattern, -1);
							Log.v(TAG,"手机已经放入口袋或包中，提示用户如果为自己从口袋中拿出手机要记得立即解锁防止报警，距离："+x);
							isPutFlag=true;
						}else{
							//如果距离突然增大则表示手机肯定从口袋或包中拿出
							if(isPutFlag){
								//TODO:
								Log.w(TAG, "手机从口袋或包中拿出来了，距离："+x);
								isStartMonitor=true;
								timer2=new Timer();
								timer2.schedule(new TimerTask() {
									@Override
									public void run() {
										//如果运行到该处还没有解锁，那么你的爱机可能已经被盗，注意小偷!!!
										if (getAppContext().getKeyguardManager().inKeyguardRestrictedInputMode()) {
											//TODO:
											Log.e(TAG, "报警,抓小偷...");
											try {
												mAlarmMP=MediaPlayer.create(SecuritySensorService.this, R.raw.alarm);
												mAlarmMP.setVolume(mMaxVolume, mMaxVolume);
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
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
								}, 8000);
							}
						}
					}
				}
				break;
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
		
	}
	
}