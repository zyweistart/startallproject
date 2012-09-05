package com.discover.core;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

	protected void makeTextLong(String message){
		if(message!=null){
			sendMessage(Toast.LENGTH_LONG,message);
		}
	}
	
	protected void makeTextShort(String message){
		if(message!=null){
			sendMessage(Toast.LENGTH_SHORT,message);
		}
	}
	
	protected void sendEmptyMessage(int what){
		sendMessage(what);
	}
	
	protected void sendMessage(int what){
		sendMessage(what,null);
	}
	
	protected void sendMessage(int what,Object obj){
		Message message=new Message();
		message.what=what;
		message.obj=obj;
		handler.sendMessage(message);
	}
	
	protected Handler handler=new Handler(){

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
	
	protected void processMessage(Message message){
		
	}
	
}