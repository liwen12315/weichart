package com.lw.entity.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("goods_type")
public class GoodsType implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@TableId(value = "id",type = IdType.AUTO)
	private Long id;								//主键
	
	private String name;							//种类名称
	
	private Long orderNum;							//排序号

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

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	
	
	
}
