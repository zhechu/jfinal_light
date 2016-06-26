package com.ugiant.modules.sys.service;

import java.util.List;

import com.ugiant.modules.sys.model.Area;

/**
 * 区域 业务类
 * @author lingyuwang
 *
 */
public class AreaService {
	
	public static final AreaService service = new AreaService(); // 区域单例

	private Area areaDao = Area.dao;
	
	/**
	 * 获取所有区域列表
	 * @return
	 */
	public List<Area> findAll() {
		return areaDao.findAll();
	}
	
}
