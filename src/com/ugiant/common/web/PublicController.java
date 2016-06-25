package com.ugiant.common.web;

import com.ugiant.common.web.BaseController;

/**
 * 后台公开路由（无需登录） 控制器
 * @author lingyuwang
 *
 */
public class PublicController extends BaseController {

	/**
	 * 进入系统设置页
	 */
	public void index() {
		this.render("sysIndex.jsp");
	}
	
	/**
	 * 进入用户管理页
	 */
	public void userList() {
		this.render("userList.jsp");
	}
	
}
