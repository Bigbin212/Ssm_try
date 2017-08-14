package com.lbcom.dadelion.bigbin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lbcom.dadelion.common.DateUtil;

/**
 * 
 * @CopyRight :
 * @Project： 
 * @Module：
 * @Description 
 * @Author  liubin
 * @Date 2016年3月30日 下午1:44:19 
 * @Version 1.0
 */
@Controller
public class TestController {

	@RequestMapping("/second")
	public ModelAndView second(){
		return new ModelAndView("bigbin/try_echarts/try_echarts");
	}
	@RequestMapping("/vindicate")
	public ModelAndView Vindicate(){
		return new ModelAndView("bigbin/Vindicate/vindicate");
	}
	@RequestMapping("/tab")
	public ModelAndView Tab(){
		return new ModelAndView("bigbin/tab_first/tabchange");
	}
	@RequestMapping("/carousel")
	public ModelAndView Carousel(){
		return new ModelAndView("bigbin/carousel/index");
	}
	@RequestMapping("/superslide")
	public ModelAndView superSlide(){
		return new ModelAndView("bigbin/carousel/superSlide");
	}
	@RequestMapping("/html5video")
	public String html5video(){
		return"bigbin/vidbg/fullscreenDemo";
	}
	@RequestMapping("/test")
	public ModelAndView testAndView(){
		return new ModelAndView("bigbin/test/testBootstrap/testDemon");
	}
	@RequestMapping("/echarts3")
	public ModelAndView echarts3(){
		return new ModelAndView("bigbin/try_echarts/echarts.3.0");
	}
	
	public static void main(String[] args) {
		 List<String> dateList = new ArrayList<String>();
		 Calendar calendar = DateUtil.string2Calendar("20170809", new SimpleDateFormat("yyyyMMdd"));
         Calendar today = Calendar.getInstance();       
         int maxHour = 23;
         if (today.get(Calendar.DATE)<=calendar.get(Calendar.DATE)&&today.get(Calendar.YEAR)<=calendar.get(Calendar.YEAR)&&today.get(Calendar.MONTH)<=calendar.get(Calendar.MONTH)) {
             maxHour = today.get(Calendar.HOUR_OF_DAY);
         }
         for (int i = 0; i <= maxHour; i++) {
             dateList.add(i+"");
         }
         System.err.println(dateList);
	}
}
