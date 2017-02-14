package com.lbcom.dadelion.common.base.result;

import java.util.List;

/**
 * @description 
 * @author  liubin
 * @date 2015年12月14日 下午5:11:12 
 * @version 1.0 
 * @param <T>
 * @parameter
 * @since  
 * @return
 */
public class PageResultEntity<T> {

	private List<T> data;
	
	private int count;

    public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}
