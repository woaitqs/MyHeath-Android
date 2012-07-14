package com.buaa.shortytall.util;

import java.security.MessageDigest;

public class MD5Util {

	/**
	    * 转换字节数组为十六进制字符串
	    * @param 字节数组
	    * @return 十六进制字符串
	    */
	    private static String byteArrayToHexString(byte[] bts) {
	          StringBuffer des = new StringBuffer();
	          String tmp = null;

	          for (int i = 0; i < bts.length; i++) {
	          tmp = (Integer.toHexString(bts[i] & 0xFF));
	          if (tmp.length() == 1) {
	             des.append("0");
	          }
	           des.append(tmp);
	       }
	        return des.toString();
	    }
	    
	    /** 对字符串进行MD5加密 */
	    //digest 方法同步有问题
	    public synchronized static String encodeByMD5(String originString) {
	      if (originString != null) {
	         try {
	            // 创建具有指定算法名称的信息摘要
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
	            byte[] results = md.digest(originString.getBytes());
	            // 将得到的字节数组变成字符串返回
	            String resultString = byteArrayToHexString(results);
	            return resultString.toUpperCase();
	         } catch (Exception ex) {
	            ex.printStackTrace();
	         }
	      }
	      return originString;
	   }
}
