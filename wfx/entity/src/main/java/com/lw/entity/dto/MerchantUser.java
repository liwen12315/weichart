package com.lw.entity.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("merchant_user")
public class MerchantUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;					//商户主键
	
	private String name;				//商户姓名
	
	private String userName;			//商户登陆账号
	
	private String password;			//商户登陆密码
	
	private String qq;					// 企鹅
	
	private String weichart;			//微信
	
	private String phone;				//电话
	
	private Date createTime;			//创建时间
	
	@TableLogic
	private Integer deleted;			//逻辑删除

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeichart() {
		return weichart;
	}

	public void setWeichart(String weichart) {
		this.weichart = weichart;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	
}
