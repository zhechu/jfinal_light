package com.ugiant.common.utils;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 分页工具类
 * @author lingyuwang
 *
 */
public class PageUtils {

	private PageUtils() {}
	
	public final static int PAGE_SIZE = 20; // 每页显示条数
	
	public final static int MAX_PAGE_SIZE = 100; // 每页最多显示条数
	
	public final static int PAGE_NO = 1; // 当前页页码

	/**
	 * 获取当前页页码
	 * @param pageNo 当前页页码
	 * @return
	 */
	public static int getPageNo(Integer pageNo) {
		return pageNo!=null ? pageNo : PAGE_NO;
	}
	
	/**
	 * 获取当前页页码
	 * @return
	 */
	public static int getPageNo() {
		return getPageNo(null);
	}
	
	/**
	 * 获取每页显示条数
	 * @param pageSize 每页显示条数
	 * @return
	 */
	public static int getPageSize(Integer pageSize) {
		return pageSize!=null ? pageSize : PAGE_SIZE;
	}
	
	/**
	 * 获取每页显示条数
	 * @return
	 */
	public static int getPageSize() {
		return getPageSize(null);
	}

	/**
	 * 获取分页对象
	 * @param model dao
	 * @param pageNo  当前页页码
	 * @param pageSize 每页显示的条数
	 * @param select
	 * @param sqlExceptSelect
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <M extends Model> Page<M> getPage(M model, int pageNo, int pageSize, String select, String sqlExceptSelect) {
		com.jfinal.plugin.activerecord.Page<M> page = model.paginate(pageNo, pageSize, select, sqlExceptSelect);
		return new com.ugiant.common.model.Page<M>(page.getList(), page.getPageNumber(), page.getPageSize(), page.getTotalPage(), page.getTotalRow());
	}
	
}
