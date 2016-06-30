package com.ugiant.modules.sys.model;

import java.util.List;

import com.ugiant.common.model.BaseModel;

/**
 * 角色 model
 * @author lingyuwang
 *
 */
public class Role extends BaseModel<Role> {
	
	private static final long serialVersionUID = 5811814534062497587L;
	
	public static final Role dao = new Role();

	/**
	 * 获取所有角色列表
	 * @return
	 */
	public List<Role> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(" from sys_role a");
		sql.append(" order by a.update_date desc");
		return dao.find(sql.toString());
	}

}
