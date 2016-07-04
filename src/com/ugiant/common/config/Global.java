package com.ugiant.common.config;

import com.jfinal.kit.PropKit;

/**
 * 全局配置类
 * @author lingyuwang
 *
 */
public class Global {

	private Global() {}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		return PropKit.get(key);
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	
}
