package com.buaa.shortytall.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.util.ImageUtil;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.util.Log;


//TODO 需要计算缓存文件的大小，定时清理缓存

public class AsyncImageLoaderSDCard {

	private static final int MB = 1024 * 1024;
	private static String FILE_FOLDER_PATH = MyHealth.APP_SDCARD_FOLDER + "/MyHealth/.imagcecache/";
	/**
	 * 计算sd空间
	 * @return sd空间大小 M
	 */
	public int freeSpaceOnSd(){
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double)stat.getAvailableBlocks()*(double)stat.getBlockSize()) / MB;
		return (int)sdFreeMB;
	}
	
	private static ExecutorService executorSerivce;
	private Handler mHandler = new Handler();
	
	public AsyncImageLoaderSDCard(){
		executorSerivce = Executors.newFixedThreadPool(5);
	}
	
	public Bitmap loadImage(final String imageUrl, final ImageCallback callback){
		Bitmap bitmap = ImageUtil.getBitmap(imageUrl);
		if(bitmap != null)
			return bitmap;
		
		//缓存中不存在图片，从网络中获取图片
		executorSerivce.submit(new Runnable() {
            public void run() {
                try {
                	final Bitmap bitmap = ImageUtil.getImageFromUrl(imageUrl);
                	if(bitmap != null){
                		ImageUtil.saveBitmapToSDCard(bitmap, imageUrl);
                	}
                    mHandler.post(new Runnable() {
                        public void run() {
                           callback.imageLoad(bitmap);
                        }
                    });
                } catch (Exception e) {
                	Log.e("Imageloader", imageUrl);
                    throw new RuntimeException(e);
                }
            }
        });
		return null;
	}
	
	public void releaseResources(){
		executorSerivce.shutdown();
	}
}
