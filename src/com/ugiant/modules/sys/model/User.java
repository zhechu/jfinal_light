package com.ugiant.modules.sys.model;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.ugiant.common.utils.PageUtils;
import com.ugiant.modules.sys.baseModel.BaseUser;

/**
 * 用户 model
 * @author lingyuwang
 *
 */
public class User extends BaseUser<User> {
	
	private static final long serialVersionUID = -240506514582599822L;
	
	public static final User dao = new User();
	
	private static String userJoins;
	
	static {
		StringBuilder userJoinsSb = new StringBuilder();
		userJoinsSb.append(" join sys_office c on c.id = a.company_id");
		userJoinsSb.append(" join sys_area ca on ca.id = c.area_id");
		userJoinsSb.append(" join sys_office o on o.id = a.office_id");
		userJoinsSb.append(" join sys_area oa on oa.id = o.area_id");
		userJoinsSb.append(" left join sys_user cu on cu.id = c.primary_person");
		userJoinsSb.append(" left join sys_user cu2 on cu2.id = c.deputy_person");
		userJoinsSb.append(" left join sys_user ou on ou.id = o.primary_person");
		userJoinsSb.append(" left join sys_user ou2 on ou2.id = o.deputy_person");
		userJoinsSb.append(" left join sys_user_role ur on ur.user_id = a.id");
		userJoins = userJoinsSb.toString();
	}

	/**
	 * 分页查询
	 * @param pageNo 当前页
	 * @param pageSize 每页显示条数
	 * @param user 用户对象
	 * @return
	 */
	public Page<User> findPageByUser(int pageNo, int pageSize, User user) {
		StringBuilder select = new StringBuilder();
		select.append("select a.*");
		StringBuilder sqlExceptSelect = new StringBuilder();
		sqlExceptSelect.append(" from sys_user a").append(userJoins);
		sqlExceptSelect.append(" where 1 = 1");
		List<Object> paras = Lists.newArrayList(); // 存储参数
		Long companyId = user.getCompanyId();
		if (companyId != null) {
			sqlExceptSelect.append(" and (a.company_id = ? or c.parent_ids like ?)");
			paras.add(companyId);
			paras.add("%," + companyId + ",%");
		}
		Long officeId = user.getOfficeId();
		if (officeId != null) {
			sqlExceptSelect.append(" and (a.office_id = ? or o.parent_ids like ?)");
			paras.add(officeId);
			paras.add("%," + officeId + ",%");
		}
		String loginName = user.getLoginName();
		if (StrKit.notBlank(loginName)) {
			loginName = "%"+loginName.trim()+"%";
			sqlExceptSelect.append(" and a.login_name like ?");
			paras.add(loginName);
		}
		String name = user.getName();
		if (StrKit.notBlank(name)) {
			name = "%"+name.trim()+"%";
			sqlExceptSelect.append(" and a.name like ?");
			paras.add(name);
		}
		String loginFlag = user.getLoginFlag();
		if (StrKit.notBlank(loginFlag)) {
			sqlExceptSelect.append(" and a.login_flag = ?");
			paras.add(loginFlag);
		}
		Long roleId = user.getRoleId();
		if (roleId != null) {
			sqlExceptSelect.append(" and ur.role_id = ?");
			paras.add(roleId);
		}
		sqlExceptSelect.append(" order by a.create_date desc");
		return PageUtils.getPage(dao, pageNo, pageSize, select.toString(), sqlExceptSelect.toString(), paras.toArray());
	}

	/**
	 * 查询
	 * @param user
	 * @return
	 */
	public List<User> find(User user) {
		StringBuilder select = new StringBuilder();
		select.append("select a.*");
		StringBuilder sqlExceptSelect = new StringBuilder();
		sqlExceptSelect.append(" from sys_user a").append(userJoins);
		sqlExceptSelect.append(" where 1 = 1");
		List<Object> paras = Lists.newArrayList(); // 存储参数
		Long companyId = user.getCompanyId();
		if (companyId != null) {
			sqlExceptSelect.append(" and (a.company_id = ? or c.parent_ids like ?)");
			paras.add(companyId);
			paras.add("%," + companyId + ",%");
		}
		Long officeId = user.getOfficeId();
		if (officeId != null) {
			sqlExceptSelect.append(" and (a.office_id = ? or o.parent_ids like ?)");
			paras.add(officeId);
			paras.add("%," + officeId + ",%");
		}
		String loginName = user.getLoginName();
		if (StrKit.notBlank(loginName)) {
			loginName = "%"+loginName.trim()+"%";
			sqlExceptSelect.append(" and a.login_name like ?");
			paras.add(loginName);
		}
		String name = user.getName();
		if (StrKit.notBlank(name)) {
			name = "%"+name.trim()+"%";
			sqlExceptSelect.append(" and a.name like ?");
			paras.add(name);
		}
		String loginFlag = user.getLoginFlag();
		if (StrKit.notBlank(loginFlag)) {
			sqlExceptSelect.append(" and a.login_flag = ?");
			paras.add(loginFlag);
		}
		Long roleId = user.getRoleId();
		if (roleId != null) {
			sqlExceptSelect.append(" and ur.role_id = ?");
			paras.add(roleId);
		}
		sqlExceptSelect.append(" order by a.create_date desc");
		return find(select.append(sqlExceptSelect).toString(), paras.toArray());
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName 登录名
	 * @return
	 */
	public User getByLoginName(String loginName) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(" from sys_user a");
		sql.append(" where a.login_name = ?");
		return findFirst(sql.toString(), loginName);
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public boolean update(User user) {
		user.setUpdateDate(new Date());
		return user.update();
	}
	
}
