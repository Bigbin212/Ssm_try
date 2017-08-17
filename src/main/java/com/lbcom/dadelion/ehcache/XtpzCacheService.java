package com.lbcom.dadelion.ehcache;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class XtpzCacheService extends CacheService{

	@Override
	public void storeAll(List t) {
		put(CacheConst.CACHE_KEY_XTPZ, t);
	}

	@Override
	public void add(Object value) {
		
	}

	@Override
	public void update(Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		delete(CacheConst.CACHE_KEY_XTPZ);
	}

	@Override
	public Object getAll() {
		// TODO Auto-generated method stub
		return get(CacheConst.CACHE_KEY_XTPZ);
	}

}
