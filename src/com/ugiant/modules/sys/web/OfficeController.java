package com.ugiant.modules.sys.web;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ugiant.common.dict.Useable;
import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.model.Office;
import com.ugiant.modules.sys.service.OfficeService;

/**
 * 机构 控制器
 * @author lingyuwang
 *
 */
public class OfficeController extends BaseController {

	private OfficeService officeService = OfficeService.service;
	
	/**
	 * 机构管理页
	 * @return
	 */
	public void index() {
		this.render("officeIndex.jsp");
	}

	/**
	 * 机构管理子页
	 * @return
	 */
	public void list() {
		String parentIds = this.getPara("parentIds");
		this.setAttr("list", officeService.findList(parentIds));
		this.render("officeList.jsp");
	}

	/**
	 * 机构添加页
	 * @return
	 */
	public void form() {
		this.render("officeForm.jsp");
	}
	
	/**
	 * 获取机构JSON数据
	 */
	public void treeData() {
		Long extId = this.getParaToLong("extId");
		String type = this.getPara("type");
		Integer grade = this.getParaToInt("grade");
		Boolean isAll = this.getParaToBoolean("isAll");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((extId==null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && e.getLong("grade") <= grade))
					&& Useable.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		this.renderJson(mapList);
	}
	
}
