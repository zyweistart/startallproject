package com.zyweistart.app;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public final class SecurityActivity extends Activity {

	private final static String TAG = "SecurityActivity";
	private final static String SecuritySensorServiceName="com.zyweistart.app.SecuritySensorService";

//	private NotificationManager mNotificationManager;
	private Button btnStartService;
	private Button btnStopService;
	private Button btnExit;
	private BtnClickListener btnClickListener;
	private Intent serviceIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);
//		 //创建一个NotificationManager的引用
//		 mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//		 //定义Notification的各种属性
//		 CharSequence tickerText = "手机监控程序"; //状态栏显示的通知文本提示
//		 long when = System.currentTimeMillis(); //通知产生的时间，会在通知信息里显示
//		 //用上面的属性初始化Nofification
//		 Notification notification = new
//		 Notification(R.drawable.ic_notification,tickerText,when);
//		 //设置通知的事件消息
//		 Context context = getApplicationContext(); //上下文
//		 CharSequence contentTitle = "手机防盗安全警报系统"; //通知栏标题
//		 CharSequence contentText = "安全监控程序已经启动!"; //通知栏内容
//		 Intent notificationIntent = new Intent(this,SecurityActivity.class);
//		 //点击该通知后要跳转的Activity
//		 PendingIntent contentIntent =
//		 PendingIntent.getActivity(this,0,notificationIntent,0);
//		 notification.setLatestEventInfo(context, contentTitle, contentText,
//		 contentIntent);
//		 //把Notification传递给NotificationManager
//		 mNotificationManager.notify(0,notification);
		
		 serviceIntent = new Intent(this, SecuritySensorService.class);
		 
		 btnClickListener=new BtnClickListener();
		 //开启服务
		 btnStartService=(Button)findViewById(R.id.btnStartService);
		 btnStartService.setOnClickListener(btnClickListener);
		 //停止服务
		 btnStopService=(Button)findViewById(R.id.btnStopService);
		 btnStopService.setOnClickListener(btnClickListener);
		 //退出程序
		 btnExit=(Button)findViewById(R.id.btnExit);
		 btnExit.setOnClickListener(btnClickListener);
		 
		 TextView tv=(TextView)findViewById(R.id.txtInfo);
		 SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		 String mAcceleRometerSensor =mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)==null?"不支持":"支持";
		 String mProximitySensor =mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)==null?"不支持":"支持";
		 tv.setText("重力传感器："+mAcceleRometerSensor+"\n距离传感器："+mProximitySensor);
		Log.i(TAG, "应用程序创建了！");
	}

	@Override
	protected void onResume() {
		if(isServiceStart(SecuritySensorServiceName)){
			btnStartService.setEnabled(false);
			btnStopService.setEnabled(true);
		}else{
			btnStartService.setEnabled(true);
			btnStopService.setEnabled(false);
		}
		super.onResume();
	}

	/**
	 * 判断某个服务是否已经开启
	 */
	private boolean isServiceStart(String className) {
		ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager.getRunningServices(30);
		for (int i = 0; i < mServiceList.size(); i++) {
			if (className.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	private final class BtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStartService:
				startService(serviceIntent);
				btnStartService.setEnabled(false);
				btnStopService.setEnabled(true);
				break;
			case R.id.btnStopService:
				stopService(serviceIntent);
				btnStartService.setEnabled(true);
				btnStopService.setEnabled(false);
				break;
			case R.id.btnExit:
				stopService(serviceIntent);
				android.os.Process.killProcess(android.os.Process.myPid());
				break;
			}
		}
	}

}