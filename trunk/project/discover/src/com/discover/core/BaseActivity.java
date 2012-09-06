package com.discover.core;

import com.discover.utils.SharedPreferencesUtils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

	private SharedPreferencesUtils preferences;
	
	public SharedPreferencesUtils getSharedPreferencesUtils(){
		if(preferences==null){
			preferences=new SharedPreferencesUtils(this,Constant.PreferencesName);
		}
		return preferences;
	}
	
	public void makeTextLong(String message){
		if(message!=null){
			sendMessage(Toast.LENGTH_LONG,message);
		}
	}
	
	public void makeTextShort(String message){
		if(message!=null){
			sendMessage(Toast.LENGTH_SHORT,message);
		}
	}
	
	public void sendEmptyMessage(int what){
		sendMessage(what);
	}
	
	public void sendMessage(int what){
		sendMessage(what,null);
	}
	
	public void sendMessage(int what,Object obj){
		Message message=new Message();
		message.what=what;
		message.obj=obj;
		handler.sendMessage(message);
	}
	
	public Handler handler=new Handler(){

		@Override
		public void handleMessage(Message message) {
			switch(message.what){
			case Toast.LENGTH_SHORT:
				Toast.makeText(BaseActivity.this, String.valueOf(message.obj), Toast.LENGTH_SHORT).show();
				break;
			case Toast.LENGTH_LONG:
				Toast.makeText(BaseActivity.this, String.valueOf(message.obj), Toast.LENGTH_LONG).show();
				break;
			default:
				processMessage(message);
				break;
			}
		}
		
	};
	
	public void processMessage(Message message){
	}
	
}