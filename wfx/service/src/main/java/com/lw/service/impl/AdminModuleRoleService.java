package com.lw.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lw.entity.dto.AdminModule;
import com.lw.entity.dto.AdminModuleRole;
import com.lw.entity.vo.TreeView;
import com.lw.mapper.AdminModuleRoleMapper;

@Service
public class AdminModuleRoleService extends ServiceImpl<AdminModuleRoleMapper, AdminModuleRole>{
	
	@Autowired
	private AdminModuleService adminModuleService;
	/**
	 *  通过用户ID获取用户 对应模块树结构
	 */
	@Transactional(readOnly = true)
	public List<TreeView> geTreeViews(Long id){
		
		return this.baseMapper.getByAdminId(id);
		
	}
	
	/**
	 * 	通过ROLEID 获取用户权限集合模
	 */
	@Transactional(readOnly = true)
	public List<AdminModule> getByRoleId(Long roleId) {
		
		//通过用户ROLEID 获取模块ID 集合
		QueryWrapper<AdminModuleRole> wrapper = new QueryWrapper<AdminModuleRole>();
		wrapper.lambda()
				.eq(AdminModuleRole::getAdminRoleId,roleId); 
		List<AdminModuleRole> adminModuleRoleList = this.list(wrapper);
		
		List<AdminModule> adminModuleList = new ArrayList<>();
		//循环list 获得moudle_id 得到List<AdminModule>
		for(AdminModuleRole adminModuleRole:adminModuleRoleList) {
			adminModuleList.add(adminModuleService.getById(adminModuleRole.getAdminModuleId()));
		}
		
		return adminModuleList;
	}
	
	/**
	 * 	通过RoleId来删除 对应的权限ID
	 */
	@Transactional
	public boolean deleteByRoleId(Long roleId) {
		
		QueryWrapper<AdminModuleRole> wrapper = new QueryWrapper<AdminModuleRole>();
		
		wrapper.lambda()
				.eq(AdminModuleRole::getAdminRoleId, roleId);  //where admin_role_id = #{roleId}
		return this.remove(wrapper);
		
	}

}
