package com.ugiant.modules.sys.web;

import java.util.List;

import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.model.Role;
import com.ugiant.modules.sys.service.SystemService;

/**
 * 角色 控制器
 * @author lingyuwang
 *
 */
public class RoleController extends BaseController {

	private SystemService systemService = SystemService.service;
	
	/**
	 * 角色管理页
	 * @return
	 */
	public void index() {
		List<Role> list = systemService.findAllRole();
		this.setAttr("list", list);
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
