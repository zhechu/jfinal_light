package com.ugiant.modules.sys.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Db;
import com.ugiant.modules.sys.baseModel.BaseUserRole;

/**
 * 用户角色 model
 * @author lingyuwang
 *
 */
public class UserRole extends BaseUserRole<UserRole> {
	
	private static final long serialVersionUID = 5811814534062497587L;
	
	public static final UserRole dao = new UserRole();
	
	/**
	 * 删除用户角色
	 * @param userRole
	 * @return
	 */
	@Override
	public boolean delete() {
		List<Object> paras = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("delete from sys_user_role where 1 = 1");
		Long userId = this.getUserId();
		if (userId != null) {
			sql.append(" and user_id = ?");
			paras.add(userId);
		}
		Long roleId = this.getRoleId();
		if (roleId != null) {
			sql.append(" and role_id = ?");
			paras.add(roleId);
		}
		int result = Db.update(sql.toString(), paras.toArray());
		return result>0 ? true : false;
	}
	
	/**
	 * 根据用户删除用户角色
	 * @param userId 用户id
	 * @return
	 */
	public boolean deleteByUserId(Long userId) {
		int result = Db.update("delete from sys_user_role where user_id = ?", userId);
		return result>0 ? true : false;
	}
	
	/**
	 * 根据角色删除用户角色
	 * @param roleId 角色id
	 * @return
	 */
	public boolean deleteByRoleId(Long roleId) {
		int result = Db.update("delete from sys_user_role where role_id = ?", roleId);
		return result>0 ? true : false;
	}
	
	/**
	 * 添加用户角色
	 * @param userRole
	 * @return
	 */
	@Override
	public boolean save() {
		int result = Db.update("insert into sys_user_role (user_id, role_id) values (?, ?)", this.getUserId(), this.getRoleId());
		return result>0 ? true : false;
	}
	
}
