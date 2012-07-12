package com.buaa.shortytall.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.buaa.shortytall.MyHealth;

public abstract class AbstractNetWorkThread extends Thread{

	//相应时间
	private final static int TIMEOUT = 6000;
	
	private String mUrl = null;
	
	private static DefaultHttpClient mHttpClient = null;
	
	public DefaultHttpClient getInstance(){
		if(mHttpClient == null){
			mHttpClient = new DefaultHttpClient();
		}
		return mHttpClient;
	}
	
	public abstract String getRequestUrl();
	
	public void setUrl() {
		mUrl = getRequestUrl();
	}
	
	protected String executePost(List<BasicNameValuePair> pairs) throws ClientProtocolException, IOException{
	    setUrl();
		if(mUrl == null){
			return null;
		}
		HttpPost httpPost = new HttpPost(mUrl);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs,HTTP.UTF_8));
		
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(params, TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
		HttpClientParams.setRedirecting(params, false);
		
		if(mHttpClient == null){
			
			mHttpClient = new DefaultHttpClient();
		}
		mHttpClient.setParams(params);
		
		DefaultCoookieStore cookieStore = DefaultCoookieStore.getInstance(MyHealth.getCurrentContext());
		mHttpClient.setCookieStore(cookieStore);
		HttpResponse response = mHttpClient.execute(httpPost);
		cookieStore.saveCookies(MyHealth.getCurrentContext());
		
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode == 200){
			return EntityUtils.toString(response.getEntity());
		}
		return null;
	}
	
	protected String executeMultipartPost(Bitmap bitmap,String filename ,List<BasicNameValuePair> pairs){
	    setUrl();
		if(mUrl == null){
			return null;
		}
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 75, bos);
			
			byte[] data = bos.toByteArray();
			
			HttpPost mHttpPost = new HttpPost(mUrl);
			mHttpPost.setEntity(new UrlEncodedFormEntity(pairs,HTTP.UTF_8));
			
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setSoTimeout(params, TIMEOUT);
			HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
			HttpClientParams.setRedirecting(params, false);
			
			if(mHttpClient == null){
				mHttpClient = new DefaultHttpClient();
			}
			mHttpClient.setParams(params);
			
			ByteArrayBody bab = new ByteArrayBody(data, filename);
			
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("photo", bab);
			for(BasicNameValuePair item : pairs){
				reqEntity.addPart(item.getName(), new StringBody(item.getValue()));
			}
			mHttpPost.setEntity(reqEntity);
			
			HttpResponse response = mHttpClient.execute(mHttpPost);
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode == 200){
				return EntityUtils.toString(response.getEntity());
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected String executeGet() throws ClientProtocolException, IOException{
	    setUrl();
		if(mUrl == null)
			return null;
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(params, TIMEOUT);
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
        HttpClientParams.setRedirecting(params, true);
        
        if (mHttpClient == null){
			mHttpClient = new DefaultHttpClient();
		}
		mHttpClient.setParams(params);
		
//		DefaultCoookieStore cookieStore = DefaultCoookieStore.getInstance(MyHealth.getCurrentContext());
//		mHttpClient.setCookieStore(cookieStore);
		
        HttpGet get = new HttpGet(mUrl);
        HttpResponse response = mHttpClient.execute(get);
//		cookieStore.saveCookies(MyHealth.getCurrentContext());
        
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        return null;
	}
}
