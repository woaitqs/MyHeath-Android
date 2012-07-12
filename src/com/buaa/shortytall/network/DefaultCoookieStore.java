package com.buaa.shortytall.network;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

//单例模式
public class DefaultCoookieStore implements CookieStore{

	private static final String PREFERENCE_NAME = "SavedCookieStorePrefernceName";
	private static final String LEFT_SEPERATOR = ",99999,";
	private static final String MIDDEL_SEPERATOR = ",88888,";
	private static final String STORE_NAME = "store_name";
	
	private static DefaultCoookieStore singleStore;
	
	private List<Cookie> cookieList = new ArrayList<Cookie>();
	
	private DefaultCoookieStore(){
	}
	
	public static DefaultCoookieStore getInstance(Context context){
		if(singleStore == null) {
			singleStore = new DefaultCoookieStore();
			singleStore.restoreCookies(context);
		}
		
		return singleStore;
	}
	
	public void saveCookies(Context context) {
		if (singleStore == null) return;
		
		List<Cookie> cookies = singleStore.getCookies();
		
		StringBuffer buffer = new StringBuffer();
		for (Cookie cookie : cookies) {
			buffer.append(LEFT_SEPERATOR);
			buffer.append(cookie.getName() + MIDDEL_SEPERATOR);
			buffer.append(cookie.getValue() + MIDDEL_SEPERATOR);
			buffer.append("" + cookie.getVersion() + MIDDEL_SEPERATOR);
			buffer.append("" + cookie.getDomain() + MIDDEL_SEPERATOR);
			buffer.append("" + cookie.getPath() + MIDDEL_SEPERATOR);
			buffer.append("" + cookie.getExpiryDate().toGMTString() + MIDDEL_SEPERATOR);
		}
		
		SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		preferences.edit().putString(STORE_NAME, buffer.toString()).commit();
		
	}
	
	public void restoreCookies(Context context) {
//		if (singleStore == null) return;
//		
//		SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
//		String storeStr = preferences.getString(STORE_NAME, "");
//		
//		String[] cookieStrs = storeStr.split(LEFT_SEPERATOR);
//		for (String str : cookieStrs) {
//			String[] values = str.split(MIDDEL_SEPERATOR);
//			if (values.length != 6) continue;
//			
//			BasicClientCookie cookie = new BasicClientCookie(values[0], values[1]);
//			cookie.setVersion(Integer.parseInt(values[2]));
//			cookie.setDomain(values[3]);
//			cookie.setPath(values[4]);
//			long dateValue = Date.parse(values[5]);
//			Date date = new Date();
//			date.setTime(dateValue);
//			cookie.setExpiryDate(date);
//			
//			singleStore.addCookie(cookie);
//		}
	}

	public void addCookie(Cookie cookie) {
		cookieList.add(cookie);
	}

	public void clear() {
		cookieList.clear();
	}

	public boolean clearExpired(Date date) {
		return false;
	}

	public List<Cookie> getCookies() {
		return cookieList;
	}
}
