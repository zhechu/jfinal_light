package com.ugiant.modules.sys.route;

import com.ugiant.common.route.BaseRoutes;
import com.ugiant.modules.sys.web.PublicController;

/**
 * 系统路由管理
 * @author lingyuwang
 *
 */
public class SystemRoute extends BaseRoutes {
	
	private static final String SYS_PATH = "/WEB-INF/views/modules/sys";
	
	@Override
	public void config() {
		
		this.add("/a", PublicController.class, SYS_PATH);
		
	}
}
