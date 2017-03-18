package com.lbcom.dadelion.bigbin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lbcom.dadelion.bigbin.dao.BZUserDao;
import com.lbcom.dadelion.bigbin.model.BZUser;
import com.lbcom.dadelion.bigbin.service.dto.YhComparator;
import com.lbcom.dadelion.common.StringUtil;
import com.lbcom.dadelion.common.base.BaseService;
/**
 * 
 * @CopyRight ©1995-2016: bigbin  
 * @Project： 
 * @Module：
 * @Description 
 * @Author  liubin
 * @Date 2016年4月6日 上午10:54:50 
 * @Version 1.0
 */
@Service
public class BZUserService extends BaseService<BZUser>{
	@Resource BZUserDao _dao;
	private static Logger log = Logger.getLogger(BZUserService.class);
	private static final int pageSize = 20;
	private static final int offset = 0;
	/**
	 * 检测注册信息是否是已经存在的
	 * @param paramMap
	 * @return
	 */
	public List<BZUser> registerCheck(HashMap<String, Object> paramMap) {
		return _dao.selectByMap(paramMap);
	}

	/**
	 * 分页查询
	 * @param request
	 * @return
	 */
	public JSONObject selectByParams(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer iPageSize,iOffset= null;
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String start = request.getParameter("start");
		String pageSize = request.getParameter("pageSize");
		if (StringUtil.isNotNullOrEmpty(start)) {
			iOffset = Integer.valueOf(start);
		}else {
			iOffset = BZUserService.offset;
		}
		if (StringUtil.isNotNullOrEmpty(pageSize)) {
			iPageSize = Integer.valueOf(pageSize);
		} else {
			iPageSize = BZUserService.pageSize;
		}
		map.put("username", username);
		map.put("email", email);
		List<BZUser> list = null;

		int total = _dao.countByMap(map);
		try {
			list= _dao.selectByMap(map, new RowBounds(iOffset,iPageSize));
			log.debug("查询用户时异常！");
			if (list.size() > 0) {
				Collections.sort(list,new YhComparator());
			}
		} catch (Exception e) {
			log.error("查询用户时异常！"+e.getMessage());
			jsonObject.put("msg", e.getMessage());
		}
		if (total == 0 || list == null) {
			jsonObject.put("data", new ArrayList<BZUser>());
			jsonObject.put("total", 0);
		}
		jsonObject.put("data", list);
		jsonObject.put("total", total);
		return jsonObject;
	}

	/**
	 * 根据序列号查用户详情
	 * @param xlh
	 * @return
	 */
	public List<BZUser> selectByPrimaryKey(String xlh) {
		return _dao.selectByPrimaryKey(xlh);
	}

	public int updateByPrimaryKey(BZUser line) {
		return _dao.updateByPrimaryKey(line);
		
	}

}
