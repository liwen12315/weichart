package com.lw.entity.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 	管理员账号类
 * @author liwen
 *
 */
@TableName("admin")
public class Admin implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@TableId(value = "id",type = IdType.AUTO)
	private Long id;           					//主键
	
	@TableField("role_id")
	private Long roleId;						//角色id
	
	@TableField("name")
	private String name;                        //姓名 
	
	@TableField("account")
	private String account;						//账号	
	
	@TableField("password")
	private String password;					//密码
	
	@TableField("description")
	private String description;					//描述信息
	
	@TableLogic
	private Integer deleted;						//逻辑删除字段

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	
	
}
