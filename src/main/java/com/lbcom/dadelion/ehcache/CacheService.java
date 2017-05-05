package com.lbcom.dadelion.ehcache;

import java.util.List;

public abstract class CacheService extends CacheManage{
	
	/**
	 * 存储所有
	 * storeAll(这里用一句话描述这个方法的作用) 
	 */
	@SuppressWarnings("rawtypes")
	public abstract void storeAll(List t);
	
	/**
	 * 添加一条
	 * add(这里用一句话描述这个方法的作用)  
	 */
	public abstract void add(Object value);
	
	/**
	 * 更新一条
	 * update(这里用一句话描述这个方法的作用) 
	 */
	public abstract void update(Object value);
	
	/**
	 * 删除一条
	 * delete(这里用一句话描述这个方法的作用) 
	 */
	public abstract void delete(Object value);
	
	/**
	 * 删除所有
	 * deleteAll(这里用一句话描述这个方法的作用) 
	 */
	public abstract void deleteAll();
	
	/**
	 * 获取所有
	 * getAll(这里用一句话描述这个方法的作用)
	 */
	public abstract Object  getAll();
	
}
