package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 用户 控制器
 * @author lingyuwang
 *
 */
public class UserController extends BaseController {

	/**
	 * 用户管理页
	 * @return
	 */
	public void index() {
		this.render("userIndex.jsp");
	}
	
	/**
	 * 用户管理子页
	 * @return
	 */
	public void list() {
		this.render("userList.jsp");
	}
	
	/**
	 * 用户添加页
	 * @return
	 */
	public void form() {
		this.render("userForm.jsp");
	}
	
	/**
	 * 用户个人信息页
	 * @return
	 */
	public void info() {
		this.render("userInfo.jsp");
	}
	
	/**
	 * 修改密码页
	 * @return
	 */
	public void modifyPwd() {
		this.render("userModifyPwd.jsp");
	}
	
}
