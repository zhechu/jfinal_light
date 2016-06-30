package com.ugiant.modules.sys.model;

import com.jfinal.plugin.activerecord.Page;
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
		select.append(", c.name company_name, c.parent_id company_parent_id, c.parent_ids company_parent_ids");
		select.append(", ca.id company_area_id, ca.name company_area_name, ca.parent_id company_area_parent_id, ca.parent_ids company_area_parent_ids");
		select.append(", o.name office_name, o.parent_id office_parent_id, o.parent_ids office_parent_ids");
		select.append(", oa.id office_area_id, oa.name office_area_name, oa.parent_id office_area_parent_id, oa.parent_ids office_area_parent_ids");
		select.append(", cu.id company_primary_person_id, cu.name company_primary_person_name");
		select.append(", cu2.id company_deputy_person_id, cu2.name company_deputy_person_name");
		select.append(", ou.id office_primary_person_id, ou.name office_primary_person_name");
		select.append(", ou2.id office_deputy_person_id, ou2.name office_deputy_person_name");
		StringBuilder sqlExceptSelect = new StringBuilder();
		sqlExceptSelect.append(" from sys_user a");
		sqlExceptSelect.append(" join sys_office c on c.id = a.company_id");
		sqlExceptSelect.append(" join sys_area ca on ca.id = c.area_id");
		sqlExceptSelect.append(" join sys_office o on o.id = a.office_id");
		sqlExceptSelect.append(" join sys_area oa on oa.id = o.area_id");
		sqlExceptSelect.append(" left join sys_user cu on cu.id = c.primary_person");
		sqlExceptSelect.append(" left join sys_user cu2 on cu2.id = c.deputy_person");
		sqlExceptSelect.append(" left join sys_user ou on ou.id = o.primary_person");
		sqlExceptSelect.append(" left join sys_user ou2 on ou2.id = o.deputy_person");
		// TODO
		return PageUtils.getPage(dao, pageNo, pageSize, select.toString(), sqlExceptSelect.toString());
	}
	
}
