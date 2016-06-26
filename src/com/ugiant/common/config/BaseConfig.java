package com.ugiant.common.config;


import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.ugiant.common.dict.Table;
import com.ugiant.modules.sys.model.Area;
import com.ugiant.modules.sys.model.Dict;
import com.ugiant.modules.sys.model.Menu;
import com.ugiant.modules.sys.model.Office;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.route.SystemRoute;

/**
 * jfinal 基础类
 * @author lingyuwang
 *
 */
public class BaseConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		PropKit.use("config.properties");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setViewType(ViewType.JSP);
	}

	@Override
	public void configRoute(Routes me) {
		me.add(new SystemRoute()); // 系统路由
	}

	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl", ""),PropKit.get("user", ""), PropKit.get("password", "").trim());
		me.add(c3p0Plugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setShowSql(true);
		me.add(arp);
		
		arp.addMapping(Table.SYS_MENU, Menu.class); // 菜单
		arp.addMapping(Table.SYS_DICT, Dict.class); // 字典
		arp.addMapping(Table.SYS_AREA, Area.class); // 区域
		arp.addMapping(Table.SYS_OFFICE, Office.class); // 机构
		arp.addMapping(Table.SYS_USER, User.class); // 用户
	}

	@Override
	public void configInterceptor(Interceptors me) {
		
	}
	
	@Override
	public void configHandler(Handlers me) {
		
	}
	
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}

}
