package com.lbcom.dadelion.bigbin.service.dto;

import java.util.Comparator;

import com.lbcom.dadelion.bigbin.model.BZUser;

/**
 * @CopyRight ©1995-2016: 
 * @Project： 
 * @Module：
 * @Description 
 * @Author  liubin
 * @Date 2016年11月28日 下午7:28:41 
 * @Version 1.0 
 */
public class YhComparator implements Comparator<BZUser>{

	@Override
	public int compare(BZUser o1, BZUser o2) {
		int ret = 0;
		if (o1.getUsername().compareTo(o2.getUsername()) > 0) {
			return 1;
		}else if (o1.getUsername().compareTo(o2.getUsername()) == 0) {
			return 0;
		} else if (o1.getUsername().compareTo(o2.getUsername()) < 0) {
			return -1;
		}
		return ret;
	}

}
