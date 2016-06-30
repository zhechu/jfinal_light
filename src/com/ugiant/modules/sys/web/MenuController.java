package com.ugiant.modules.sys.web;

import java.util.List;

import com.google.common.collect.Lists;
import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.model.Menu;
import com.ugiant.modules.sys.service.SystemService;

/**
 * 菜单 控制器
 * @author lingyuwang
 *
 */
public class MenuController extends BaseController {

	private SystemService systemService =SystemService.service;
	
	/**
	 * 菜单树
	 * @return
	 */
	public void tree() {
		this.setAttr("parentId", this.getParaToInt("parentId"));
		this.render("menuTree.jsp");
	}

	/**
	 * 菜单管理页
	 * @return
	 */
	public void index() {
		List<Menu> list = Lists.newArrayList();
		List<Menu> sourcelist = systemService.findAllMenu();
		Menu.sortList(list, sourcelist, Menu.getRootId(), true);
        this.setAttr("list", list);
		this.render("menuList.jsp");
	}
	
	/**
	 * 菜单添加页
	 * @return
	 */
	public void form() {
		this.render("menuForm.jsp");
	}
	
}
