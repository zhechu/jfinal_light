package com.ugiant.modules.sys.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.common.model.BaseModel;

/**
 * 用户角色 model
 * @author lingyuwang
 *
 */
public class UserRole extends BaseModel<UserRole> {
	
	private static final long serialVersionUID = 5811814534062497587L;
	
	public static final UserRole dao = new UserRole();
	
	/**
	 * 删除用户角色
	 * @param userRole
	 * @return
	 */
	public boolean delete(Record userRole) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from sys_user_role where 1 = 1");
		Long user_id = userRole.getLong("user_id");
		if (user_id != null) {
			sql.append(" and user_id = ").append(user_id);
		}
		Long role_id = userRole.getLong("role_id");
		if (role_id != null) {
			sql.append(" and role_id = ").append(role_id);
		}
		if (Db.update(sql.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据用户删除用户角色
	 * @param userId 用户id
	 * @return
	 */
	public boolean deleteByUserId(Long userId) {
		Record userRole = new Record();
		userRole.set("user_id", userId);
		return delete(userRole);
	}
	
	/**
	 * 根据角色删除用户角色
	 * @param roleId 角色id
	 * @return
	 */
	public boolean deleteByRoleId(Long roleId) {
		Record userRole = new Record();
		userRole.set("role_id", roleId);
		return delete(userRole);
	}
	
	/**
	 * 添加用户角色
	 * @param role
	 * @return
	 */
	public boolean save(Record role) {
		return Db.save("sys_user_role", role);
	}
	
}
