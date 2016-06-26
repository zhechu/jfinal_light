package com.ugiant.modules.sys.web;

import com.jfinal.plugin.activerecord.Page;
import com.ugiant.common.utils.PageUtils;
import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.service.UserService;

/**
 * 用户 控制器
 * @author lingyuwang
 *
 */
public class UserController extends BaseController {

	private UserService userService = UserService.service;
	
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
		int pageNo = PageUtils.getPageNo(this.getParaToInt("pageNo"));
		int pageSize = PageUtils.getPageSize(this.getParaToInt("pageSize"));
		User user = this.getModel(User.class); // 用户
		Page<User> page = userService.findPageByUser(pageNo, pageSize, user);
		this.setAttr("page", page);
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
