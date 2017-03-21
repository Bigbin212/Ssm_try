package com.lbcom.dadelion.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class ImageUtil {
	private static Logger log = Logger.getLogger(ImageUtil.class);
	
	public static BufferedImage getImageIoFromUrl(String imgUrl) {
		URL url = null;
		InputStream is = null;
		BufferedImage img = null;
		try {
			url = new URL(imgUrl);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(1000);
			connection.connect();
			is = url.openStream();
			img = ImageIO.read(is);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.info("获取图片流异常",e);
		} catch (IOException e) {
			log.info("获取图片流异常",e);
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static JSONObject getImageCssByUrl(String url,int divWidth,int divHeight){
		BufferedImage img = getImageIoFromUrl(url);
		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		return getLayout(imgWidth, imgHeight, divWidth, divHeight);
	}
	
	private static JSONObject getLayout(int imgWidth,int imgHeight,int divWidth,int divHeight){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("position", "relative");
		jsonObject.put("text-align", "left");
		jsonObject.put("imgWidth", imgWidth);
		jsonObject.put("imgHeight", imgHeight);
		
		if(imgWidth < divWidth && imgHeight < divHeight){
			jsonObject.put("width", imgWidth);
			jsonObject.put("height", imgHeight);
			jsonObject.put("left", (divWidth - imgWidth)/2);
			jsonObject.put("top", (divHeight - imgHeight)/2);
			return jsonObject;
		}
		
		double imgBlc = (double) imgWidth / (double) imgHeight;
		double divBlc = (double) divWidth / (double) divHeight;
		if(imgBlc > divBlc){
			jsonObject.put("width", divWidth);
			jsonObject.put("height", (imgHeight*divWidth)/imgWidth );
			jsonObject.put("left", 0);
			jsonObject.put("top", (divHeight-(imgHeight*divWidth)/imgWidth)/2);
		}else{
			jsonObject.put("height", divHeight);
			jsonObject.put("width", (imgWidth*divHeight)/imgHeight);
			jsonObject.put("left", (divWidth-(imgWidth*divHeight)/imgHeight)/2);
			jsonObject.put("top", 0);
		}
		return jsonObject;
	}
	
	
	/**
	 * 对路径进行编码
	 * encodeURL(这里用一句话描述这个方法的作用)        
	 * author    
	 * 日期  2016年4月26日 下午3:47:47
	 * @param        
	 * @return String    
	 * @Exception 异常对象
	 */
	public static String encodeURL(String url,String encode)  
            throws UnsupportedEncodingException {  
        StringBuilder sb = new StringBuilder();  
        StringBuilder noAsciiPart = new StringBuilder();  
        for (int i = 0; i < url.length(); i++) {  
            char c = url.charAt(i);  
            if (c > 255) {  
                noAsciiPart.append(c);  
            } else {  
                if (noAsciiPart.length() != 0) {  
                    sb.append(URLEncoder.encode(noAsciiPart.toString(),encode));  
                    noAsciiPart.delete(0, noAsciiPart.length());  
                }  
                sb.append(c);  
            }  
        }  
        return sb.toString();  
    }  
}
