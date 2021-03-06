package com.ugiant.common.config;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.ugiant.modules.sys.model.Area;
import com.ugiant.modules.sys.model.Dict;
import com.ugiant.modules.sys.model.Menu;
import com.ugiant.modules.sys.model.Office;
import com.ugiant.modules.sys.model.Role;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.model.UserRole;
import com.ugiant.modules.sys.route.SystemRoute;

/**
 * jfinal 基础类
 * @author lingyuwang
 *
 */
public class BaseConfig extends JFinalConfig {
	
	/**
     * 供Shiro插件使用。
     */
    Routes routes;
    
	@Override
	public void configConstant(Constants me) {
		PropKit.use("config.properties");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setViewType(ViewType.JSP);
		me.setBaseUploadPath(PathKit.getWebRootPath() + PropKit.get("upload_dir"));
	}

	@Override
	public void configRoute(Routes me) {
		this.routes = me;
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
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true)); // 大小写不敏感
		me.add(arp);
		
		arp.addMapping(Menu.TABLE_NAME, Menu.class); // 菜单
		arp.addMapping(Dict.TABLE_NAME, Dict.class); // 字典
		arp.addMapping(Area.TABLE_NAME, Area.class); // 区域
		arp.addMapping(Office.TABLE_NAME, Office.class); // 机构
		arp.addMapping(User.TABLE_NAME, User.class); // 用户
		arp.addMapping(Role.TABLE_NAME, Role.class); // 角色
		arp.addMapping(UserRole.TABLE_NAME, UserRole.class); // 用户角色
		
		ShiroPlugin shiroPlugin = new ShiroPlugin(this.routes);
		shiroPlugin.setLoginUrl(PropKit.get("adminPath")+"/login");
		shiroPlugin.setSuccessUrl(PropKit.get("adminPath"));
		shiroPlugin.setUnauthorizedUrl(PropKit.get("adminPath")+"/login");
		me.add(shiroPlugin);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new ShiroInterceptor());
		
		me.add(new Interceptor() {
			@Override
			public void intercept(Invocation inv) {
				inv.getController().setAttr("actionKey", inv.getActionKey()); // 传递 actionKey
				// 执行正常逻辑
				inv.invoke();
			}
		});
	}
	
	@Override
	public void configHandler(Handlers me) {
		
	}
	
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}

}
