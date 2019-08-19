package com.lw.common;

import java.io.Serializable;
import java.util.List;

/**
 *  page 泛型类 用于向后台传递一个
 * @author liwen
 *
 */
public class MyPage<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int currentPage; 		// 当前页
    
	private int pageSize; 			// 每页显示多少条记录

    private List<T> recordList; 	// 本页的数据列表
    
    private int recordCount; 		// 总记录数

    private int pageCount; 			// 总页数
    
    private int beginPageIndex;     // 页码列表的开始索引
    private int endPageIndex;       // 页码列表的结束索引 

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}
  
	public MyPage(int currentPage, int pageSize, List<T> recordList,
            int recordCount) { 
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.recordList = recordList;
        this.recordCount = recordCount;
        
        pageCount = (recordCount + pageSize - 1) / pageSize;
        // 计算 beginPageIndex 与 endPageIndex
        // 总页码小于等于5页时，全部显示
        if (pageCount <= 5)
        {
            beginPageIndex = 1;
            endPageIndex = pageCount;
        }else{
            // 默认显示 前2页 + 当前页 + 后2页
            beginPageIndex = currentPage - 2;
            endPageIndex = currentPage + 2;
            // 如果前面不足2个页码时，则显示前5页
            if (beginPageIndex < 1) {
            	beginPageIndex = 1;
                endPageIndex = 5;
            }else if (endPageIndex > pageCount) {
            	// 如果后面不足2个页码时，则显示后5页
            	endPageIndex = pageCount;
            	beginPageIndex = pageCount - 5;
            }
        }
    }
	

}
