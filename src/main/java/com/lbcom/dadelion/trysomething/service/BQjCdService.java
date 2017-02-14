package com.lbcom.dadelion.trysomething.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbcom.dadelion.common.base.BaseService;
import com.lbcom.dadelion.trysomething.dao.BQjCdDao;
import com.lbcom.dadelion.trysomething.model.BQjCd;

/**
 * @description 
 * @author  liubin
 * @date 2016年3月11日 下午1:43:52 
 * @version 1.0 
 * @parameter
 * @since  
 * @return
 */
@Service
public class BQjCdService extends BaseService<BQjCd>{
	
	@Autowired
	BQjCdDao qjcd_dao;

	public List<BQjCd> selectByparams(Map<String, Object> map) {
		return qjcd_dao.selectByparams(map);
	}

}
