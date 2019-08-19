package com.lw.entity.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("goods_sku")
public class GoodsSku implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;							//主键
	private Long goodId;						//物品ID
	private String title;						//主题
    private String cost;						//成本
    private String price;						//价格
    private String pmoney;						//分成价格
    private Long orderNum;						//排序号
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGoodId() {
		return goodId;
	}
	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPmoney() {
		return pmoney;
	}
	public void setPmoney(String pmoney) {
		this.pmoney = pmoney;
	}
	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	
    
}
