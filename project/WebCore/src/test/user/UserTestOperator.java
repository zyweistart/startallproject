package test.user;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.framework.utils.MD5;

import test.core.Base;

public class UserTestOperator extends Base {
	
	public void register()throws Exception{
		httpPost = new HttpPost(SERVERURL+"user/register.do");
		params.put("source","WEB");
		params.put("usertype","USER");
		params.put("email","45442142@qq.com");
		params.put("password",MD5.md5("5"));
		params.put("ip","192.168.0.204");
		sendRequest();
	}
	
	public void login()throws Exception{
		httpGet = new HttpGet(SERVERURL+"user/login.do");
		params.put("source","WEB");
		params.put("loginmode","Normal");
		params.put("email","45442142@qq.com");
		params.put("ip","192.168.0.204");
		sendRequest(MD5.md5("5"));
	}
	
	public void resendActivationEmail()throws Exception{
		httpGet = new HttpGet(SERVERURL+"user/resendActivationEmail.do");
		params.put("email","4544242@qq.com");
		params.put("ip","192.168.0.204");
		sendRequest();
	}
	public void activation()throws Exception{
		httpGet = new HttpGet(SERVERURL+"user/activation.do");
		params.put("passkey","dwP1EP4kxqZ35SRsNx2JeHVUEqYP162vANWsnQNlW1xwmmoSZB7Pug==");
		params.put("handleip","192.168.0.204");
		sendRequest();
	}
	
	public void changePassword()throws Exception{
		httpPost = new HttpPost(SERVERURL+"user/changePassword.do");
		params.put("accessid",accessid);
		params.put("oldpassword",MD5.md5("5"));
		params.put("newpassword",MD5.md5("1"));
		sendRequest(accesskey);
	}
	
	public void forgetPassword()throws Exception{
		httpPost = new HttpPost(SERVERURL+"user/forgetPassword.do");
		params.put("email","4544242@qq.com");
		params.put("ip","192.168.0.223");
		sendRequest();
	}
	
	public void resetPassword()throws Exception{
		httpPost = new HttpPost(SERVERURL+"user/resetPassword.do");
		params.put("passkey","FMqQxIGzZiHR7ePORBOozXJLK6nTQi9yrldBMjsQFGhwmmoSZB7Pug==");
		params.put("newpassword",MD5.md5("1234"));
		sendRequest();
	}
	
}
