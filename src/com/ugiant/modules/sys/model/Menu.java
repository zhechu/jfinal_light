package com.ugiant.modules.sys.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.common.model.BaseModel;

/**
 * 菜单 model
 * @author lingyuwang
 *
 */
public class Menu extends BaseModel<Menu> {
	
	private static final long serialVersionUID = -4900058748571010519L;
	
	public static final Menu dao = new Menu();

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<Record> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*, p.name parent_name");
		sql.append(" from sys_menu a");
		sql.append(" left join sys_menu p on p.id = a.parent_id");
		sql.append(" order by a.sort");
		return Db.find(sql.toString());
	}

	/**
	 * 获取根菜单
	 * @return
	 */
	public static Long getRootId(){
		return 1L;
	}
	
	/**
	 * 菜单排序
	 * @param list 目标菜单列表
	 * @param sourcelist 源菜单列表
	 * @param parentId 父id
	 * @param cascade 是否级联
	 */
	public static void sortList(List<Record> list, List<Record> sourcelist, Long parentId, boolean cascade){
		Record e = null; // 当前菜单
		Long parent_id = null; // 当前菜单父id
		Record child = null; // 当前菜单子菜单
		Long child_parent_id = null; // 当前菜单子菜单父id
		Long id = null; // 当前菜单id
		for (int i=0; i<sourcelist.size(); i++){
			e = sourcelist.get(i);
			parent_id = e.getLong("parent_id");
			if (parent_id!=null && parent_id.equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						child = sourcelist.get(j);
						child_parent_id = child.getLong("parent_id");
						id = e.getLong("id");
						if (child_parent_id!=null && child_parent_id.equals(id)){
							sortList(list, sourcelist, id, true);
							break;
						}
					}
				}
			}
		}
	}
}
