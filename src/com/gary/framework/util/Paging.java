package com.gary.framework.util;

import java.io.Serializable;
import java.util.List;

import com.gary.framework.core.Constants;

public class Paging implements Serializable{

	private static final long serialVersionUID = -7227529678815442509L;
	// 记录总数
	private long totalRows = 0;
	// 分页总数
	private int totalPages = 1;
	// 默认分页大小
	private static final int DEFAULT_PAGE_SIZE = Constants.PAGE_SIZE; 
	// 分页大小
	private int pageSize = DEFAULT_PAGE_SIZE;
	// 当前页
	private int pageNum = 1;
	// 列表
	private List<?> list;
	
	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * 检验参数是否合法
	 * @param pageNum
	 * @param pageSize
	 */
	private void verifyParams(int pageNum, int pageSize) {
		//当前页号
		if(pageNum > 0){
			this.pageNum = pageNum;
		}else{
			this.pageNum = 1;
		}
		
		//分页大小
		if(pageSize > 0){
			this.pageSize = pageSize;
		}else{
			this.pageSize = DEFAULT_PAGE_SIZE;
		}
	}

	/**
	 * 计算页数
	 */
	private void pageCount() {
		if ((totalRows % pageSize) == 0) {
			totalPages = (int) (totalRows / pageSize);
		} else {
			totalPages = (int) (totalRows / pageSize + 1);
		}

		if (totalPages == 0) {
			totalPages = 1;
		}
		if (pageNum > totalPages && totalRows != 0) {
			pageNum = totalPages;
		}
	}
	
	public Paging(){
	}
	
	/**
	 * 普通构造方法
	 * @param totalRows
	 * @param pageNum
	 * @param pageSize
	 * @param list
	 */
	public Paging(List<?> list, long totalRows, int pageNum, int pageSize) {
		this.list = list;
		this.totalRows = totalRows;
		//验证页号等是否是负数
		verifyParams(pageNum, pageSize);
		//计算分页
		pageCount();
	}
	
}