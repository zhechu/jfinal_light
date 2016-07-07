package com.ugiant.modules.sys.service;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.common.service.BaseService;
import com.ugiant.modules.sys.model.Menu;
import com.ugiant.modules.sys.model.Role;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.utils.UserUtils;

/**
 * 后台系统管理 业务类
 * @author lingyuwang
 *
 */
public class SystemService extends BaseService {
	
	public static final SystemService service = new SystemService(); // 系统管理业务单例

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	private Menu menuDao = Menu.dao;
	
	private Role roleDao = Role.dao;

	private User userDao = User.dao;
	
	/**
	 * 分页查询
	 * @param pageNo 当前页
	 * @param pageSize 每页显示条数
	 * @param user 用户对象
	 * @return
	 */
	public Page<Record> findPageByUser(int pageNo, int pageSize, User user) {
		return userDao.findPageByUser(pageNo, pageSize, user);
	}
	
	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<Record> findAllMenu() {
		return menuDao.findAll();
	}
	
	/**
	 * 获取所有角色列表
	 * @return
	 */
	public List<Record> findAllRole() {
		return roleDao.findAll();
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public Record getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	/**
	 * 更新用户登录信息
	 * @param user
	 */
	public void updateUserLoginInfo(User user) {
		// 更新本次登录信息
		user.set("login_ip", UserUtils.getSession().getHost());
		user.set("login_date", new Date());
		user.update();
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public boolean updateUser(Record user) {
		return userDao.update(user);
	}
	
}
