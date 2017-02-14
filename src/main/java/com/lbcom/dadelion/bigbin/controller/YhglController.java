package com.lbcom.dadelion.bigbin.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lbcom.dadelion.bigbin.model.BZUser;
import com.lbcom.dadelion.bigbin.service.BZUserService;
import com.lbcom.dadelion.common.JSONUtil;
import com.lbcom.dadelion.common.StringUtil;

/**
 * @CopyRight ©1995-2016: 
 * @Project： 
 * @Module：
 * @Description 用户信息管理
 * @Author  liubin
 * @Date 2016年4月28日 下午3:50:07 
 * @Version 1.0 
 */
@Controller
public class YhglController {
	
	@Resource
	BZUserService userSer;
	
	@RequestMapping("/yhgl.do")
	public String Yhxxgl(){
		return "bigbin/login/login/yhgl.html";
	}
	
	/**
	 * 分页显示详情
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryDate.do")
	public void queryDate(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userSer.selectByParams(request);
		} catch (Exception e) {
			
		}
		JSONUtil.writeJSONObjectToResponse(response, jsonObject);
	}
	
	/**
	 * 根据用户注册时的序列号查取详情信息
	 */
	@RequestMapping("/selectByxlh.do")
	public void selectByyhxlh(HttpServletRequest request,HttpServletResponse response){
		String xlh = request.getParameter("xlh");
		List<BZUser> list = userSer.selectByPrimaryKey(xlh);
		JSONArray jb = JSONArray.fromObject(list);
		JSONUtil.writeJSONArrayToResponse(response, jb);
	}
	
	@RequestMapping("/yhxxUpdate.do")
	public void yhxxUpdate(HttpServletRequest request,HttpServletResponse response){
		BZUser line = update(request);
		String msg = "";
		try {
			userSer.updateByPrimaryKey(line);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", msg);
		JSONObject jb = JSONObject.fromObject(resultMap);
		JSONUtil.writeJSONObjectToResponse(response, jb);
	}
	
	
	private BZUser update(HttpServletRequest request){
		String xlh = request.getParameter("xlh");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		BZUser line = new BZUser();
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
		line.setXlh(xlh);
		return line;
	}

}