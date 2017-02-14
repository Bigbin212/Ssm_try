package com.lbcom.dadelion.bigbin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lbcom.dadelion.common.JSONUtil;
import com.lbcom.dadelion.common.StringUtil;

/**
 * @CopyRight ©1995-2016: 苏州科达科技股份有限公司 
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
	@RequestMapping("/user-center.do")
	public ModelAndView userCenter(HttpServletRequest request) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String ip = IpGet.getIpAddress(request);
		map.put("username", username);
		map.put("ip", ip);
		log.error("[error] 用户名："+username +"-------[ip:]"+ ip);
		return new ModelAndView("bigbin/login/userMessage/userMessage.html", map);
	}
	
	@RequestMapping("/getUserImage.do")
	public void getYdDeviceDetail(HttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession();
		String xlh = (String) session.getAttribute("xlh");
		//判断编号是否为空
		if(StringUtil.isNullOrEmpty(xlh)){
			JSONUtil.writeJSONObjectToResponse(response, jsonObject);
			return;
		}
		String filePathString = getImgDir(request.getSession().getServletContext().getRealPath(""))+"/"+xlh+".jpg";
		File file = new File(filePathString);
		if(file.exists()){
			String   ipAddr = getIpAndPort(request.getRequestURL().toString());
		    jsonObject.put("url", ipAddr+"/userMessage/images/"+xlh+".jpg");
		}
		//返回数据
		JSONUtil.writeJSONObjectToResponse(response, jsonObject);
	}
	
	
	/**
	 * 上传头像
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadDevImg.do")
	public void uploadDevImg(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String xlh = (String) session.getAttribute("xlh");
		
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
			String ipAddr = getIpAndPort(request.getRequestURL().toString());
			String name = getImgDir(request.getSession().getServletContext().getRealPath(""))+xlh+".jpg";			
			FileOutputStream fileOutputStream = new FileOutputStream(name);
			byte[] buffer = new byte[1024];
			while (in.read(buffer) !=-1) {
				fileOutputStream.write(buffer);
			}
			//关闭流
			in.close();
			fileOutputStream.flush();
			fileOutputStream.close();	
			jsonObject.put("url", ipAddr+"/userMessage/images/"+xlh+".jpg");
		}catch(Exception e){
			jsonObject.put("msg", "头像保存失败!");
		}
		JSONUtil.writeJSONObjectToResponse(response, jsonObject);
	}
	
	/**
	 * 截取ip和端口
	 * getIpAndPort(这里用一句话描述这个方法的作用)        
	 * @return String    
	 * @Exception 异常对象
	 */
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
		int index = realPath.indexOf("webapps");
		if(index>=0){
			realPath = realPath.substring(0, index+7);
		}
		String filePath = realPath+"/userMessage/images/";
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		return filePath;
		
	}
}
