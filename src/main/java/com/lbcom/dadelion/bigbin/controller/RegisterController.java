package com.lbcom.dadelion.bigbin.controller;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbcom.dadelion.bigbin.model.BZUser;
import com.lbcom.dadelion.bigbin.service.BZUserService;
import com.lbcom.dadelion.common.JSONUtil;
import com.lbcom.dadelion.common.StringUtil;
import com.lbcom.dadelion.common.UUIDGenerator;
/**
 * @CopyRight ©1995-2016: 
 * @Project：
 * @Module：
 * @Description 用户注册
 * @Author  liubin
 * @Date 2016年3月10日 下午1:09:08 
 * @Version 1.0
 */
@Controller
public class RegisterController {

	@Resource
	private BZUserService user_ser;
	
	@RequestMapping("/register.do")
	public String Registerindex() {
		return "bigbin/login/register/register.html";
	}
	
	// 注册
	@RequestMapping("/registerindex.do")
	public void Register( HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("success", "1");
	    jsonObject.put("msg", "恭喜你注册成功！");
		try {
			user_ser.insert(register(request));
		} catch (Exception e) {
			jsonObject.put("success", "0");
			jsonObject.put("msg", e.getMessage());
		}
		JSONUtil.writeJSONObjectToResponse(response, jsonObject);
	}
	
	/**
	 * 自动生成序列号
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getuserxlh.do")
	public void getBh(HttpServletRequest request,HttpServletResponse response){
		String xlh = UUIDGenerator.getUUID();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("xlh", xlh);
		JSONObject jb = JSONObject.fromObject(resultMap);
		JSONUtil.writeJSONObjectToResponse(response, jb);
	}
	
	/**
	 * 注册信息实体化
	 * @param request
	 * @return
	 */
	private BZUser register(HttpServletRequest request){
		String xlh = request.getParameter("xlh");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		String ip = IpGet.getIpAddr(request);
		BZUser line = new BZUser();
		if (StringUtil.isNotNullOrEmpty(xlh)) {
			line.setXlh(xlh);
		}else{
			line.setXlh(UUIDGenerator.getUUID());
		}
		if (StringUtil.isNotNullOrEmpty(username)) {
			line.setUsername(username);
		}
		if (StringUtil.isNotNullOrEmpty(password)) {
			line.setPassword(password);
		}
		if (StringUtil.isNotNullOrEmpty(phone)) {
			line.setPhone(phone);
		}
		if (StringUtil.isNotNullOrEmpty(email)) {
			line.setEmail(email);
		}
		line.setIp(ip);
		line.setZcsj(new Date());
		return line;
	}
	
	/**
	 * 验证用户名是否重复
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/registerCheck.do")
	@ResponseBody
	public void nameCheck(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		String msg = "true";
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("username", username);
		List<BZUser> register = user_ser.registerCheck(paramMap);
		if (StringUtil.isNULLOrEmpty(register)) {
			msg = "false";
		}
		response.getWriter().print(msg);
	}
	
	/**
	 * 验证邮箱是否重复
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/registerEmail.do")
	@ResponseBody
	public void emailCheck(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email = request.getParameter("email");
		String msg = "true";
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("email", email);
		List<BZUser> register = user_ser.registerCheck(paramMap);
		if (StringUtil.isNULLOrEmpty(register)) {
			msg = "false";
		}
		response.getWriter().print(msg);
	}
}
