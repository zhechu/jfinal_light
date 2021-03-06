package com.ugiant.modules.sys.service;

import java.util.List;

import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.common.service.BaseService;
import com.ugiant.modules.sys.model.Area;

/**
 * 区域 业务类
 * @author lingyuwang
 *
 */
public class AreaService extends BaseService {
	
	public static final AreaService service = Enhancer.enhance(AreaService.class); // 区域单例

	private Area areaDao = Area.dao;
	
	/**
	 * 获取所有区域列表
	 * @return
	 */
	public List<Record> findAll() {
		return areaDao.findAll();
	}
	
}
