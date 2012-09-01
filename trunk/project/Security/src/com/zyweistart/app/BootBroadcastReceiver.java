package com.zyweistart.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 开机自动启动服务广播
 * @author Start
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			Intent sayHelloIntent=new Intent(context,SecurityActivity.class);
			sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(sayHelloIntent);
		}
	}

}