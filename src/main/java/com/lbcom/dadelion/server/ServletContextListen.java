package com.lbcom.dadelion.server;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ServletContextListen
 *
 */
@WebListener
public class ServletContextListen implements ServletContextListener, ServletContextAttributeListener {

    /**
     * Default constructor. 
     */
    public ServletContextListen() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	String logPath = "..";
		try {
			ServletContext context = arg0.getServletContext();
	    	String springPath = context.getContextPath();
	    	context.setAttribute("springPath", springPath);//返回项目名
	    	
			String projectPath = System.getProperty("Ssm_try");
			File projectDir = new File(projectPath);
			logPath = projectDir.getParentFile().getParentFile().getPath(); //指定log存储地址
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置日志路径
		System.setProperty("logHome", logPath);
		System.out.println(System.getProperty("logHome"));
    }
	
}
