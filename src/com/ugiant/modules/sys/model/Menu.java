package com.ugiant.modules.sys.model;

import java.util.List;

import com.ugiant.common.model.BaseModel;

/**
 * 菜单 model
 * @author lingyuwang
 *
 */
public class Menu extends BaseModel<Menu> {
	
	private static final long serialVersionUID = -4900058748571010519L;
	
	public static final Menu dao = new Menu();

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<Menu> findAllList() {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*, p.name parent_name");
		sql.append(" from sys_menu a");
		sql.append(" left join sys_menu p on p.id = a.parent_id");
		sql.append(" order by a.sort");
		return Menu.dao.find(sql.toString());
	}

}
