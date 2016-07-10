package com.ugiant.modules.sys.web;

import com.ugiant.common.web.BaseController;

/**
 * 标签 控制器
 * @author lingyuwang
 *
 */
public class TagController extends BaseController {

	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	public void treeselect() {
		this.setAttr("url", this.getPara("url")); // 树结构数据URL
		this.setAttr("extId", this.getPara("extId")); // 排除的编号ID
		this.setAttr("checked", this.getPara("checked")); // 是否可复选
		this.setAttr("selectIds", this.getPara("selectIds")); // 指定默认选中的ID
		this.setAttr("isAll", this.getPara("isAll")); // 是否读取全部数据，不进行权限过滤
		this.setAttr("module", this.getPara("module")); // 过滤栏目模型（仅针对CMS的Category树）
		this.render("tagTreeselect.jsp");
	}
	
}
