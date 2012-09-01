package com.zyweistart.app;

import java.util.Timer;
import java.util.TimerTask;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
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
import android.os.Vibrator;
import android.util.Log;

/**
 * 传感器服务,重力与距离感器
 * @author Start
 */
public final class SecuritySensorService extends Service {

	private final static String TAG = "SecuritySensorService";

	private KeyguardManager mKeyguardManager;
	private SensorManager mSensorManager;
	private AudioManager mAudioManager;
	private SensorServiceEventListener mSensorEventListener;
	private ScreenLockUnlockMonitor mScreenLockUnlockMonitor;
	private SecurityMonitoring mSecurityMonitoring;
	/**
	 * 定时器
	 */
	private Timer timer;
	/**
	 * 手机震动
	 */
	private Vibrator mVibrator;
	/**
	 * 重力传感器
	 */
	private Sensor mAccelerometerSensor=null;
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
	/**
	 * 该值越小表示敏感越大
	 */
	private final static int SHAKE_THRESHOLD=2500;
	private long mLastTimeMillis;
	private int mStreamMaxVolume=0;
	private float x,y,z,lastX,lastY,lastZ;
	
	@Override
	public void onCreate() {
		// 获取键盘管理器
		mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		// 获取感应器管理器
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAudioManager= (AudioManager)getSystemService(Context.AUDIO_SERVICE );
		mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
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
											if(mAlarmMP.isPlaying()){
												mAlarmMP.stop();
											}
											mAlarmMP.release();
											mAlarmMP=null;
										}else{
											//提示一下表示已解锁
											mVibrator.vibrate(pattern, -1);
										}
										isMonitoring=false;
									}
									unLock();
								}
								break;
							}
						}
					});
		}
		timer=new Timer(true);
		mScreenLockUnlockMonitor.openMonitor();
		mSensorEventListener = new SensorServiceEventListener();
		mStreamMaxVolume=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		unLock();
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		mScreenLockUnlockMonitor.closeMonitor();
		mSensorManager.unregisterListener(mSensorEventListener);
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
			//重力传感器晃动手机让其进入监控状态
			case Sensor.TYPE_ACCELEROMETER:
				//键盘必须为锁定状态
				if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
					long currentTimeMillis=System.currentTimeMillis();
					//每100毫秒检测一次
					x = event.values[SensorManager.DATA_X];
					y = event.values[SensorManager.DATA_Y];
					z = event.values[SensorManager.DATA_Z];
					if((currentTimeMillis-mLastTimeMillis)>100){
						x = event.values[SensorManager.DATA_X];
						y = event.values[SensorManager.DATA_Y];
						z = event.values[SensorManager.DATA_Z];
						long intervalTime=currentTimeMillis-mLastTimeMillis;
						float speed=Math.abs(x+y+z-lastX-lastY-lastZ)/intervalTime*10000;
						//重力传感器已满足条件
						if(speed>SHAKE_THRESHOLD){
							Log.i(TAG,"监控设备启动完毕！");
							if(mProximitySensor==null){
								//注册距离传感器
								mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
								mSensorManager.registerListener(mSensorEventListener, mProximitySensor,SensorManager.SENSOR_DELAY_FASTEST);
							}
							//解除重力传感器
							if(mAccelerometerSensor!=null){
								mSensorManager.unregisterListener(mSensorEventListener,mAccelerometerSensor);
								mAccelerometerSensor=null;
							}
							//震动提示一下表示进入监控状态
						    mVibrator.vibrate(pattern, -1);
						}
						lastX=x;
						lastY=y;
						lastZ=z;
						mLastTimeMillis=currentTimeMillis;
					}
				}
				break;
			//距离传感器
			case Sensor.TYPE_PROXIMITY:
				if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
					//防止重复监控
					if(!isMonitoring){
						x = event.values[SensorManager.DATA_X];
						//大于零则表示手机肯定不是在口袋里
						if (x > 0) {
							//如果距离传感器注册监听10秒后值发生变化则启动安全监控线程
							if((System.currentTimeMillis()-mLastTimeMillis)>10000){
								Log.w(TAG, "安全警报系统已启动，将在5秒后报警,请注意!");
								isMonitoring=true;
								// 新建一个任务
								mSecurityMonitoring=new SecurityMonitoring();
								timer.schedule(mSecurityMonitoring, 5000);
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
			mSensorManager.unregisterListener(mSensorEventListener,mProximitySensor);
			mProximitySensor=null;
		}
		// 注册重力传感器
		if(mAccelerometerSensor==null){
			mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			mSensorManager.registerListener(mSensorEventListener, mAccelerometerSensor,SensorManager.SENSOR_DELAY_FASTEST);
		}
		mLastTimeMillis=0l;
	}
	
	/**
	 * 安全监控线程
	 */
	private final class SecurityMonitoring extends TimerTask {
		@Override
		public void run() {
			try {
				//如果运行到该处还没有解锁，那么你的爱机可能已经被盗，注意!!!
				if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
					Log.e(TAG, "抓小偷，手机被盗了！");
					//提示报警音等动作
					mAlarmMP=MediaPlayer.create(SecuritySensorService.this, R.raw.alarm);
					mAlarmMP.setVolume(mStreamMaxVolume, mStreamMaxVolume);
					mAlarmMP.setLooping(true);
					if (mAlarmMP != null) { 
						mAlarmMP.stop();
				    }
					//播放警报声音
					mAlarmMP.prepare();
					mAlarmMP.start();
					mAlarmMP.setOnCompletionListener(new OnCompletionListener() {
						//由于设置为了循环播放则永远不可能触发该结束播放的事件
						@Override
						public void onCompletion(MediaPlayer mp) {
							mAlarmMP.release();
							mAlarmMP=null;
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(mSecurityMonitoring!=null){
					//将原任务从队列中移除
					mSecurityMonitoring.cancel();
					mSecurityMonitoring=null;
				}
			}
		}
	}
	
}