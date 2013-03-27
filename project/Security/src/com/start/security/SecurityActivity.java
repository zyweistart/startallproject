package com.start.security;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public final class SecurityActivity extends Activity implements OnClickListener {

	private final static String SecuritySensorServiceName=SecuritySensorService.class.getName();

	private Intent mServiceIntent;

	private Button btnStartService;
	private Button btnStopService;
	private Button btnExit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);
		
		 mServiceIntent = new Intent(this, SecuritySensorService.class);
		 
		 //开启服务
		 btnStartService=(Button)findViewById(R.id.btnStartService);
		 btnStartService.setOnClickListener(this);
		 //停止服务
		 btnStopService=(Button)findViewById(R.id.btnStopService);
		 btnStopService.setOnClickListener(this);
		 //退出程序
		 btnExit=(Button)findViewById(R.id.btnExit);
		 btnExit.setOnClickListener(this);
		 
		 TextView tv=(TextView)findViewById(R.id.txtInfo);
		 String mAcceleRometerSensor =getAppContext().getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER)==null?"不支持":"支持";
		 String mProximitySensor =getAppContext().getSensorManager().getDefaultSensor(Sensor.TYPE_PROXIMITY)==null?"不支持":"支持";
		 tv.setText("重力传感器："+mAcceleRometerSensor+"\n距离传感器："+mProximitySensor);
	}

	@Override
	protected void onResume() {
		if(getAppContext().isServiceStart(SecuritySensorServiceName)){
			btnStartService.setEnabled(false);
			btnStopService.setEnabled(true);
		}else{
			btnStartService.setEnabled(true);
			btnStopService.setEnabled(false);
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnStartService:
			startService(mServiceIntent);
			btnStartService.setEnabled(false);
			btnStopService.setEnabled(true);
			break;
		case R.id.btnStopService:
			stopService(mServiceIntent);
			btnStartService.setEnabled(true);
			btnStopService.setEnabled(false);
			break;
		case R.id.btnExit:
			finish();
			break;
		}
	}

	public AppContext getAppContext(){
		return (AppContext)getApplication();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	@Override
	protected void onDestroy() {
		if(getAppContext().isServiceStart(SecuritySensorServiceName)){
			CharSequence tickerText = "防盗精灵";
		    Notification notification = new Notification(R.drawable.ic_launcher, tickerText, System.currentTimeMillis());
		    notification.flags = Notification.FLAG_AUTO_CANCEL;
		    //定义下拉通知栏时要展现的内容信息
		    Intent nIntent = new Intent(this, SecurityActivity.class);
		    PendingIntent contentIntent = PendingIntent.getActivity(this, 0,nIntent, 0);
		    notification.setLatestEventInfo(getApplicationContext(), tickerText, "手机监控中...",contentIntent); 
		    //用mNotificationManager的notify方法通知用户生成标题栏消息通知
		    getAppContext().getNotificationManager().notify(1, notification);
		}
		super.onDestroy();
	}
	
}