package test.file;

import java.io.File;
import java.io.FileInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.framework.utils.MD5;

import test.core.Base;

public class FileTestOperator extends Base {
	
	public void uploadapply() throws Exception{
		httpGet = new HttpGet(SERVERURL+"file/apply.do");
		params.put("accessid", accessid);
		params.put("applysize", "2073741824");
		params.put("generateip", "192.168.0.204");
		sendRequest(accesskey);
	}
	
	public void upload()throws Exception{
		httpPost = new HttpPost(SERVERURL+"fileRecord/upload.do");
		//传输文件
		String transportfilepath="D://Temp/OSS_API.pdf";
		//原始文件
		String originalfilepath=transportfilepath;
		
		File transportfile=new File(transportfilepath);
		File originalfile=new File(originalfilepath);
		params.put("accessid", "d60d112b1a0becae74557ea411a07d43");
		params.put("transportMode", "NO");
		params.put("transportSize", String.valueOf(transportfile.length()));
		params.put("transportMD5", MD5.md5file(transportfilepath));
		params.put("originalSize", String.valueOf(originalfile.length()));
		params.put("originalMD5", MD5.md5file(originalfilepath));
		params.put("fileName",transportfile.getName());
		HttpEntity entity=new InputStreamEntity(new FileInputStream(transportfile),transportfile.length());
		httpPost.setEntity(entity);
		sendRequest();
	}
	
	public void download() throws Exception{
		download=true;
		httpPost = new HttpPost(SERVERURL+"fileRecord/download.do");
		params.put("code","73cf5f4d70a4f7006dc99f737961b739");
		sendRequest();
	}
}
