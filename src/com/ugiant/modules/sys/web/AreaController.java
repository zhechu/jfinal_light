package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.service.AreaService;

/**
 * 区域 控制器
 * @author lingyuwang
 *
 */
public class AreaController extends BaseController {

	private AreaService areaService = AreaService.service;
	
	/**
	 * 区域管理页
	 * @return
	 */
	public void index() {
		this.setAttr("list", areaService.findAll());
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
