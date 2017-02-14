package com.lbcom.dadelion.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * @CopyRight ©1995-2016: 
 * @Project：
 * @Module：
 * @Description 获取spring MVC上下文 
 * @Author  liubin
 * @Date 2016年11月11日 下午3:18:23 
 * @Version 1.0
 */
public class AppContext implements ApplicationContextAware {
	private static  ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		AppContext.context = context;
	}

	public static <T> T getBean(String beanId, Class<T> clazz) {
		return context.getBean(beanId, clazz);
	}

	public static ApplicationContext getContext() {
		return context;
	}
}