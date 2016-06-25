package com.ugiant.modules.sys.route;

import com.ugiant.common.route.BaseRoutes;
import com.ugiant.common.web.PublicController;
import com.ugiant.modules.sys.web.AreaController;
import com.ugiant.modules.sys.web.DictController;
import com.ugiant.modules.sys.web.MenuController;
import com.ugiant.modules.sys.web.OfficeController;
import com.ugiant.modules.sys.web.RoleController;
import com.ugiant.modules.sys.web.UserController;

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
		
		this.add("/a/sys/menu", MenuController.class, SYS_PATH); // 菜单
		
		this.add("/a/sys/user", UserController.class, SYS_PATH); // 用户
		
		this.add("/a/sys/office", OfficeController.class, SYS_PATH); // 机构
		
		this.add("/a/sys/area", AreaController.class, SYS_PATH); // 区域
		
		this.add("/a/sys/role", RoleController.class, SYS_PATH); // 角色
		
		this.add("/a/sys/dict", DictController.class, SYS_PATH); // 字典
		
	}
}
