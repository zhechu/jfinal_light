package com.ugiant.modules.sys.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.ugiant.common.service.BaseService;
import com.ugiant.modules.sys.model.Office;

/**
 * 机构 业务类
 * @author lingyuwang
 *
 */
public class OfficeService extends BaseService {
	
	public static final OfficeService service = new OfficeService(); // 机构单例

	private Office officeDao = Office.dao;

	/**
	 * 根据父 ids（以逗号,隔开）查询机构
	 * @param parentIds 父 ids（以逗号,隔开）
	 * @return
	 */
	public List<Record> findList(String parentIds) {
		parentIds = parentIds + "%";
		return officeDao.findByParentIdsLike(parentIds);
	}

	/**
	 * 获取所有或有数据权限的机构列表
	 * @return
	 */
	public List<Record> findList(Boolean isAll){
		/*if (isAll!=null && isAll){
			return officeDao.findAll();
		} else {
			return null;
		}*/
		return officeDao.findAll();
	}
	
}
