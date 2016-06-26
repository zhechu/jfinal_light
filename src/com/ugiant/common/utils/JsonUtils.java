package com.ugiant.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.plugin.activerecord.Model;
import com.ugiant.common.mapper.JsonMapper;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * @author lingyuwang
 *
 */
public class JsonUtils {

	private JsonUtils() {}

	/**
	 * com.jfinal.plugin.activerecord.Model 转换为JSON字符串
	 * @param model
	 * @return
	 */
	public static String modelToJsonString(Model<?> model){
		return model.toJson();
	}
	
	/**
	 * java.util.List<com.jfinal.plugin.activerecord.Model> 转换为JSON字符串
	 * @param modelList
	 * @return
	 */
	public static String modelToJsonString(List<Model<?>> modelList) throws JsonParseException, JsonMappingException, IOException{
		List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
		ObjectMapper mapper = new ObjectMapper();  
		for (Model<?> model : modelList) {
			list.add(mapper.readValue(model.toJson(), Map.class));
		}
		return mapper.writeValueAsString(list);
	}
	
	/**
	 * 对象转换为JSON字符串
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String toJsonString(Object object) throws JsonParseException, JsonMappingException, IOException {
		try {
			List<Model<?>> modelList = (List<Model<?>>) object;
			return modelToJsonString(modelList);
		} catch (Exception e) {
			try {
				Model<?> model = (Model<?>) object;
				return modelToJsonString(model);
			} catch (Exception e2) {}
		}
		return JsonMapper.toJsonString(object);
	}
	
}
