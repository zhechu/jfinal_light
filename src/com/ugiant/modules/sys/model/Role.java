package com.ugiant.modules.sys.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ugiant.common.dict.Useable;
import com.ugiant.modules.sys.baseModel.BaseRole;

/**
 * 角色 model
 * @author lingyuwang
 *
 */
public class Role extends BaseRole<Role> {
	
	private static final long serialVersionUID = 5811814534062497587L;
	
	public static final Role dao = new Role();

	private static String roleColumns;
	
	private static String roleJoins;
	
	static {
		StringBuilder roleColumnsSb = new StringBuilder();
		roleColumnsSb.append(" a.*");
		//roleColumnsSb.append(", o.name office_name, o.code office_code");
		roleColumns = roleColumnsSb.toString();
		
		StringBuilder roleJoinsSb = new StringBuilder();
		roleJoinsSb.append(" left join sys_user_role ur on ur.role_id = a.id");
		roleJoinsSb.append(" left join sys_user u on u.id = ur.user_id");
		//roleJoinsSb.append(" left join sys_role_office ro on ro.role_id = a.id");
		//roleJoinsSb.append(" left join sys_office o on o.id = a.office_id");
		roleJoins = roleJoinsSb.toString();
	}
	
	/**
	 * 获取所有角色列表
	 * @return
	 */
	public List<Role> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(" from sys_role a");
		sql.append(" order by a.update_date desc");
		return find(sql.toString());
	}
	
	/**
	 * 根据用户 id 获取角色
	 * @param useable 是否可用（可选）
	 * @param userId 用户 id
	 * @return
	 */
	public List<Role> findByUserId(String useable, Long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select").append(roleColumns);
		//sql.append(", ro.office_id office_list_id");
		sql.append(" from sys_role a").append(roleJoins);
		sql.append(" where u.id = ?");
		if (StringUtils.isNotBlank(useable)) {
			sql.append(" and a.useable = '").append(useable).append("'");
		}
		sql.append(" order by a.update_date desc");
		return find(sql.toString(), userId);
	}
	
	/**
	 * 根据用户 id 获取角色
	 * @param userId 用户 id
	 * @return
	 */
	public List<Role> findByUserId(Long userId) {
		return findByUserId(Useable.YES, userId);
	}
	
	/**
	 * 根据登录名获取角色
	 * @param useable 是否可用（可选）
	 * @param loginName 登录名
	 * @return
	 */
	public List<Role> findByLoginName(String useable, String loginName) {
		StringBuilder sql = new StringBuilder();
		sql.append("select").append(roleColumns);
		//sql.append(", ro.office_id office_list_id");
		sql.append(" from sys_role a").append(roleJoins);
		sql.append(" where u.login_name = ?");
		if (StringUtils.isNotBlank(useable)) {
			sql.append(" and a.useable = '").append(useable).append("'");
		}
		sql.append(" order by a.update_date desc");
		return find(sql.toString(), loginName);
	}
	
	/**
	 * 根据登录名获取角色
	 * @param loginName 登录名
	 * @return
	 */
	public List<Role> findByLoginName(String loginName) {
		return findByLoginName(Useable.YES, loginName);
	}

}
