package com.start.security;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 屏幕监控
 * @author Start
 */
public class ScreenLockUnlockMonitor {
	Context mContext;
    Handler mHandler;
    BroadcastReceiver mLockUnlockReceiver;
    public static final int ACTION_SCREEN_LOCKUNLOCK = 1001;
    public static final String KEY_SCREEN_LOCKUNLOCK = "Key_Screen_LockUnlock";

    public ScreenLockUnlockMonitor(Context context) {
        mContext = context;
    }

    public void setObserver(Handler hanler) {
        mHandler = hanler;
    }
    /**
     * 监控屏幕广播
     */
    public void openMonitor() {
        if (null == mLockUnlockReceiver) {
            mLockUnlockReceiver = new ScreenOnReceiver();
        }
        final IntentFilter filter = new IntentFilter();
        //屏幕暗
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //屏幕亮
        filter.addAction(Intent.ACTION_SCREEN_ON);
        //屏幕是否解锁
        filter.addAction(Intent.ACTION_USER_PRESENT);
        //注册广播
        mContext.registerReceiver(mLockUnlockReceiver, filter);
    }
    /**
     * 关闭监听器
     */
    public void closeMonitor() {
        if (null != mLockUnlockReceiver) {
            mContext.unregisterReceiver(mLockUnlockReceiver);
        }
    }

    public class ScreenOnReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Message msg = Message.obtain();
            msg.what = ACTION_SCREEN_LOCKUNLOCK;
            Bundle data = new Bundle();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
            	//屏幕亮
                data.putString(KEY_SCREEN_LOCKUNLOCK, Intent.ACTION_SCREEN_ON);
            } else if(Intent.ACTION_USER_PRESENT.equals(action)){
            	//屏幕解锁
                data.putString(KEY_SCREEN_LOCKUNLOCK, Intent.ACTION_USER_PRESENT);
            }else{
            	//屏幕暗
                data.putString(KEY_SCREEN_LOCKUNLOCK, Intent.ACTION_SCREEN_OFF);
            }
            msg.setData(data);
            if(null != mHandler){
                mHandler.sendMessage(msg);
            }
        }
    }
}