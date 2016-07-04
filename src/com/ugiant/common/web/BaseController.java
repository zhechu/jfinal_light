package com.ugiant.common.web;

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
	
}
