package com.ugiant.modules.sys.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.common.utils.PageUtils;
import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.service.SystemService;
import com.ugiant.modules.sys.utils.UserUtils;

/**
 * 用户 控制器
 * @author lingyuwang
 *
 */
@RequiresUser
public class UserController extends BaseController {

	private SystemService systemService = SystemService.service;
	
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
		Page<Record> page = systemService.findPageByUser(pageNo, pageSize, user);
		this.setAttr("page", page);
		this.render("userList.jsp");
	}
	
	/**
	 * 用户添加页
	 * @return
	 */
	public void form() {
		this.setAttr("allRoles", systemService.findAllRole());
		this.render("userForm.jsp");
	}
	
	/**
	 * 用户个人信息页
	 * @return
	 */
	@Before({Tx.class})
	public void info() {
		User user = this.getModel(User.class);
		Record currentUser = UserUtils.getUser();
		if (user!=null && StringUtils.isNotBlank(user.getStr("name"))){
			currentUser.set("email", user.getStr("email"));
			currentUser.set("phone", user.getStr("phone"));
			currentUser.set("mobile", user.getStr("mobile"));
			currentUser.set("remarks", user.getStr("remarks"));
			currentUser.set("photo", user.getStr("photo"));
			//currentUser.update();
			this.setAttr("message", "保存用户信息成功");
		}
		this.setAttr("user", currentUser);
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
