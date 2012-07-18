package com.buaa.shortytall.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.thread.GlobalMultiThreadExecutor;
import com.buaa.shortytall.util.MD5Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.text.TextUtils;

public class ImageCache {

	private LinkedHashMap<String, SoftReference<Bitmap>> memCache;
	private HashMap<String,String> urlMap;
	private static ImageCache instance;
	
	private static String IMAGE_FILE_FOLDER_PATH = MyHealth.APP_SDCARD_FOLDER + "/MyHealth/.imagcecache/";
	private static final int MB = 1024 * 1024;
	private static final int TIMEOUT = 3000;
	
	private static boolean STORAGE_ON = false;
	private Handler handler;
	
	private static DefaultHttpClient mHttpClient = null;
	
	static{
		if(mHttpClient == null){
			mHttpClient = new DefaultHttpClient();
		}
	}
	
	//lock for url map
	private Object urlMapLock = new Object();
	//image fresh lock
	private boolean freshImgWaiting = false;
	
	/**
	 * 计算sd空间
	 * @return sd空间大小 M
	 */
	public int freeSpaceOnSd(){
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double)stat.getAvailableBlocks()*(double)stat.getBlockSize()) / MB;
		return (int)sdFreeMB;
	}
	  
	static{
		if (instance == null){
			instance = new ImageCache();
		}
		//sdcard prepared
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File file = new File(IMAGE_FILE_FOLDER_PATH);
			if (!file.exists()){
				file.mkdirs();
				STORAGE_ON = true;
			}
		}
	}
	
	private ImageCache(){
		memCache = new LinkedHashMap<String, SoftReference<Bitmap>>();
		urlMap = new HashMap<String, String>();
	}

	public static ImageCache getInstance(){
	    return instance;
	}
	
	public Bitmap getBitmapFromUrl(String url){
		Bitmap bitmap = getCachedBitmap(url);
		//this means if bitmap is in cache , this is no need to get bitmap from url
		if (bitmap != null){
			return bitmap;
		}
		synchronized (urlMapLock) {
			//if urlMap is in, this bitmap is working for getting
			if (urlMap.containsKey(url)){
				return null;
			}else{
				urlMap.put(url, url);
			}
		}
		GlobalMultiThreadExecutor.getInstance().execute(new FetchImageRunnable(url), Thread.MIN_PRIORITY);
		return null;
	}
	
	private Bitmap getBitmapFromSDCard(String url){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Bitmap bmp;
            String filePathStr = IMAGE_FILE_FOLDER_PATH + MD5Util.encodeByMD5(url) + ".png";
            File file = new File(filePathStr);
            if (!file.exists()) {
                return null;
            }
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                bmp = BitmapFactory.decodeStream(fileInputStream);
                return bmp;
            }
            catch (Exception e) {
                return null;
            }
            finally {
                try {
                    fileInputStream.close();
                }
                catch (Exception e) {
                }
            }
        }
        else {
            return null;
        }
    }
	
	private class FetchImageRunnable implements Runnable{

		private String url;
		
		public FetchImageRunnable(String url){
			this.url = url;
		}
		
		@Override
		public void run() {
		    Bitmap bitmap = null;
		    bitmap = getBitmapFromSDCard(url);
		    if (bitmap == null){
		        bitmap = getBitmapFromUrlBackground(url);
		    }
			if (bitmap != null){
				handler.post(new BitmapFreshRunnable(url,bitmap));
			}
		}
		
	}
	
	private class BitmapFreshRunnable implements Runnable{

		private String mUrl;
		private Bitmap mBitmap;
		
		public BitmapFreshRunnable(String url,Bitmap bitmap){
			this.mUrl = url;
			this.mBitmap = bitmap;
		}
		
		@Override
		public void run() {
			if (!TextUtils.isEmpty(mUrl) && mBitmap != null ){
			    memCache.put(mUrl, new SoftReference<Bitmap>(mBitmap));
			    
				if (freshImgWaiting){
					return ;
				}
				freshImgWaiting = true;
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						handler.sendEmptyMessage(MyHealth.Msg.IMG_LOADED_COMPLETED);
						freshImgWaiting = false;
					}
				}, 100);
			}
			
		}
		
	}
	
	private byte[] fetchImg(HttpGet httpGet){
		//Http
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(params, TIMEOUT);
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
        HttpClientParams.setRedirecting(params, true);
        
        if (mHttpClient == null){
			mHttpClient = new DefaultHttpClient();
		}
		mHttpClient.setParams(params);
		try {
			HttpResponse response = mHttpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				byte[] imgbytes = EntityUtils.toByteArray(entity);
				//this method means entity is no longer need
				entity.consumeContent();
				
				return imgbytes;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Bitmap getBitmapFromUrlBackground(String url){
		//do cache
		File file = new File(IMAGE_FILE_FOLDER_PATH + MD5Util.encodeByMD5(url) + ".png");
		if (file.exists()){
			Bitmap filebitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			if (filebitmap != null){
				synchronized (urlMapLock) {
                    urlMap.remove(url);
                }
				return filebitmap;
			}else{
				file.delete();
			}
		}
		
		byte[] imgbytes = fetchImg(new HttpGet(url));
		if (imgbytes == null){
		    return null;
		}
		Bitmap bitmap = BitmapFactory.decodeByteArray(imgbytes, 0, imgbytes.length);
		
		
		if (STORAGE_ON){
			
            try {
            	FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(imgbytes);
                fileOutputStream.flush();
				fileOutputStream.close();
				
				return bitmap;
			} catch (IOException e) {
				e.printStackTrace();
				synchronized (urlMapLock) {
	                urlMap.remove(url);
	            }
			}
		}else {
            synchronized (urlMapLock) {
                urlMap.remove(url);
            }
        }
		return null;
	}
	
	public void setHandler(Handler handler){
		this.handler = handler;
	}
	
	public Bitmap getCachedBitmap(String Url){
		SoftReference<Bitmap> bitmap = memCache.get(Url);
		if (bitmap != null){
			Bitmap cachedBitmap = bitmap.get();
			if (cachedBitmap == null){
				memCache.remove(Url);
				urlMap.remove(Url);
			}
			return cachedBitmap;
		}
		return null;
	}
	
}
