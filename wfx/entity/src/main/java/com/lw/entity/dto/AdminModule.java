package com.lw.entity.dto;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("admin_module")
public class AdminModule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;						//主键
	
	private String name;					//模块名称
	
	private String url;						//模块链接地址
	
	private Long orderNum;					//模块排序号
	
	private Long parentId;					//父模块ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
