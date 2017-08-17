package com.lbcom.dadelion.common.task;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbcom.dadelion.bigbincm.model.BQjXtpz;
import com.lbcom.dadelion.bigbincm.service.BQjXtpzService;
import com.lbcom.dadelion.common.StringUtil;
import com.lbcom.dadelion.ehcache.XtpzCacheService;



/**
 * 初始化缓存 
 * 类名称：InitCacheTask 
 * @version     
 *
 */
public class InitCacheTask {

	private static Logger log = LoggerFactory.getLogger(InitCacheTask.class);
	
	@Resource
	private XtpzCacheService xtpzCacheService;
	@Resource
	private BQjXtpzService bQjXtpzService; 
	
	public void  initCache(){
		log.error("初始化缓存>>>>>>");
		
		log.debug("初始化系统配置缓存>>>>>>");
		List<BQjXtpz> bQjXtpzs =bQjXtpzService.selectAll();	
		if(!StringUtil.isNULLOrEmpty(bQjXtpzs)){
			
			xtpzCacheService.deleteAll();
			xtpzCacheService.storeAll(bQjXtpzs);
		}
		
	}
}
