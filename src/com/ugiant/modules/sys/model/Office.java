package com.ugiant.modules.sys.model;

import java.util.List;

import com.ugiant.common.model.BaseModel;

/**
 * 机构 model
 * @author lingyuwang
 *
 */
public class Office extends BaseModel<Office> {
	
	private static final long serialVersionUID = 1816843156414268447L;
	
	public static final Office dao = new Office();

	/**
	 * 根据父 ids（以逗号,隔开）模糊查询机构
	 * @param parentIds 父 ids（以逗号,隔开）
	 * @return
	 */
	public List<Office> findByParentIdsLike(String parentIds) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*, p.name as parent_name, ar.name as area_name, ar.parent_ids as area_parent_ids, pp.name as primary_person_name, dp.name as deputy_person_name");
		sql.append(" from sys_office a");
		sql.append(" left join sys_office p on p.id = a.parent_id");
		sql.append(" left join sys_area ar on ar.id = a.area_id");
		sql.append(" left join sys_user pp on pp.id = a.primary_person");
		sql.append(" left join sys_user dp on dp.id = a.deputy_person");
		sql.append(" where a.parent_ids like ?");
		sql.append(" order by a.sort, a.update_date desc");
		return dao.find(sql.toString(), parentIds);
	}

	/**
	 * 获取所有机构列表
	 * @return
	 */
	public List<Office> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*, p.name as parent_name, ar.name as area_name, ar.parent_ids as area_parent_ids, pp.name as primary_person_name, dp.name as deputy_person_name");
		sql.append(" from sys_office a");
		sql.append(" left join sys_office p on p.id = a.parent_id");
		sql.append(" left join sys_area ar on ar.id = a.area_id");
		sql.append(" left join sys_user pp on pp.id = a.primary_person");
		sql.append(" left join sys_user dp on dp.id = a.deputy_person");
		sql.append(" order by a.sort, a.update_date desc");
		return dao.find(sql.toString());
	}

}
