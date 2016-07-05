package com.ugiant.modules.sys.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.modules.sys.model.Menu;
import com.ugiant.modules.sys.model.Role;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * @author lingyuwang
 *
 */
public class UserUtils {
	
	private static Menu menuDao = Menu.dao;
	
	private static Role roleDao = Role.dao;
	
	private static User userDao = User.dao;
	
	private UserUtils() {}

	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static Record get(Integer id){
		Record user = userDao.findById(id);
		if (user == null){
			return null;
		}
		List<String> roleNames = Lists.newArrayList();
		List<Record> roleList = roleDao.findByUserId(id);
		for (Record role : roleList) {
			roleNames.add(role.getStr("name"));
		}
		user.set("role_names", StringUtils.join(roleNames, ","));
		return user;
	}
	
	/**
	 * 菜单列表
	 * @return
	 */
	public static List<Record> getMenuList(){
		List<Record> menuList = menuDao.findAll();
		return menuList;
	}

	/**
	 * 获取 会话
	 * @return
	 */
	public static Session getSession(){
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
		} catch (InvalidSessionException e){}
		return null;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static Record getByLoginName(String loginName){
		Record user = userDao.getByLoginName(loginName);
		if (user == null){
			return null;
		}
		//user.setRoleList(roleDao.findByLoginName(loginName));
		return user;
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
		} catch (UnavailableSecurityManagerException e) {
			
		} catch (InvalidSessionException e){
			
		}
		return null;
	}

	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static Record getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			Record user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new Record();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new Record();
	}

}
