package com.lbcom.dadelion.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lbcom.dadelion.common.JSONUtil;

@Controller
@RequestMapping("/JsPlugin")
public class JSPluginUtil {
	Logger logger = LoggerFactory.getLogger(JSPluginUtil.class);
	@RequestMapping("/getImgLayout.do")
	public void getImgLayout(HttpServletRequest req,
			HttpServletResponse res,String picUrl,Integer divWidth,Integer divHeight){
		if(picUrl.toUpperCase().indexOf("HTTP") < 0){
			picUrl = req.getScheme()+"://"+req.getServerName()+":"
					+req.getServerPort() + req.getContextPath() + "/" + picUrl;
		}
		JSONObject jsonObject = ImageUtil.getImageCssByUrl(picUrl, divWidth, divHeight);
		JSONUtil.writeJSONObjectToResponse(res, jsonObject);
	}
	
	/**
	 * @author 
	 * @date 2016-12-08 16:21:52
	 * @param width
	 * @param height
	 * @param imageUri
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @description  获取图片的压缩图
	 */
	@RequestMapping("/getThumbnails.do")
	public void getThumbnails(HttpServletRequest req, Integer width, Integer height, String imgUrl, Integer isIE, HttpServletResponse response) throws MalformedURLException, IOException{
		BufferedImage srcImage;

		logger.info("传入url:" + imgUrl);
		imgUrl = unescape(imgUrl);
		imgUrl = unescape(imgUrl);
		logger.info("两次转码url:" + imgUrl);
		imgUrl = URLDecoder.decode(imgUrl, "utf-8");
		logger.info("两次解码url:" + imgUrl);
		
		if(imgUrl.toUpperCase().indexOf("HTTP") < 0){
			imgUrl = req.getScheme()+"://"+req.getServerName()+":"
					+req.getServerPort() + req.getContextPath() + "/" + imgUrl;
		}
        String imgType="JPEG";
        if(imgUrl.toLowerCase().endsWith(".png")){
        	imgType="PNG";
        }else if(imgUrl.toLowerCase().endsWith(".jpg")){
        	imgType = "JPEG" ;
        }
        
        //获取远程图片流
        srcImage = ImageIO.read(new URL(getUTF8URI(imgUrl)));
        BufferedImage target = null;
		
		target = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB );
		//绘制缩小后的图
		target.getGraphics().drawImage(srcImage, 0, 0, width, height, null);   
		
		response.setContentType("image/"+imgType.toLowerCase());
		OutputStream os = response.getOutputStream();
		ImageIO.write(target, imgType, os);
        os.flush();    
        os.close();
	}
	
	private static final String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	private static final String getUTF8URI(String picUrl) throws UnsupportedEncodingException{
		String regex="([(]|[)]|[\u4e00-\u9fa5]+)";
		Matcher matcher = Pattern.compile(regex).matcher(picUrl);
		while(matcher.find()){
			String matched = matcher.group(0);
			picUrl = picUrl.replace(matched, URLEncoder.encode(matched, "utf-8"));
		}
		return picUrl;
	}
	
}
