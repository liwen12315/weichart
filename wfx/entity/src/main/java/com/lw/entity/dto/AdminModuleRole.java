package com.lw.entity.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("admin_module_role")
public class AdminModuleRole implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;							//主键
	
	private Long adminModuleId;					//功能模块id
	
	private Long adminRoleId;					//角色对应的功能模块

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdminModuleId() {
		return adminModuleId;
	}

	public void setAdminModuleId(Long adminModuleId) {
		this.adminModuleId = adminModuleId;
	}

	public Long getAdminRoleId() {
		return adminRoleId;
	}

	public void setAdminRoleId(Long adminRoleId) {
		this.adminRoleId = adminRoleId;
	}
		
}
