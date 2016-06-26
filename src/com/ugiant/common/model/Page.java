package com.ugiant.common.model;

import java.util.List;

import com.ugiant.common.utils.PageUtils;

/**
 * 分页类
 * @author lingyuwang
 *
 * @param <T>
 */
public class Page<T> extends com.jfinal.plugin.activerecord.Page<T> {

	private static final long serialVersionUID = -7216107428463837585L;

	public Page(List<T> list, int pageNumber, int pageSize, int totalPage, int totalRow) {
		super(list, pageNumber, pageSize, totalPage, totalRow);
		initialize(); // 初始化参数
	}
	
	private int pageNo; // 当前页码
	private int pageSize; // 页面大小，设置为“-1”表示不进行分页（分页无效）
	
	private long count;// 总记录数，设置为“-1”表示不查询总数
	
	private int first;// 首页索引
	private int last;// 尾页索引
	private int prev;// 上一页索引
	private int next;// 下一页索引
	
	private int length = 8;// 显示页面长度
	private int slider = 1;// 前后显示页面长度
	
	private String funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
	
	private String funcParam = ""; // 函数的附加参数，第三个参数值。
	
	private String message = ""; // 设置提示消息，显示在“共n条”之后

	/**
	 * 初始化参数
	 */
	private void initialize(){
		pageNo = super.getPageNumber();
		pageSize = super.getPageSize();
		count = super.getTotalRow();
		
		this.first = 1;
		
		this.last = (int)(count / (this.pageSize < 1 ? PageUtils.PAGE_SIZE : this.pageSize) + first - 1);
		
		if (this.count % this.pageSize != 0 || this.last == 0) {
			this.last++;
		}

		if (this.last < this.first) {
			this.last = this.first;
		}
		
		if (this.pageNo <= 1) {
			this.pageNo = this.first;
		}

		if (this.pageNo >= this.last) {
			this.pageNo = this.last;
		}

		if (this.pageNo < this.last - 1) {
			this.next = this.pageNo + 1;
		} else {
			this.next = this.last;
		}

		if (this.pageNo > 1) {
			this.prev = this.pageNo - 1;
		} else {
			this.prev = this.first;
		}
		
		//2
		if (this.pageNo < this.first) {// 如果当前页小于首页
			this.pageNo = this.first;
		}

		if (this.pageNo > this.last) {// 如果当前页大于尾页
			this.pageNo = this.last;
		}
	}
	
	/**
	 * 默认输出当前分页标签 
	 * <div class="page">${page}</div>
	 */
	public String toString() {

		StringBuilder sb = new StringBuilder();
		
		if (pageNo == first) {// 如果是首页
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n");
		} else {
			sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+prev+","+pageSize+",'"+funcParam+"');\">&#171; 上一页</a></li>\n");
		}

		int begin = pageNo - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}

		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
			if (i < begin) {
				sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<li class=\"active\"><a href=\"javascript:\">" + (i + 1 - first)
						+ "</a></li>\n");
			} else {
				sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
		}

		if (last - end > slider) {
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
			end = last - slider;
		}

		for (int i = end + 1; i <= last; i++) {
			sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
					+ (i + 1 - first) + "</a></li>\n");
		}

		if (pageNo == last) {
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n");
		} else {
			sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+next+","+pageSize+",'"+funcParam+"');\">"
					+ "下一页 &#187;</a></li>\n");
		}

		sb.append("<li class=\"disabled controls\"><a href=\"javascript:\">当前 ");
		sb.append("<input type=\"text\" value=\""+pageNo+"\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
		sb.append(funcName+"(this.value,"+pageSize+",'"+funcParam+"');\" onclick=\"this.select();\"/> / ");
		sb.append("<input type=\"text\" value=\""+pageSize+"\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
		sb.append(funcName+"("+pageNo+",this.value,'"+funcParam+"');\" onclick=\"this.select();\"/> 条，");
		sb.append("共 " + count + " 条"+(message!=null?message:"")+"</a></li>\n");

		sb.insert(0,"<ul>\n").append("</ul>\n");
		
		sb.append("<div style=\"clear:both;\"></div>");

		return sb.toString();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getSlider() {
		return slider;
	}

	public void setSlider(int slider) {
		this.slider = slider;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncParam() {
		return funcParam;
	}

	public void setFuncParam(String funcParam) {
		this.funcParam = funcParam;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
