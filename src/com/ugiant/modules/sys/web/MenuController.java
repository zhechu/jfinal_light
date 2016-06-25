package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 菜单 控制器
 * @author lingyuwang
 *
 */
public class MenuController extends BaseController {

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
