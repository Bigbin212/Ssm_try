package com.lbcom.dadelion.bigbin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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

	@RequestMapping("/second.do")
	public ModelAndView second(){
		return new ModelAndView("bigbin/try_echarts/try_echarts.html");
	}
	@RequestMapping("/vindicate.do")
	public ModelAndView Vindicate(){
		return new ModelAndView("bigbin/Vindicate/vindicate.html");
	}
	@RequestMapping("/tab.do")
	public ModelAndView Tab(){
		return new ModelAndView("bigbin/tab_first/tabchange.html");
	}
	@RequestMapping("/carousel.do")
	public ModelAndView Carousel(){
		return new ModelAndView("bigbin/carousel/index.html");
	}
	@RequestMapping("/superslide.do")
	public ModelAndView superSlide(){
		return new ModelAndView("bigbin/carousel/superSlide.html");
	}
	@RequestMapping("/html5video.do")
	public String html5video(){
		return"bigbin/vidbg/fullscreenDemo.html";
	}
}
