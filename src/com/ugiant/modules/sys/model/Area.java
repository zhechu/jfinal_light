package com.ugiant.modules.sys.model;

import java.util.List;

import com.ugiant.modules.sys.baseModel.BaseArea;

/**
 * 区域 model
 * @author lingyuwang
 *
 */
public class Area extends BaseArea<Area> {
	
	private static final long serialVersionUID = 8347926442086346963L;
	
	public static final Area dao = new Area();

	/**
	 * 获取所有区域列表
	 * @return
	 */
	public List<Area> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(" from sys_area a");
		sql.append(" order by a.sort, a.update_date desc");
		return find(sql.toString());
	}

}
