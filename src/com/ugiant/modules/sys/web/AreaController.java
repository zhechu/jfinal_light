package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 区域 控制器
 * @author lingyuwang
 *
 */
public class AreaController extends BaseController {

	/**
	 * 区域管理页
	 * @return
	 */
	public void index() {
		this.render("areaList.jsp");
	}

	/**
	 * 区域添加页
	 * @return
	 */
	public void form() {
		this.render("areaForm.jsp");
	}
	
}
