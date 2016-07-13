package com.ugiant.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.common.security.Digests;
import com.ugiant.common.service.BaseService;
import com.ugiant.common.utils.Encodes;
import com.ugiant.modules.sys.model.Menu;
import com.ugiant.modules.sys.model.Role;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.model.UserRole;
import com.ugiant.modules.sys.utils.UserUtils;

/**
 * 后台系统管理 业务类
 * @author lingyuwang
 *
 */
public class SystemService extends BaseService {
	
	public static final SystemService service = Enhancer.enhance(SystemService.class); // 系统管理业务单例

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	private Menu menuDao = Menu.dao;
	
	private Role roleDao = Role.dao;

	private User userDao = User.dao;
	
	private UserRole userRoleDao = UserRole.dao;

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 分页查询用户
	 * @param pageNo 当前页
	 * @param pageSize 每页显示条数
	 * @param user 用户对象
	 * @return
	 */
	public Page<User> findPageByUser(int pageNo, int pageSize, User user) {
		return userDao.findPageByUser(pageNo, pageSize, user);
	}
	
	/**
	 * 查询用户
	 * @param user
	 * @return
	 */
	public List<User> findByUser(User user) {
		return userDao.find(user);
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
	public List<Role> findAllRole() {
		return roleDao.findAll();
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	/**
	 * 根据用户 id 获取用户
	 * @param id 用户 id
	 * @return
	 */
	public User getUserById(Long id) {
		User user = userDao.findById(id);
		if (user == null){
			return null;
		}
		return user;
	}
	
	/**
	 * 更新用户登录信息
	 * @param user
	 */
	@Before(Tx.class)
	public boolean updateUserLoginInfo(User currentUser) {
		// 保存上次登录信息
		currentUser.set("old_login_ip", currentUser.get("login_ip"));
		currentUser.set("old_login_date", currentUser.get("login_date"));
		// 更新本次登录信息
		currentUser.set("login_ip", UserUtils.getSession().getHost());
		currentUser.set("login_date", new Date());
		return userDao.update(currentUser);
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	@Before(Tx.class)
	public boolean updateUser(User user) {
		List<Long> roleIdList = user.getRoleIdList();
		if (!roleIdList.isEmpty()) { // 角色不为空，则更新角色
			Long userId = user.getId();
			// 删除用户角色关系
			userRoleDao.deleteByUserId(userId);
			// 添加用户角色关系
			UserRole userRole = null;
			for (Long roleId : roleIdList) {
				userRole = new UserRole();
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				userRole.save();
			}
		}
		return user.update();
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@Before(Tx.class)
	public boolean saveUser(User user) {
		List<Long> roleIdList = user.getRoleIdList();
		// 创建者和创建时间
		user.setCreateBy(UserUtils.getUser().getId());
		user.setCreateDate(new Date());
		boolean flag = user.save();
		if (!roleIdList.isEmpty()) { // 角色不为空，则更新角色
			Long userId = user.getId();
			// 删除用户角色关系
			userRoleDao.deleteByUserId(userId);
			// 添加用户角色关系
			UserRole userRole = null;
			for (Long roleId : roleIdList) {
				userRole = new UserRole();
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				userRole.save();
			}
		}
		return flag;
	}

	/**
	 * 更新用户密码
	 * @param id 用户id
	 * @param newPassword 新密码（明文）
	 */
	@Before(Tx.class)
	public boolean updatePasswordById(Long id, String password) {
		User user = new User();
		user.setId(id);
		user.setPassword(entryptPassword(password));
		return userDao.update(user);
	}

	/**
	 * 登录名是否不存在
	 * @param oldLoginName 原登录名
	 * @param loginName 新登录名
	 * @return
	 */
	public boolean isNotExistLoginName(String oldLoginName, String loginName) {
		boolean flag = false;
		if (loginName !=null && loginName.equals(oldLoginName)) {
			flag = true;
		} else if (loginName !=null && this.getUserByLoginName(loginName) == null) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	@Before(Tx.class)
	public boolean deleteUser(User user) {
		// 删除用户角色关系
		userRoleDao.deleteByUserId(user.getId());
		return user.delete();
	}
	
}
