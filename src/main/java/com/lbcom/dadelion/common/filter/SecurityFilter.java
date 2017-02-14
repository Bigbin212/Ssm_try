package com.lbcom.dadelion.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



/**
 * 权限控制过滤器
 */
public class SecurityFilter implements Filter {

	private static Logger log = Logger.getLogger(SecurityFilter.class);

	public SecurityFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String url = req.getRequestURI();
		
	/*	if (url.contains("login.do")){
			Cookie[] cookies = req.getCookies();
			String username = null;
			
			System.err.println("======进入过滤器=====");
			
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if (c.getName().equalsIgnoreCase("username")) {
						System.err.println("======找到cookie中的username:====="+ c.getValue());
						username = c.getValue();
					}
				}
				chain.doFilter(req, res);
			}
			if (username == null) {
				username = (String) MemCachedUtil.get(username);
				username = (String) req.getSession().getAttribute("username");
				log.debug("======获取到的用户名====="+username);
				//放行
				chain.doFilter(req, res);
			}
		}else{*/
			// 直接放行
			chain.doFilter(req, res);
		/*}*/
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
	
}
