package com.lw.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lw.entity.dto.AdminModuleRole;
import com.lw.entity.dto.AdminRole;
import com.lw.mapper.AdminRoleMapper;

@Service
public class AdminRoleService extends ServiceImpl<AdminRoleMapper, AdminRole>{
	
	@Autowired
	private AdminModuleRoleService adminModuleRoleService;
	
	//获取比当前用户权限小的用户集合
	@Transactional(readOnly = true)
	public List<AdminRole> getByAdmin(AdminRole adminRole) {
		
		QueryWrapper<AdminRole> wrapper = new QueryWrapper<AdminRole>();
		wrapper.lambda()
				.gt(AdminRole::getGrade, adminRole.getGrade());   // 权限等级比当前用户大的
		return this.list(wrapper);	
	}
	
	//更新或者升级用户权限
	@Transactional
	public void saveAdmin(AdminRole adminRole, String moduleRoles) {
		
		//判断AdminRole 中的ID是否为空;如果如果不为空就删除 AdminModuleRole表锁对应的权限
		if(adminRole.getId() != null) {
			adminModuleRoleService.deleteByRoleId(adminRole.getId());
		}
		
		this.saveOrUpdate(adminRole);
		//解析JSON数组 然后进行插入操作
		Gson gson = new Gson();
        List<Long> moduleList = gson.fromJson(moduleRoles, new TypeToken<List<Long>>() {
        }.getType());
        
        //定义需要插入MODULEROLE表的集合
        List<AdminModuleRole> adminModuleRoleList = new ArrayList<AdminModuleRole>(moduleList.size());
        
        for(Long moduleId:moduleList) {
        	AdminModuleRole adminModuleRole = new AdminModuleRole();
        	adminModuleRole.setAdminModuleId(moduleId);
        	adminModuleRole.setAdminRoleId(adminRole.getId());
        	adminModuleRoleList.add(adminModuleRole);
        }
	
        adminModuleRoleService.saveBatch(adminModuleRoleList,moduleList.size());
	}
	
	//删除用户 同时删除MODULEROLE表的相关信息
	@Transactional
	public void delete(Long roleId) {
		
		this.removeById(roleId);
		
		adminModuleRoleService.deleteByRoleId(roleId);
		
	}

}
