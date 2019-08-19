package com.lw.entity.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("admin_role")
public class AdminRole implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;                       // id
	
	private String name;				   // 账户类型名称
	
	private Integer grade;					//账号权限等级
	
	private String description;				//账号描述
	
	private Date createTime;				//创建时间

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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
