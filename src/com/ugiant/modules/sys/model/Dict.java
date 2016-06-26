package com.ugiant.modules.sys.model;

import java.util.List;

import com.ugiant.common.model.BaseModel;

/**
 * 字典 model
 * @author lingyuwang
 *
 */
public class Dict extends BaseModel<Dict> {
	
	private static final long serialVersionUID = -7558822960750888221L;
	
	public static final Dict dao = new Dict();

	/**
	 * 获取字典列表
	 * @param type 类型
	 * @return
	 */
	public List<Dict> findByType(String type) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(" from sys_dict a");
		sql.append(" where a.type = ?");
		sql.append(" order by a.type, a.sort, a.update_date desc");
		return dao.find(sql.toString(), type);
	}

}
