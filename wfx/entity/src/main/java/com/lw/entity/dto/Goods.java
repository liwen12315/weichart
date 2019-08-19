package com.lw.entity.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("goods")
public class Goods implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;										//主键		
	private String name;									//物品名称
	private Long merchantUserId;							//商家ID
	private Long goodsTypeId;								//物品种类
	private String pic;										//图片地址
	private String promoteDesc;								//描述
	private String skuTitle;								//标题
	private String skuCost;									//成本(单位:分)
	private String skuPrice;								//价格(单位:分)
	private String skuPmoney;								//分成金额(单位:分)
	private Long orderNum;									//排序号
	private Long state;										//状态(上架,下架,待上架)
	private Date createTime;								//上传时间
	
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
	public Long getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(Long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public Long getGoodsTypeId() {
		return goodsTypeId;
	}
	public void setGoodsTypeId(Long goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPromoteDesc() {
		return promoteDesc;
	}
	public void setPromoteDesc(String promoteDesc) {
		this.promoteDesc = promoteDesc;
	}
	public String getSkuTitle() {
		return skuTitle;
	}
	public void setSkuTitle(String skuTitle) {
		this.skuTitle = skuTitle;
	}
	public String getSkuCost() {
		return skuCost;
	}
	public void setSkuCost(String skuCost) {
		this.skuCost = skuCost;
	}
	public String getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(String skuPrice) {
		this.skuPrice = skuPrice;
	}
	public String getSkuPmoney() {
		return skuPmoney;
	}
	public void setSkuPmoney(String skuPmoney) {
		this.skuPmoney = skuPmoney;
	}
	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

}
