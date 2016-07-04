package com.ugiant.modules.sys.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.common.service.BaseService;
import com.ugiant.modules.sys.model.Dict;

/**
 * 字典 业务类
 * @author lingyuwang
 *
 */
public class DictService extends BaseService {
	
	public static final DictService service = new DictService(); // 字典单例

	private Dict dictDao = Dict.dao;

	/**
	 * 查询字典类型列表
	 * @return
	 */
	public List<Record> findTypeList() {
		return dictDao.findTypeList();
	} 

	/**
	 * 分页查询
	 * @param pageNo 当前页
	 * @param pageSize 每页显示条数
	 * @param dict 字典对象
	 * @return
	 */
	public Page<Dict> findPageByDict(int pageNo, int pageSize, Dict dict) {
		return dictDao.findPageByDict(pageNo, pageSize, dict);
	}
	
}
