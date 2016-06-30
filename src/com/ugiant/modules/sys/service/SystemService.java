package com.ugiant.modules.sys.service;

import java.util.List;

import com.ugiant.modules.sys.model.Menu;
import com.ugiant.modules.sys.model.Role;

/**
 * 后台系统管理 业务类
 * @author lingyuwang
 *
 */
public class SystemService {
	
	public static final SystemService service = new SystemService(); // 系统管理业务单例

	private Menu menuDao = Menu.dao;
	
	private Role roleDao = Role.dao;
	
	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<Menu> findAllMenu() {
		return menuDao.findAll();
	}
	
	/**
	 * 获取所有角色列表
	 * @return
	 */
	public List<Role> findAllRole() {
		return roleDao.findAll();
	}
	
}
