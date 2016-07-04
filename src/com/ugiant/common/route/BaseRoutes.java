package com.ugiant.common.route;

import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;

/**
 * 路由 基类
 * @author lingyuwang
 *
 */
public abstract class BaseRoutes extends Routes {

	/**
	 * 管理基础路径
	 */
	protected String adminPath = PropKit.get("adminPath");
	
}
