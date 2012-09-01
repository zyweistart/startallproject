package test.core;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.framework.config.Variable;
import org.framework.utils.HmacSHA1;
import org.framework.utils.MD5;
import org.framework.utils.StringUtils;
import org.framework.utils.TimeUtils;

import test.TestProject;

public class Base {
	
	protected final static String accessid=TestProject.accessid;
	protected final static String accesskey=TestProject.accesskey;

	protected String SERVERURL="http://127.0.0.1/";
	
	protected HttpClient httpClient=new DefaultHttpClient();

	protected HttpPost httpPost=null;
	
	protected HttpGet httpGet=null;
	
	protected Map<String,String> params=new HashMap<String,String>();
	
	protected Boolean download=false;
	
	/**
	 * MD5签名
	 */
	public void sendRequest()throws Exception{
		sendRequest("");
	}
	/**
	 * HmacSHA1签名
	 */
	public void sendRequest(String sig)throws Exception{
		params.put("requesttime",TimeUtils.getSysTimeLong());
		
		List<String> siglist=new ArrayList<String>();
		for(String k:params.keySet()){
			siglist.add(k.toLowerCase());
		}
		Collections.sort(siglist);
		
		StringBuilder sb = new StringBuilder();
		for(String s:siglist){
			sb.append(params.get(s));
			sb.append("\n");
		}
		for (String key : params.keySet()) {
			if(httpGet!=null){
				httpGet.addHeader(Variable.RequestPrefix+key, StringUtils.encode(params.get(key)));
			}
			if(httpPost!=null){
				httpPost.addHeader(Variable.RequestPrefix+key, StringUtils.encode(params.get(key)));
			}
		}
		if("".equals(sig)){
			if(httpGet!=null){
				httpGet.addHeader("signature",StringUtils.encode(MD5.md5(sb.toString())));
			}
			if(httpPost!=null){
				httpPost.addHeader("signature",StringUtils.encode(MD5.md5(sb.toString())));
			}
		}else{
			if(httpGet!=null){
				httpGet.addHeader("signature",StringUtils.encode(new String(Base64.encodeBase64(HmacSHA1.signature(sb.toString(),sig, "utf-8")))));
			}
			if(httpPost!=null){
				httpPost.addHeader("signature",StringUtils.encode(new String(Base64.encodeBase64(HmacSHA1.signature(sb.toString(),sig, "utf-8")))));
			}
		}
		HttpResponse response=null;
		if(httpGet!=null){
			response=httpClient.execute(httpGet);
		}
		if(httpPost!=null){
			response=httpClient.execute(httpPost);
		}
		HttpEntity entity=response.getEntity();
		InputStream is=entity.getContent();
		int len=-1;
		byte[] buffer=new byte[1024*8];
		try{
			if(download){
				Header name=response.getFirstHeader("FileName");
				if(name!=null){
					BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("d:/"+name.getValue()));
					while((len=is.read(buffer))!=-1){
						bos.write(buffer,0,len);
					}
					bos.flush();
					bos.close();
					System.out.println("下载完成!");
				}else{
					StringBuilder sbstr=new StringBuilder();
					while((len=is.read(buffer))!=-1){
						sbstr.append(new String(buffer,0,len));
					}
					System.out.println(sbstr.toString());
				}
			}else{
				StringBuilder sbstr=new StringBuilder();
				while((len=is.read(buffer))!=-1){
					sbstr.append(new String(buffer,0,len));
				}
				System.out.println(sbstr.toString());
			}
		}finally{
			is.close();
		}
	}
	
}
