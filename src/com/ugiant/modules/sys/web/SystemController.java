package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 系统  控制器
 * @author lingyuwang
 *
 */
public class SystemController extends BaseController {

	/**
	 * 进入系统设置页
	 */
	public void index() {
		this.render("sysIndex.jsp");
	}
	
}
