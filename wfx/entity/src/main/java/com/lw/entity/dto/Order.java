package com.lw.entity.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("`order`")
public class Order implements Serializable{

	  private static final long serialVersionUID = 1L;
	  
	  @TableId(value = "id",type = IdType.AUTO)
	  private Long id;                           //主键
	  
	  @TableField("order_id")
	  private String orderId;					//订单号
	  
	  @TableField("phone")
	  private String phone;						//联系电话
	  
	  @TableField("`name`")
	  private String name;						//真实姓名
	  
	  @TableField("merchant_user_id")
	  private Long merchantUserId;				//用户ID
	  
	  @TableField("good_id")
	  private Long goodId;						//物品ID
	  
	  @TableField("goods_name")
	  private String goodsName;					//物品名称
	  
	  @TableField("num")
	  private Integer num;						//购买数量
	  
	  @TableField("price")
	  private Integer price;					//单价
	  
	  @TableField("province")
	  private String province;					//省
	  
	  @TableField("city")
	  private String city;						//市
	  
	  @TableField("area")
	  private String area;						//区
	  
	  @TableField("address")
	  private String address;					//详细地址
	  
	  @TableField("remark")
	  private String remark;					//买家留言
	  
	  @TableField("pay_type")
	  private Long payType;						//支付方式1 微信 2支付宝
	  
	  @TableField("sku_id")
	  private Long skuId;						//套餐id
	  
	  @TableField("pay_state")
	  private Long payState;					//支付状态
	  
	  @TableField("create_time")
	  private Date createTime;					//订单生成时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getPayState() {
		return payState;
	}

	public void setPayState(Long payState) {
		this.payState = payState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	  
}
