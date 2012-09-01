package test.manager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.framework.utils.MD5;

import test.core.Base;

public class ManagerTestOperator extends Base {
	
	public void login()throws Exception{
		httpGet = new HttpGet(SERVERURL+"manager/login.do");
		params.put("username","weizhenyao0624");
		params.put("ip","192.168.0.204");
		sendRequest(MD5.md5("5"));
	}
	
	public void create() throws Exception{
		httpPost = new HttpPost(SERVERURL+"manager/create.do");
		params.put("accessid", accessid);
		params.put("username", "weizhenyao0624");
		params.put("password", MD5.md5("5"));
		StringBuilder sb=new StringBuilder();
		sb.append("<acl>");
//		sb.append("<permission>/manager/create.do</permission>");
//		sb.append("<permission>/manager/login.do</permission>");
//		sb.append("<permission>/manager/logout.do</permission>");
//		sb.append("<permission>/manager/authorize.do</permission>");
//		sb.append("<permission>/permission/remove.do</permission>");
//		sb.append("<permission>/permission/create.do</permission>");
//		sb.append("<permission>/permission/builder.do</permission>");
//		sb.append("<permission>/role/remove.do</permission>");
//		sb.append("<permission>/role/create.do</permission>");
//		sb.append("<permission>/role/authorize.do</permission>");
		sb.append("</acl>");
		HttpEntity he=new StringEntity(sb.toString());
		httpPost.setEntity(he);
		sendRequest(accesskey);
	}
	
}
