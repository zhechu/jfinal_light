package com.ugiant.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
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
	
	public static final SystemService service = new SystemService(); // 系统管理业务单例

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
	public Page<Record> findPageByUser(int pageNo, int pageSize, Record user) {
		Page<Record> page = userDao.findPageByUser(pageNo, pageSize, user);
		for (Record sourUser : page.getList()) {
			List<String> roleNames = Lists.newArrayList();
			List<Record> roleList = roleDao.findByUserId(sourUser.getLong("id"));
			for (Record role : roleList) {
				roleNames.add(role.getStr("name"));
			}
			sourUser.set("role_names", StringUtils.join(roleNames, ","));
		}
		return page;
	}
	
	/**
	 * 查询用户
	 * @param user
	 * @return
	 */
	public List<Record> findByUser(Record user) {
		List<Record> userList = userDao.find(user);
		for (Record sourUser : userList) {
			List<String> roleNames = Lists.newArrayList();
			List<Record> roleList = roleDao.findByUserId(sourUser.getLong("id"));
			for (Record role : roleList) {
				roleNames.add(role.getStr("name"));
			}
			sourUser.set("role_names", StringUtils.join(roleNames, ","));
		}
		return userList;
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
	 * 根据用户 id 获取用户
	 * @param id 用户 id
	 * @return
	 */
	public Record getUserById(Long id) {
		Record user = userDao.findById(id);
		if (user == null){
			return null;
		}
		List<Record> roleList = roleDao.findByUserId(id);
		user.set("role_list", roleList);
		return user;
	}
	
	/**
	 * 更新用户登录信息
	 * @param user
	 */
	public boolean updateUserLoginInfo(Record currentUser) {
		Record user = new Record();
		user.set("id", currentUser.getLong("id"));
		// 保存上次登录信息
		user.set("old_login_ip", currentUser.get("login_ip"));
		user.set("old_login_date", currentUser.get("login_date"));
		// 更新本次登录信息
		user.set("login_ip", UserUtils.getSession().getHost());
		user.set("login_date", new Date());
		return userDao.update(user);
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public boolean updateUser(Record user) {
		Long[] roleIdList = user.get("role_id_list");
		if (roleIdList != null) { // 角色不为空，则更新角色
			Long userId = user.getLong("id");
			// 删除用户角色关系
			userRoleDao.deleteByUserId(userId);
			// 添加用户角色关系
			Record role = null;
			for (Long roleId : roleIdList) {
				role = new Record();
				role.set("user_id", userId);
				role.set("role_id", roleId);
				userRoleDao.save(role);
			}
			// 更新完角色，则删除，因数据库没有 role_id_list 字段
			user.remove("role_id_list");
		}
		return userDao.update(user);
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public boolean saveUser(Record user) {
		Long[] roleIdList = user.get("role_id_list");
		if (roleIdList != null) { // 角色不为空，则删除，因数据库没有 role_id_list 字段
			user.remove("role_id_list");
		}
		// 创建者和创建时间
		user.set("create_by", UserUtils.getUser().getLong("id"));
		user.set("create_date", new Date());
		boolean flag = userDao.save(user);
		if (roleIdList != null) { // 角色不为空，则更新角色
			Long userId = user.getLong("id");
			// 删除用户角色关系
			userRoleDao.deleteByUserId(userId);
			// 添加用户角色关系
			Record role = null;
			for (Long roleId : roleIdList) {
				role = new Record();
				role.set("user_id", userId);
				role.set("role_id", roleId);
				userRoleDao.save(role);
			}
		}
		return flag;
	}

	/**
	 * 更新用户密码
	 * @param id 用户id
	 * @param newPassword 新密码（明文）
	 */
	public boolean updatePasswordById(Long id, String password) {
		Record user = new Record();
		user.set("id", id);
		user.set("password", entryptPassword(password));
		return userDao.update(user);
	}

	/**
	 * 登录名是否已存在
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
	public boolean deleteUser(Record user) {
		// 删除用户角色关系
		userRoleDao.deleteByUserId(user.getLong("id"));
		return userDao.delete(user);
	}
	
}
