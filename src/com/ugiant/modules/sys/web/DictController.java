package com.ugiant.modules.sys.web;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.common.utils.PageUtils;
import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.model.Dict;
import com.ugiant.modules.sys.service.DictService;

/**
 * 字典 控制器
 * @author lingyuwang
 *
 */
public class DictController extends BaseController {

	private DictService dictService = DictService.service;
	
	/**
	 * 字典管理页
	 * @return
	 */
	public void index() {
		List<Record> typeList = dictService.findTypeList();
		this.setAttr("typeList", typeList);
		int pageNo = PageUtils.getPageNo(this.getParaToInt("pageNo"));
		int pageSize = PageUtils.getPageSize(this.getParaToInt("pageSize"));
		Dict dict = this.getModel(Dict.class); // 字典
		Page<Dict> page = dictService.findPageByDict(pageNo, pageSize, dict);
        this.setAttr("page", page);
		this.render("dictList.jsp");
	}

	/**
	 * 字典添加页
	 * @return
	 */
	public void form() {
		this.render("dictForm.jsp");
	}
	
}
