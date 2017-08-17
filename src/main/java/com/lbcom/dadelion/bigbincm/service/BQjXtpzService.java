package com.lbcom.dadelion.bigbincm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lbcom.dadelion.bigbincm.dao.BQjXtpzDao;
import com.lbcom.dadelion.bigbincm.model.BQjXtpz;
import com.lbcom.dadelion.common.base.BaseService;

@Service
public class BQjXtpzService extends BaseService<BQjXtpz>{

	@Resource BQjXtpzDao _dao;

	public List<BQjXtpz> selectAll() {
		// TODO Auto-generated method stub
		return _dao.selectAll();
	}
}
