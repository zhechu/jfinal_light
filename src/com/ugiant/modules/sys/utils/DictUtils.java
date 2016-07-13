package com.ugiant.modules.sys.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ugiant.modules.sys.model.Dict;

/**
 * 字典工具类
 * @author lingyuwang
 *
 */
public class DictUtils {

	private static Dict dictDao = Dict.dao;

	private DictUtils() {}
	
	/**
	 * 获取字典标签
	 * @param value 数据值
	 * @param type 类型
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 获取字典标签列表
	 * @param values 一个或多个数据值，已逗号,分隔
	 * @param type 类型
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	/**
	 * 获取字典值
	 * @param label 标签
	 * @param type 数据值
	 * @param defaultLabel 默认标签
	 * @return
	 */
	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	/**
	 * 获取字典列表
	 * @param type 类型
	 * @return
	 */
	public static List<Dict> getDictList(String type){
		Map<String, List<Dict>> dictMap = Maps.newHashMap();
		for (Dict dict : dictDao.findByType(type)){
			List<Dict> dictList = dictMap.get(dict.getType());
			if (dictList != null){
				dictList.add(dict);
			} else{
				dictMap.put(dict.getType(), Lists.newArrayList(dict));
			}
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
}
