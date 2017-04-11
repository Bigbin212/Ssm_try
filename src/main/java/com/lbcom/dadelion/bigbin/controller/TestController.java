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
}
