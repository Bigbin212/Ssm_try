package com.lbcom.dadelion.trysomething.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lbcom.dadelion.bigbin.controller.IpGet;
import com.lbcom.dadelion.common.base.BaseResource;
import com.lbcom.dadelion.trysomething.model.BQjCd;
import com.lbcom.dadelion.trysomething.service.BQjCdService;

/**
 * @description
 * @author liubin
 * @date 2016年3月7日 上午9:47:57
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Controller
public class TryWholeDivController extends BaseResource<BQjCd>{
	
	@Autowired
	BQjCdService cd_ser;
	
	/**
	 * 显示主页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/wholediv")
	public ModelAndView WholeDiv(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String ip = IpGet.getIpAddr(request);
		map.put("ip", ip);
		System.err.println("获取到的ip地址："+ip);
		return new ModelAndView("bigbin/TryDiv/wholediv", map);
	}

	/**
	 * 初始化菜单
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/initcd")
	private void Initcd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", "");
		
		String fjd = "FBQJ";
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("fjd", fjd);
		try {
			List<BQjCd> list = cd_ser.selectByparams(map);
			if(null != list && list.size()>0){
				jsonObject.put("data", list);
			}else{
				jsonObject.put("data", "{}");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			jsonObject.put("success", false);
			jsonObject.put("msg", "初始化菜单时异常"+ e.getMessage());
			
		}
		response.getWriter().print(jsonObject);
	}
}
