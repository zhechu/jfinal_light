package com.ugiant.modules.sys.service;

import com.jfinal.plugin.activerecord.Page;
import com.ugiant.modules.sys.model.User;

/**
 * 用户 业务类
 * @author lingyuwang
 *
 */
public class UserService {
	
	public static final UserService service = new UserService(); // 用户单例

	private User userDao = User.dao;

	/**
	 * 分页查询
	 * @param pageNo 当前页
	 * @param pageSize 每页显示条数
	 * @param user 用户对象
	 * @return
	 */
	public Page<User> findPageByUser(int pageNo, int pageSize, User user) {
		return userDao.findPageByUser(pageNo, pageSize, user);
	}
	
}
