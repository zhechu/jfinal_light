package com.ugiant.modules.sys.utils;

import java.util.List;

import com.ugiant.modules.sys.model.Menu;

/**
 * 用户工具类
 * @author lingyuwang
 *
 */
public class UserUtils {
	
	private static Menu menuDao = Menu.dao;
	
	private UserUtils() {}
	
	/**
	 * 菜单列表
	 * @return
	 */
	public static List<Menu> getMenuList(){
		List<Menu> menuList = menuDao.findAll();
		return menuList;
	}
	
}
