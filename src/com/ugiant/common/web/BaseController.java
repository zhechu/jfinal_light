package com.ugiant.common.web;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

/**
 * controller 基类
 * @author lingyuwang
 *
 */
public abstract class BaseController extends Controller {
	
	/**
	 * 管理基础路径
	 */
	protected String adminPath = PropKit.get("adminPath");
	
	/**
	 * 管理首页
	 */
	protected String adminIndexPath = PropKit.get("adminIndexPath");
	
	/**
	 * 回显提示信息
	 */
	protected void setAttrMessage() {
		String message = this.getPara("message");
		if (StringUtils.isNotBlank(message)) {
			this.setAttr("message", message);
		}
		String type = this.getPara("type");
		if (StringUtils.isNotBlank(type)) {
			this.setAttr("type", type);
		}
	}
	
}
