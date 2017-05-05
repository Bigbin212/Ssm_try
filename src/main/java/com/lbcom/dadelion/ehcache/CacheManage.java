package com.lbcom.dadelion.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


public abstract class CacheManage{
	
	private static CacheManager cacheManager = null;
	
	private static Cache cache = null;
	
	static{
		cacheManager = CacheManager.create();
		cache = new Cache(CacheConst.CACHE_DEFAULT_NAME, 100000, false, true, 0, 0);
		cacheManager.addCache(cache);
		cache = cacheManager.getCache(CacheConst.CACHE_DEFAULT_NAME);
	}
	
	/**
	 * 向缓存中添加数据
	 * put(这里用一句话描述这个方法的作用)   
	 */
	public  void  put(String key,Object object){
		Element element = new Element(key, object);
		//写加锁
		cache.acquireWriteLockOnKey(key);
		try {
			cache.put(element);
		} finally{
			//释放锁
			cache.releaseWriteLockOnKey(key);
		}
		
	}
	
	/**
	 * get(使用key读取)
	 */
	public Object get(String key){
		Element element = cache.get(key);		
		return null==element ? null:element.getObjectValue();
	}
	
	/**
	 * 根据key删除缓存
	 * delete(这里用一句话描述这个方法的作用)
	 */
	public void delete(String key){
		cache.acquireWriteLockOnKey(key);
		try {
			cache.remove(key);
		} finally{
			cache.releaseWriteLockOnKey(key);
		}	
	}
	
	/**
	 * 判断key是否存在
	 * contians(这里用一句话描述这个方法的作用)   
	 */
	public boolean contians(String key){
		return cache.isKeyInCache(key);
	}
}
