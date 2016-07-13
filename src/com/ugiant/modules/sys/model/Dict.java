package com.ugiant.modules.sys.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.ugiant.common.utils.PageUtils;
import com.ugiant.modules.sys.baseModel.BaseDict;

/**
 * 字典 model
 * @author lingyuwang
 *
 */
public class Dict extends BaseDict<Dict> {
	
	private static final long serialVersionUID = 1681584707732812683L;
	
	public static final Dict dao = new Dict();

	/**
	 * 获取字典列表
	 * @param type 类型
	 * @return
	 */
	public List<Dict> findByType(String type) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(" from sys_dict a");
		sql.append(" where a.type = ?");
		sql.append(" order by a.type, a.sort, a.update_date desc");
		return find(sql.toString(), type);
	}
	
	/**
	 * 查询字典类型列表
	 * @return
	 */
	public List<Dict> findTypeList() {
		StringBuilder sql = new StringBuilder();
		sql.append("select type");
		sql.append(" from sys_dict a");
		sql.append(" group by a.type");
		sql.append(" order by a.type");
		return find(sql.toString());
	} 

	/**
	 * 分页查询
	 * @param pageNo 当前页
	 * @param pageSize 每页显示条数
	 * @param dict 字典对象
	 * @return
	 */
	public Page<Dict> findPageByDict(int pageNo, int pageSize, Dict dict) {
		StringBuilder select = new StringBuilder();
		select.append("select a.*");
		StringBuilder sqlExceptSelect = new StringBuilder();
		sqlExceptSelect.append(" from sys_dict a");
		sqlExceptSelect.append(" where 1 = 1");
		String type = dict.getType();
		if (StringUtils.isNotBlank(type)) {
			sqlExceptSelect.append(" and a.type = ").append(type);
		}
		String description = dict.getDescription();
		if (StringUtils.isNotBlank(description)) {
			sqlExceptSelect.append(" and a.description = ").append(description);
		}
		return PageUtils.getPage(dao, pageNo, pageSize, select.toString(), sqlExceptSelect.toString());
	}
	
}
