package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 后台公开路由（无需登录） 控制器
 * @author lingyuwang
 *
 */
public class PublicController extends BaseController {

	/**
	 * 进入首页
	 */
	public void index() {
		this.render("index.jsp");
	}
	
}
