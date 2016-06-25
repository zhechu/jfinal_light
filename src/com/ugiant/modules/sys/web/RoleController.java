package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 角色 控制器
 * @author lingyuwang
 *
 */
public class RoleController extends BaseController {

	/**
	 * 角色管理页
	 * @return
	 */
	public void index() {
		this.render("roleList.jsp");
	}

	/**
	 * 角色添加页
	 * @return
	 */
	public void form() {
		this.render("roleForm.jsp");
	}
	
}
