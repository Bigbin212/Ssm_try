package com.lbcom.dadelion.ehcache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lbcom.dadelion.bigbincm.dao.BQjXtpzDao;
import com.lbcom.dadelion.bigbincm.model.BQjXtpz;
import com.lbcom.dadelion.common.AppContext;
import com.lbcom.dadelion.common.StringUtil;

/**
 * @Description 通用缓存工具类
 * @Version 1.0 
 */
@Service("cacheUtil")
public class CacheUtil {
	private  static CacheUtil cacheUtil;
	@Resource
	private XtpzCacheService xtpzCacheService;
	@Resource
	private BQjXtpzDao xtpzDao ;
	
	public CacheUtil() {
		System.err.println(" init bean cacheUtil");
	}
	
	public static CacheUtil getInstance(){
		if(null==cacheUtil){
			cacheUtil = (CacheUtil) AppContext.getContext().getBean("cacheUtil");
		}
		return cacheUtil;
	}
	
	@SuppressWarnings("unchecked")
	public String getXtpzValue(String key) {
		//从缓存中获取配置信息
		List<BQjXtpz> bQjXtpzs = (List<BQjXtpz>) xtpzCacheService.getAll();
		String value = null;
		
		//如果获取的配置值为空或者key为空
		 if(StringUtil.isNullOrEmpty(key)){
			return value;
		}
		if(StringUtil.isNULLOrEmpty(bQjXtpzs)){
			BQjXtpz entity = xtpzDao.selectByKey(key);
			if(entity != null)
			{
				value = entity.getVal();
			}
			return value;
		}		
		for(BQjXtpz bQjXtpz:bQjXtpzs){
			//如果等于key值，则赋值并跳出循环
			if(key.equals(bQjXtpz.getName())){
				value = bQjXtpz.getVal();
				break;
			}
		}
		return value;
	}

}
