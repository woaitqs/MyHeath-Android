package com.buaa.shortytall.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.buaa.shortytall.MyHealth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;


public class ImageUtil {
	
	private static String FILE_FOLDER_PATH = MyHealth.APP_SDCARD_FOLDER + "/pic/";
	
	/**
     * 下载图片,以字节数组返
     */
    public static byte[] downLoadImage(String path) throws Exception {
    	
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[100 * 1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
    
    /**
     * 返回图片
     * 
     * @param path url
     * @return
     */
    public static Bitmap getImageFromUrl(String path){
    	
    	if (path == null || path.length() == 0) 
    		return null;
    	
    	byte[] data;
		try {
			data = downLoadImage(path);
			if(data == null){
				return null;
			}
			
			Bitmap mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			return mBitmap;
		} 
		catch (Exception e) {
			return null;
		}
    }
    
    public static Bitmap getThumbImageFromUrl(String path){
    	if (path == null || path.length() == 0) 
    		return null;
    	
    	byte[] data;
		try {
			data = downLoadImage(path);
			if(data == null){
				return null;
			}
			Options opition = new Options();
			opition.outHeight = 48;
			opition.outWidth = 48;
			
			Bitmap mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length , opition);
			return mBitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static boolean saveBitmapToSDCard(Bitmap bitmap , String imageName){
    	if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
    		File path = new File(FILE_FOLDER_PATH);
    		//如果文件夹不存在
    		if(!path.exists()){
    			path.mkdirs();
    		}
    		
    		File file = new File(path + "/" + MD5Util.encodeByMD5(imageName));
    		
    		if (!file.exists()) {
                try {
                    file.createNewFile();
                }
                catch (Exception e) {
                    return false;
                }
            }
    		
    		FileOutputStream mFileOutputStream = null;
    		try {
				mFileOutputStream = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 75, mFileOutputStream);
				mFileOutputStream.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}finally{
				try {
					mFileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
    		
    	}
    	return true;
    }
    
    public static Bitmap getBitmap(String imageName){
    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Bitmap bmp;
            String filePathStr = FILE_FOLDER_PATH + MD5Util.encodeByMD5(imageName);
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
}
