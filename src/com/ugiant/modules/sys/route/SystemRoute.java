package com.ugiant.modules.sys.route;

import com.ugiant.common.route.BaseRoutes;
import com.ugiant.common.web.PublicController;
import com.ugiant.modules.sys.web.AreaController;
import com.ugiant.modules.sys.web.DictController;
import com.ugiant.modules.sys.web.MenuController;
import com.ugiant.modules.sys.web.OfficeController;
import com.ugiant.modules.sys.web.RoleController;
import com.ugiant.modules.sys.web.SystemController;
import com.ugiant.modules.sys.web.TagController;
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
		
		this.add("/", PublicController.class); // 公共
		
		this.add(adminPath, SystemController.class, SYS_PATH); // 系统
		
		this.add(adminPath+"/sys/menu", MenuController.class, SYS_PATH); // 菜单
		
		this.add(adminPath+"/sys/user", UserController.class, SYS_PATH); // 用户
		
		this.add(adminPath+"/sys/office", OfficeController.class, SYS_PATH); // 机构
		
		this.add(adminPath+"/sys/area", AreaController.class, SYS_PATH); // 区域
		
		this.add(adminPath+"/sys/role", RoleController.class, SYS_PATH); // 角色
		
		this.add(adminPath+"/sys/dict", DictController.class, SYS_PATH); // 字典
		
		this.add(adminPath+"/tag", TagController.class, SYS_PATH); // 标签
		
	}
}
