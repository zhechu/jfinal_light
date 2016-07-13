package com.ugiant.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.kit.StrKit;
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
	public static int getPageNo(HttpServletRequest request, HttpServletResponse response, Integer pageNo, Boolean repage) {
		if (repage!=null && repage) { // 回到原页码，优先级最高
			String pageNoStr = CookieUtils.getCookie(request, "pageNo");
			if (StrKit.notBlank(pageNoStr)) {
				pageNo = Integer.parseInt(pageNoStr);
			}
		}
		if (pageNo == null) {
			pageNo = PAGE_NO;
		}
		// 存储当前页页码
		CookieUtils.setCookie(response, "pageNo", String.valueOf(pageNo));
		return pageNo;
	}
	
	/**
	 * 获取当前页页码
	 * @return
	 */
	public static int getPageNo() {
		return getPageNo(null, null, null, null);
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
	public static <M extends Model<M>> Page<M> getPage(M m, int pageNo, int pageSize, String select, String sqlExceptSelect) {
		//com.jfinal.plugin.activerecord.Page<M> page = m.paginate(pageNo, pageSize, select, sqlExceptSelect);
		//return new com.ugiant.common.model.Page<M>(page.getList(), page.getPageNumber(), page.getPageSize(), page.getTotalPage(), page.getTotalRow());
		return getPage(m, pageNo, pageSize, select, sqlExceptSelect, new Object[]{});
	}
	
	/**
	 * 获取分页对象
	 * @param pageNo
	 * @param pageSize
	 * @param select
	 * @param sqlExceptSelect
	 * @param paras
	 * @return
	 */
	public static <M extends Model<M>> Page<M> getPage(M m, int pageNo, int pageSize, String select, String sqlExceptSelect, Object... paras) {
		com.jfinal.plugin.activerecord.Page<M> page = m.paginate(pageNo, pageSize, select, sqlExceptSelect, paras);
		return new com.ugiant.common.model.Page<M>(page.getList(), page.getPageNumber(), page.getPageSize(), page.getTotalPage(), page.getTotalRow());
	}
	
}
