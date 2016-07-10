package com.ugiant.modules.sys.model;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.common.model.BaseModel;
import com.ugiant.common.utils.PageUtils;

/**
 * 用户 model
 * @author lingyuwang
 *
 */
public class User extends BaseModel<User> {
	
	private static final long serialVersionUID = -240506514582599822L;
	
	public static final User dao = new User();
	
	private static String userColumns;
	
	private static String userJoins;
	
	static {
		StringBuilder userColumnsSb = new StringBuilder();
		userColumnsSb.append(" a.*");
		userColumnsSb.append(", c.name company_name, c.parent_id company_parent_id, c.parent_ids company_parent_ids");
		userColumnsSb.append(", ca.id company_area_id, ca.name company_area_name, ca.parent_id company_area_parent_id, ca.parent_ids company_area_parent_ids");
		userColumnsSb.append(", o.name office_name, o.parent_id office_parent_id, o.parent_ids office_parent_ids");
		userColumnsSb.append(", oa.id office_area_id, oa.name office_area_name, oa.parent_id office_area_parent_id, oa.parent_ids office_area_parent_ids");
		userColumnsSb.append(", cu.id company_primary_person_id, cu.name company_primary_person_name");
		userColumnsSb.append(", cu2.id company_deputy_person_id, cu2.name company_deputy_person_name");
		userColumnsSb.append(", ou.id office_primary_person_id, ou.name office_primary_person_name");
		userColumnsSb.append(", ou2.id office_deputy_person_id, ou2.name office_deputy_person_name");
		userColumns = userColumnsSb.toString();
		
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
	public Page<Record> findPageByUser(int pageNo, int pageSize, Record user) {
		StringBuilder select = new StringBuilder();
		select.append("select").append(userColumns);
		StringBuilder sqlExceptSelect = new StringBuilder();
		sqlExceptSelect.append(" from sys_user a").append(userJoins);
		sqlExceptSelect.append(" where 1 = 1");
		List<Object> paras = Lists.newArrayList(); // 存储参数
		Long companyId = user.getLong("company_id");
		if (companyId != null) {
			sqlExceptSelect.append(" and (a.company_id = ? or c.parent_ids like ?)");
			paras.add(companyId);
			paras.add("%," + companyId + ",%");
		}
		Long officeId = user.getLong("office_id");
		if (officeId != null) {
			sqlExceptSelect.append(" and (a.office_id = ? or o.parent_ids like ?)");
			paras.add(officeId);
			paras.add("%," + officeId + ",%");
		}
		String loginName = user.getStr("login_name");
		if (StrKit.notBlank(loginName)) {
			loginName = "%"+loginName.trim()+"%";
			sqlExceptSelect.append(" and a.login_name like ?");
			paras.add(loginName);
		}
		String name = user.getStr("name");
		if (StrKit.notBlank(name)) {
			name = "%"+name.trim()+"%";
			sqlExceptSelect.append(" and a.name like ?");
			paras.add(name);
		}
		String loginFlag = user.getStr("login_flag");
		if (StrKit.notBlank(loginFlag)) {
			sqlExceptSelect.append(" and a.login_flag = ?");
			paras.add(loginFlag);
		}
		Long roleId = user.getLong("role_id");
		if (roleId != null) {
			sqlExceptSelect.append(" and ur.role_id = ?");
			paras.add(roleId);
		}
		sqlExceptSelect.append(" order by a.create_date desc");
		return PageUtils.getPage(pageNo, pageSize, select.toString(), sqlExceptSelect.toString(), paras.toArray());
	}

	/**
	 * 查询
	 * @param user
	 * @return
	 */
	public List<Record> find(Record user) {
		StringBuilder select = new StringBuilder();
		select.append("select").append(userColumns);
		StringBuilder sqlExceptSelect = new StringBuilder();
		sqlExceptSelect.append(" from sys_user a").append(userJoins);
		sqlExceptSelect.append(" where 1 = 1");
		List<Object> paras = Lists.newArrayList(); // 存储参数
		Long companyId = user.getLong("company_id");
		if (companyId != null) {
			sqlExceptSelect.append(" and (a.company_id = ? or c.parent_ids like ?)");
			paras.add(companyId);
			paras.add("%," + companyId + ",%");
		}
		Long officeId = user.getLong("office_id");
		if (officeId != null) {
			sqlExceptSelect.append(" and (a.office_id = ? or o.parent_ids like ?)");
			paras.add(officeId);
			paras.add("%," + officeId + ",%");
		}
		String loginName = user.getStr("login_name");
		if (StrKit.notBlank(loginName)) {
			loginName = "%"+loginName.trim()+"%";
			sqlExceptSelect.append(" and a.login_name like ?");
			paras.add(loginName);
		}
		String name = user.getStr("name");
		if (StrKit.notBlank(name)) {
			name = "%"+name.trim()+"%";
			sqlExceptSelect.append(" and a.name like ?");
			paras.add(name);
		}
		String loginFlag = user.getStr("login_flag");
		if (StrKit.notBlank(loginFlag)) {
			sqlExceptSelect.append(" and a.login_flag = ?");
			paras.add(loginFlag);
		}
		Long roleId = user.getLong("role_id");
		if (roleId != null) {
			sqlExceptSelect.append(" and ur.role_id = ?");
			paras.add(roleId);
		}
		sqlExceptSelect.append(" order by a.create_date desc");
		return Db.find(select.append(sqlExceptSelect).toString(), paras.toArray());
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName 登录名
	 * @return
	 */
	public Record getByLoginName(String loginName) {
		StringBuilder sql = new StringBuilder();
		sql.append("select").append(userColumns);
		sql.append(" from sys_user a").append(userJoins);
		sql.append(" where a.login_name = ?");
		return Db.findFirst(sql.toString(), loginName);
	}
	
	/**
	 * 根据用户编号获取用户
	 * @param id
	 * @return
	 */
	public Record findById(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select").append(userColumns);
		sql.append(" from sys_user a").append(userJoins);
		sql.append(" where a.id = ?");
		return Db.findFirst(sql.toString(), id);
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public boolean update(Record user) {
		user.set("update_date", new Date());
		return Db.update("sys_user", user);
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public boolean save(Record user) {
		return Db.save("sys_user", user);
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public boolean delete(Record user) {
		return Db.delete("sys_user", user);
	}
	
}
