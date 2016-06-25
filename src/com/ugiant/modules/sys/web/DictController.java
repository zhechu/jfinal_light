package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 字典 控制器
 * @author lingyuwang
 *
 */
public class DictController extends BaseController {

	/**
	 * 字典管理页
	 * @return
	 */
	public void index() {
		this.render("roleList.jsp");
	}

	/**
	 * 字典添加页
	 * @return
	 */
	public void form() {
		this.render("roleForm.jsp");
	}
	
}
