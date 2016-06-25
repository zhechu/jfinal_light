package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 机构 控制器
 * @author lingyuwang
 *
 */
public class OfficeController extends BaseController {

	/**
	 * 机构管理页
	 * @return
	 */
	public void index() {
		this.render("officeIndex.jsp");
	}

	/**
	 * 机构管理子页
	 * @return
	 */
	public void list() {
		this.render("officeList.jsp");
	}

	/**
	 * 机构添加页
	 * @return
	 */
	public void form() {
		this.render("officeForm.jsp");
	}
	
}
