package com.lbcom.dadelion.bigbin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lbcom.dadelion.bigbin.model.BZUser;
import com.lbcom.dadelion.bigbin.service.BZUserService;
import com.lbcom.dadelion.common.JSONUtil;
import com.lbcom.dadelion.common.StringUtil;
import com.lbcom.dadelion.util.ConfigManager;

/**
 * @CopyRight ©1995-2016: 
 * @Project： 
 * @Module：
 * @Description 
 * @Author 
 * @Date 2016年11月8日 下午4:51:51 
 * @Version 1.0 
 */
@Controller
public class UserMessageController {
	
	private static Logger log = Logger.getLogger(UserMessageController.class);
	private static final String nginx_url = "ngnix_url"; //图片访问路径
	private static final String save_images = "save_images";//图片保存路径
	
	@Resource BZUserService zser;
	
	@RequestMapping("/user-center")
	public ModelAndView userCenter(HttpServletRequest request) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Object username = request.getSession(true).getAttribute("username");
		Object xlh = request.getSession(true).getAttribute("xlh");
		String ip = IpGet.getIpAddress(request);
		map.put("username", username);
		map.put("xlh", xlh);
		map.put("ip", ip);
		return new ModelAndView("bigbin/login/userMessage/userMessage","map",map);
	}
	
	@RequestMapping("/getUserImage")
	public void getUserImage(HttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		//HttpSession session = request.getSession();
		String xlh = (String) request.getSession(true).getAttribute("xlh");
		//判断编号是否为空
		if(StringUtil.isNullOrEmpty(xlh)){
			JSONUtil.writeJSONObjectToResponse(response, jsonObject);
			return;
		}
		List<BZUser> list = null;
		list = zser.selectByPrimaryKey(xlh);
		if(!StringUtil.isNULLOrEmpty(list)){
			jsonObject.put("url", list.get(0).getPhoto());
		}
		//String url = zser.selectByKey(xlh);
		//String filePathString = getImgDir(request.getSession().getServletContext().getRealPath(""))+"/"+xlh+".jpg";
		//File file = new File(filePathString);
		//if(file.exists()){
			//String ipAddr = getIpAndPort(request.getRequestURL().toString());
			//String url = ConfigManager.commonCfg.getString(nginx_url);
		    //jsonObject.put("url", ipAddr+"/userMessage/images/"+xlh+".jpg");
			//jsonObject.put("url", url+xlh+".jpg");
		//}
		//返回数据
		JSONUtil.writeJSONObjectToResponse(response, jsonObject);
	}
	
	
	/**
	 * 上传头像
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadDevImg")
	public void uploadDevImg(HttpServletRequest request,HttpServletResponse response){
		String xlh = (String) request.getSession(true).getAttribute("xlh");
		String url = ConfigManager.commonCfg.getString(nginx_url);
		
		JSONObject jsonObject = new JSONObject();
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
		//如果序列号号是空的，直接返回
		if(!resolver.isMultipart(request) || StringUtil.isNullOrEmpty(xlh)){  
			 return;
		}
		//示意图文件
		MultipartFile images  =  multiRequest.getFile("syt");
		//如果是空的
		if(images == null || images.isEmpty() || images.getSize()==0){
			jsonObject.put("msg", "读取文件异常!");
			JSONUtil.writeJSONObjectToResponse(response, jsonObject);
			return;
		}else if(images.getSize() > 5*1048576){
			jsonObject.put("msg", "图片的大小超过5M啦！！！！！");
			JSONUtil.writeJSONObjectToResponse(response, jsonObject);
			return;
		}
		InputStream in = null;
		try {
			//文件输入流
			in = images.getInputStream();
			//获取ip和端口
			//String ipAddr = getIpAndPort(request.getRequestURL().toString());
			String name = getImgDir(save_images)+xlh+".jpg";			
			//FileOutputStream out = new FileOutputStream(name);
			OutputStream out = new FileOutputStream(name);
			byte[] buffer = new byte[1024];
			int len = 0;
	        while( (len = in.read(buffer)) > 0){
	          out.write(buffer, 0, len);
	        }
	        //关闭流
	        out.close();
	        in.close();
			
	        //更新新用户图片字段
			BZUser line = new BZUser();
			line.setXlh(xlh);
			line.setPhoto(url+xlh+".jpg");
			zser.updateByPrimaryKey(line);
			
			jsonObject.put("url", url+xlh+".jpg");
			//jsonObject.put("url", ipAddr+"/userMessage/images/"+xlh+".jpg");
		}catch(IOException e){
			jsonObject.put("msg", "头像保存失败!"+e.getMessage());
			e.printStackTrace();
		}
		JSONUtil.writeJSONObjectToResponse(response, jsonObject);
	}
	
	/**
	 * 截取ip和端口
	 * getIpAndPort(这里用一句话描述这个方法的作用)        
	 * @return String    
	 * @Exception 异常对象
	 */
	@SuppressWarnings("unused")
	private String  getIpAndPort(String url){
		String ipString = null;
		try {
			String[] ip = url.split("/");	
			ipString= "http://"+ip[2];
		} catch (Exception e) {
			log.error("解析url异常" + e.getMessage());
		}
		return ipString;
	}
	
	
	/***
	 * 获取 (这里用一句话描述这个方法的作用)        
	 * @param        
	 * @return String    
	 * @Exception 异常对象
	 */
	private String getImgDir(String realPath){
	/*	int index = realPath.indexOf("webapps");
		if(index>=0){
			realPath = realPath.substring(0, index+7);
		}
		String filePath = realPath+"/userMessage/images/";*/
		
		String filePath = ConfigManager.commonCfg.getString(realPath);
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		return filePath;
		
	}
	
	@SuppressWarnings("unused")
	private byte[] GetImageStrByInPut(InputStream input) {  
        byte[] data = null;  
        // 读取图片字节数组  
        try {  
            data = new byte[input.available()];  
            input.read(data);  
            input.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        // 对字节数组Base64编码   
        return Base64.encodeBase64(data);// 返回Base64编码过的字节数组字符串  
    }  
}
